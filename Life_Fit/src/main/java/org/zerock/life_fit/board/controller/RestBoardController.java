package org.zerock.life_fit.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.life_fit.OAuth2User.CustomOAuth2User;
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

    private final BoardService boardService;
    private final LocalCateService localCateService;
    private final PageService pageService;
    private final CommentService2 commentService;

    // principal에서 User 꺼내는 헬퍼 메서드
    private User extractUser(Object principal) {
        if (principal == null) return null;
        if (principal instanceof User) return (User) principal;
        if (principal instanceof CustomOAuth2User) return ((CustomOAuth2User) principal).getUser();
        return null;
    }

    // ... 생략

    @GetMapping("/free")
    public String listFreeBoards(
            @RequestParam(required = false, defaultValue = "all") String searchType,
            Model model,
            PageRequestDTO pageRequestDTO,
            HttpServletRequest request,
            @AuthenticationPrincipal Object principal
    ) {
        User user = extractUser(principal);
        PageResponseDTO<BoardDTO> responseDTO = pageService.getFreeBoardList(pageRequestDTO, searchType);

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("keyword", pageRequestDTO.getKeyword());
        model.addAttribute("searchType", searchType);

        return "board/boardList"; // ✅ 변경
    }

    @GetMapping("/topic")
    public String listTopicBoards(
            @RequestParam(required = false) Long localId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "all") String searchType,
            PageRequestDTO pageRequestDTO,
            Model model, HttpServletRequest request,
            @AuthenticationPrincipal Object principal) {

        User user = extractUser(principal);
        pageRequestDTO.setKeyword(keyword);

        PageResponseDTO<BoardDTO> responseDTO = pageService.getTopicBoardList(pageRequestDTO, localId, keyword, searchType);

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("localList", localCateService.getAllLocalCates());
        model.addAttribute("selectedLocalId", localId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("requestURI", request.getRequestURI());

        return "board/topicList"; // ✅ 변경
    }

    @GetMapping("/write")
    public String showWriteForm(@AuthenticationPrincipal Object principal, Model model) {
        User user = extractUser(principal);
        if (user == null) {
            return "redirect:/free";
        }
        model.addAttribute("boardDTO", new BoardDTO());
        return "board/write"; // ✅ 변경
    }

    @PostMapping("/write")
    public String submitWrite(@ModelAttribute BoardDTO boardDTO,
                              @AuthenticationPrincipal Object principal) {
        User user = extractUser(principal);
        if (user == null) {
            return "redirect:/free";
        }
        boardDTO.setBoardType("FREE");
        boardService.save(boardDTO, user);
        return "redirect:/free";
    }

    @GetMapping("/topic/write")
    public String writeForm(@RequestParam(value = "localId", required = false) Long localId,
                            @AuthenticationPrincipal Object principal,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        User user = extractUser(principal);
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
        return "board/topicWrite"; // ✅ 변경
    }

    @PostMapping("/topic/write")
    public String submitTopicBoard(@ModelAttribute BoardDTO boardDTO,
                                   @AuthenticationPrincipal Object principal,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        User user = extractUser(principal);
        if (user == null) {
            redirectAttributes.addFlashAttribute("alertMessage", "로그인 후 작성할 수 있습니다.");
            return "redirect:/topic";
        }

        if (boardDTO.getLocalCateId() == null) {
            model.addAttribute("errorMessage", "지역을 선택해주세요.");
            model.addAttribute("boardDTO", boardDTO);
            model.addAttribute("localList", localCateService.getAllLocalCates());
            return "board/topicWrite"; // ✅ 변경
        }

        boardDTO.setBoardType("TOPIC");
        boardService.save(boardDTO, user);

        return "redirect:/topic";
    }

    @GetMapping("/board/{bno}")
    public String viewBoard(@PathVariable Long bno, Model model,
                            @AuthenticationPrincipal Object principal) {
        User user = extractUser(principal);
        Board board = boardService.findById(bno);

        model.addAttribute("board", board);
        List<CommentResponseDTO> comments = commentService.getCommentsByBoard(bno);
        model.addAttribute("comments", comments);

        Long loginUserId = (user != null) ? user.getUserId() : null;
        model.addAttribute("loginUserId", loginUserId);

        return "board/boardDetail"; // ✅ 변경
    }

    @GetMapping("/board/{bno}/edit")
    public String editBoardForm(@PathVariable Long bno,
                                @AuthenticationPrincipal Object principal,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        User user = extractUser(principal);
        Board board = boardService.findById(bno);

        if (user == null || !board.getWriter().getUserId().equals(user.getUserId())) {
            redirectAttributes.addFlashAttribute("alertMessage", "해당 게시글은 본인만 수정할 수 있습니다.");
            return "redirect:/board/" + bno;
        }

        BoardDTO dto = new BoardDTO(board);
        model.addAttribute("boardDTO", dto);
        model.addAttribute("localList", localCateService.getAllLocalCates());
        return "board/boardEdit"; // ✅ 변경
    }

    @PostMapping("/board/{bno}/edit")
    public String editBoardSubmit(@PathVariable Long bno,
                                  @ModelAttribute BoardDTO boardDTO,
                                  @AuthenticationPrincipal Object principal,
                                  RedirectAttributes redirectAttributes) {
        User user = extractUser(principal);
        Board existing = boardService.findById(bno);

        if (user == null || !existing.getWriter().getUserId().equals(user.getUserId())) {
            redirectAttributes.addFlashAttribute("alertMessage", "작성자 본인만 수정할 수 있습니다.");
            return "redirect:/board/" + bno;
        }

        existing.setTitle(boardDTO.getTitle());
        existing.setContent(boardDTO.getContent());

        Long localCateId = boardDTO.getLocalCateId();
        LocalCate localCate = (localCateId != null) ? localCateService.getLocalCateById(localCateId) : null;
        existing.setLocalCate(localCate);

        boardService.update(existing);

        return "redirect:/board/" + bno;
    }

    @GetMapping("/service")
    public String service() {
        return "board/services"; // ✅ 변경
    }

}
