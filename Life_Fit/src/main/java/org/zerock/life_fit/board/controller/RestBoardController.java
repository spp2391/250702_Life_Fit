package org.zerock.life_fit.board.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.domain.LocalCate;
import org.zerock.life_fit.board.dto.BoardDTO;
import org.zerock.life_fit.board.dto.PageRequestDTO;
import org.zerock.life_fit.board.dto.PageResponseDTO;
import org.zerock.life_fit.board.service.BoardService;
import org.zerock.life_fit.board.service.LocalCateService;
import org.zerock.life_fit.board.service.PageService;

import java.util.List;

    @Controller
    @RequiredArgsConstructor
public class RestBoardController {
        public final BoardService boardService;
        public final LocalCateService localCateService;
        public final PageService pageService;

   @GetMapping("/free")
   public String listFreeBoards(
           @RequestParam(required = false, defaultValue = "all") String searchType,
           Model model,
           PageRequestDTO pageRequestDTO,
           HttpServletRequest request) {

       PageResponseDTO<BoardDTO> responseDTO = pageService.getFreeBoardList(pageRequestDTO, searchType);

       model.addAttribute("requestURI", request.getRequestURI());
       model.addAttribute("responseDTO", responseDTO);
       model.addAttribute("keyword", pageRequestDTO.getKeyword());
       model.addAttribute("searchType", searchType);

       return "boardList";
   }


        /*@GetMapping("/topic")
        public String listTopicBoards(Model model) {
            List<Board> topicBoards = boardService.findByBoardType("TOPIC");
            List<LocalCate> localList = localCateService.getAllLocalCates(); // ✅ 지역 목록 추가

            model.addAttribute("boards", topicBoards);
            model.addAttribute("localList", localList); // ✅ 뷰로 전달

            return "topicList"; // templates/topicList.html
        }*/

        /*@GetMapping("/topic")
        public String listTopicBoards(
                @RequestParam(value = "localId", required = false) Long localId,
                Model model
        ) {
            List<Board> topicBoards;

            if (localId != null) {
                topicBoards = boardService.findByLocalAndBoardType(localId, "TOPIC");
                model.addAttribute("selectedLocalId", localId);
            } else {
                topicBoards = boardService.findByBoardType("TOPIC");
            }

            List<LocalCate> localList = localCateService.getAllLocalCates();

            model.addAttribute("boards", topicBoards);
            model.addAttribute("localList", localList);
            return "topicList";
        }*/
        /*@GetMapping("/topic")
        public String listTopicBoards(@RequestParam(required = false) Long localId,
                                      @RequestParam(required = false) String keyword,
                                      PageRequestDTO pageRequestDTO,
                                      Model model, HttpServletRequest request) {
            pageRequestDTO.setKeyword(keyword);

            PageResponseDTO<BoardDTO> responseDTO = pageService.getTopicBoardList(pageRequestDTO, localId ,keyword);
            model.addAttribute("responseDTO", responseDTO);
            model.addAttribute("localList", localCateService.getAllLocalCates());
            model.addAttribute("selectedLocalId", localId);
            model.addAttribute("keyword", keyword);
            model.addAttribute("requestURI", request.getRequestURI());

            return "topicList"; // 주제게시판 뷰
        }*/
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
        public String showWriteForm(Model model) {
            model.addAttribute("boardDTO", new BoardDTO());
            return "write";  // src/main/resources/templates/write.html
        }

        // 글쓰기 폼 제출 처리
        @PostMapping("/write")
        public String submitWrite(@ModelAttribute BoardDTO boardDTO) {
            boardDTO.setBoardType("FREE");
         /*   // TODO: 실제 로그인 사용자 연동 필요
            User dummyUser = User.builder().userId("testUser").build();

            boardService.save(boardDTO, dummyUser);*/

            return "redirect:/free"; // 저장 후 게시판 목록 페이지로 이동
        }



       @GetMapping("/topic/write")
       public String writeForm(@RequestParam(value = "localId", required = false) Long localId, Model model) {
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
        public String submitTopicBoard(@ModelAttribute BoardDTO boardDTO, Model model) {

            if (boardDTO.getLocalCateId() == null) {
                model.addAttribute("errorMessage", "지역을 선택해주세요.");
                model.addAttribute("boardDTO", boardDTO);  // 사용자가 입력한 내용 유지
                model.addAttribute("localList", localCateService.getAllLocalCates());  // 지역 목록 다시 전달
                return "topicWrite";
            }
            // 고정적으로 TOPIC 타입 설정
            boardDTO.setBoardType("TOPIC");

            /*// 로그인 기능 없으므로 임시 사용자
            User dummyUser = User.builder().userId("testUser").build();

            boardService.save(boardDTO, dummyUser);*/

            return "redirect:/topic";
        }

        @GetMapping("/board/{bno}")
        public String viewBoardDetail(@PathVariable Long bno, Model model) {
            /*Board board = boardService.findById(bno);*/ // 예외 처리 포함됨
            Board board = boardService.increaseVisitCount(bno);
            model.addAttribute("board", board);
            model.addAttribute("comments", board.getComments());
            return "boardDetail"; // 하나의 통합 뷰
        }

        @PostMapping("/board/{bno}/delete")
        public String deleteBoard(@PathVariable Long bno) {
            Board board = boardService.findById(bno); // 게시글 확인용 (없으면 예외 발생)
            boardService.delete(bno);

            // 게시판 종류에 따라 목록으로 리다이렉트
            if ("TOPIC".equals(board.getBoardType())) {
                return "redirect:/topic";
            } else {
                return "redirect:/free";
            }
        }

        @GetMapping("/board/{bno}/edit")
        public String editBoardForm(@PathVariable Long bno, Model model) {
            Board board = boardService.findById(bno);
            BoardDTO dto = new BoardDTO(board);

            model.addAttribute("boardDTO", dto);
            model.addAttribute("localList", localCateService.getAllLocalCates()); // 지역 선택용
            return "boardEdit";
        }
        @PostMapping("/board/{bno}/edit")
        public String editBoardSubmit(@PathVariable Long bno, @ModelAttribute BoardDTO boardDTO) {
            Board existing = boardService.findById(bno);

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
    /*@GetMapping("/notice")
        public String viewNotice() {
            return "notice";
        }
        */
        @ResponseBody
        @PostMapping("/board/{bno}/like")
        public int likeBoard(@PathVariable Long bno) {
            Board board = boardService.increaseLikes(bno);
            return board.getLikes();
        }

    }
