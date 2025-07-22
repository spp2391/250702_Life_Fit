package org.zerock.life_fit.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.life_fit.OAuth2User.CustomOAuth2User;
import org.zerock.life_fit.comment.Service.CommentService2;
import org.zerock.life_fit.comment.dto.CommentDTO;
import org.zerock.life_fit.user.domain.User;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService2 commentService;

    // principal에서 User 꺼내는 헬퍼
    private User extractUser(Object principal) {
        if (principal == null) return null;
        if (principal instanceof User) return (User) principal;
        if (principal instanceof CustomOAuth2User) return ((CustomOAuth2User) principal).getUser();
        return null;
    }

    @PostMapping("/add")
    public String addComment(@ModelAttribute CommentDTO commentDTO,
                             @AuthenticationPrincipal Object principal,
                             RedirectAttributes redirectAttributes) {

        User user = extractUser(principal);
        if (user == null) {
            return "redirect:/member/login";
        }

        commentService.addComment(commentDTO, user);
        return "redirect:/board/" + commentDTO.getBoardId();
    }

    @PostMapping("/{cno}/delete")
    public String deleteComment(@PathVariable Long cno,
                                @RequestParam Long boardId,
                                @AuthenticationPrincipal Object principal,
                                RedirectAttributes redirectAttributes) {

        User user = extractUser(principal);
        if (user == null) {
            return "redirect:/member/login";
        }

        if (!commentService.isCommentOwner(cno, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "댓글은 본인만 삭제할 수 있습니다.");
            return "redirect:/board/" + boardId;
        }

        commentService.deleteComment(cno);
        return "redirect:/board/" + boardId;
    }

    @PostMapping("/{cno}/edit")
    public String editComment(@PathVariable Long cno,
                              @RequestParam String content,
                              @RequestParam Long boardId,
                              @AuthenticationPrincipal Object principal,
                              RedirectAttributes redirectAttributes) {

        User user = extractUser(principal);
        if (user == null) {
            return "redirect:/member/login";
        }

        if (!commentService.isCommentOwner(cno, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "댓글은 본인만 수정할 수 있습니다.");
            return "redirect:/board/" + boardId;
        }

        commentService.updateComment(cno, content);
        return "redirect:/board/" + boardId;
    }
}
