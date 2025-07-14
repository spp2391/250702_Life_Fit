package org.zerock.life_fit.board.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.domain.LocalCate;
import org.zerock.life_fit.board.dto.BoardDTO;
import org.zerock.life_fit.board.dto.PageRequestDTO;
import org.zerock.life_fit.board.dto.PageResponseDTO;
import org.zerock.life_fit.board.service.BoardService;
import org.zerock.life_fit.board.service.LocalCateService;
import org.zerock.life_fit.board.service.PageService;
import org.zerock.life_fit.comment.Service.CommentService2;
import org.zerock.life_fit.comment.dto.CommentResponseDTO;
import org.zerock.life_fit.user.domain.User;

import java.util.List;

    @Controller
    @RequiredArgsConstructor
public class RestBoardController {
        public final BoardService boardService;
        public final LocalCateService localCateService;
        public final PageService pageService;
        public final CommentService2 commentService;
   @GetMapping("/free")
   public String listFreeBoards(
           @RequestParam(required = false, defaultValue = "all") String searchType,
           Model model,
           PageRequestDTO pageRequestDTO,
           HttpServletRequest request,
           @AuthenticationPrincipal User user
           ) {

       PageResponseDTO<BoardDTO> responseDTO = pageService.getFreeBoardList(pageRequestDTO, searchType);

       model.addAttribute("requestURI", request.getRequestURI());
       model.addAttribute("responseDTO", responseDTO);
       model.addAttribute("keyword", pageRequestDTO.getKeyword());
       model.addAttribute("searchType", searchType);

       return "boardList";
   }



        @GetMapping("/topic")
        public String listTopicBoards(@RequestParam(required = false) Long localId,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false, defaultValue = "all") String searchType,
                                      PageRequestDTO pageRequestDTO,
                                      Model model, HttpServletRequest request) {

            pageRequestDTO.setKeyword(keyword);

            PageResponseDTO<BoardDTO> responseDTO = pageService.getTopicBoardList(pageRequestDTO, localId, keyword, searchType);

            model.addAttribute("responseDTO", responseDTO);
            model.addAttribute("localList", localCateService.getAllLocalCates());
            model.addAttribute("selectedLocalId", localId);
            model.addAttribute("keyword", keyword);
            model.addAttribute("searchType", searchType);
            model.addAttribute("requestURI", request.getRequestURI());

            return "topicList"; // 주제게시판 뷰
        }


       @GetMapping("/write")
       public String showWriteForm(@AuthenticationPrincipal User user, Model model) {
           if (user == null) {
               return "redirect:/free"; // 로그인 페이지로 리다이렉트
           }
           model.addAttribute("boardDTO", new BoardDTO());
           return "write";
       }

        @PostMapping("/write")
        public String submitWrite(@ModelAttribute BoardDTO boardDTO,
                                  @AuthenticationPrincipal User user) {
            if (user == null) {
                return "redirect:/free";
            }
            boardDTO.setBoardType("FREE");
            boardService.save(boardDTO, user);
            return "redirect:/free";
        }



      @GetMapping("/topic/write")
      public String writeForm(@RequestParam(value = "localId", required = false) Long localId,
                              @AuthenticationPrincipal User user,
                              RedirectAttributes redirectAttributes,
                              Model model) {

          if (user == null) {
              redirectAttributes.addFlashAttribute("alertMessage", "로그인 후 이용 가능합니다.");
              return "redirect:/topic";
          }

          BoardDTO boardDTO = new BoardDTO();
          if (localId != null) {
              boardDTO.setLocalCateId(localId);
          }

          model.addAttribute("boardDTO", boardDTO);
          model.addAttribute("localList", localCateService.getAllLocalCates());
          return "topicWrite";
      }


        // 글쓰기 저장 처리
        @PostMapping("/topic/write")
        public String submitTopicBoard(@ModelAttribute BoardDTO boardDTO,
                                       @AuthenticationPrincipal User user,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

            if (user == null) {
                redirectAttributes.addFlashAttribute("alertMessage", "로그인 후 작성할 수 있습니다.");
                return "redirect:/topic";
            }

            if (boardDTO.getLocalCateId() == null) {
                model.addAttribute("errorMessage", "지역을 선택해주세요.");
                model.addAttribute("boardDTO", boardDTO);
                model.addAttribute("localList", localCateService.getAllLocalCates());
                return "topicWrite";
            }

            boardDTO.setBoardType("TOPIC");
            boardService.save(boardDTO, user);

            return "redirect:/topic";
        }


@GetMapping("/board/{bno}")
public String viewBoard(@PathVariable Long bno, Model model) {
    Board board = boardService.findById(bno);
    model.addAttribute("board", board);

    List<CommentResponseDTO> comments = commentService.getCommentsByBoard(bno);
    model.addAttribute("comments", comments); // ✅ DTO 리스트 넘기기

    return "boardDetail";
}



       @GetMapping("/board/{bno}/edit")
       public String editBoardForm(@PathVariable Long bno,
                                   @AuthenticationPrincipal User user,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
           Board board = boardService.findById(bno);

           if (user == null || !board.getWriter().getUserId().equals(user.getUserId())) {
               /*return "redirect:/board/" + bno; // 권한 없으면 상세보기 페이지로*/
               redirectAttributes.addFlashAttribute("alertMessage", "해당 게시글은 본인만 수정할 수 있습니다.");
               return "redirect:/board/" + bno;
           }

           BoardDTO dto = new BoardDTO(board);
           model.addAttribute("boardDTO", dto);
           model.addAttribute("localList", localCateService.getAllLocalCates());
           return "boardEdit";
       }

        @PostMapping("/board/{bno}/edit")
        public String editBoardSubmit(@PathVariable Long bno,
                                      @ModelAttribute BoardDTO boardDTO,
                                      @AuthenticationPrincipal User user,
                                      RedirectAttributes redirectAttributes
                                      ) {
            Board existing = boardService.findById(bno);

            if (user == null || !existing.getWriter().getUserId().equals(user.getUserId())) {
                redirectAttributes.addFlashAttribute("alertMessage", "작성자 본인만 수정할 수 있습니다.");
                return "redirect:/board/" + bno; // 권한 없으면 상세보기 페이지로
            }

            existing.setTitle(boardDTO.getTitle());
            existing.setContent(boardDTO.getContent());
            Long localCateId = boardDTO.getLocalCateId();
            LocalCate localCate = null;
            if (localCateId != null) {
                localCate = localCateService.getLocalCateById(localCateId);
            }
            existing.setLocalCate(localCate);

            boardService.update(existing);

            return "redirect:/board/" + bno;
        }

        @PostMapping("/board/{bno}/delete")
        public String deleteBoard(@PathVariable Long bno,
                                  @AuthenticationPrincipal User user,
                                  RedirectAttributes redirectAttributes
                                  ) {
            Board board = boardService.findById(bno);

            if (user == null || !board.getWriter().getUserId().equals(user.getUserId())) {
                redirectAttributes.addFlashAttribute("alertMessage", "해당 게시글은 본인만 삭제할 수 있습니다.");
                return "redirect:/board/" + bno; // 권한 없으면 상세보기 페이지로
            }

            boardService.delete(bno);

            if ("TOPIC".equals(board.getBoardType())) {
                return "redirect:/topic";
            } else {
                return "redirect:/free";
            }
        }

        @ResponseBody
        @PostMapping("/board/{bno}/like")
        public int likeBoard(@PathVariable Long bno) {
            Board board = boardService.increaseLikes(bno);
            return board.getLikes();
        }

    }
