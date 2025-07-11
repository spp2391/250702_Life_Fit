/*

package org.zerock.life_fit.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.comment.Service.CommentService;
import org.zerock.life_fit.comment.dto.CommentDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public String addComment(@ModelAttribute CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return "redirect:/board/" + commentDTO.getBoardId();
    }

    @PostMapping("/{cno}/delete")
    public String deleteComment(@PathVariable Long cno, Long boardId) {
        commentService.deleteComment(cno);
        return "redirect:/board/" + boardId;
    }

    @PostMapping("/{cno}/edit")
    public String editComment(@PathVariable Long cno,
                              @RequestParam String content,
                              @RequestParam Long boardId) {
        commentService.updateComment(cno, content);
        return "redirect:/board/" + boardId;
    }
}

*/
package org.zerock.life_fit.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.life_fit.comment.Service.CommentService;
import org.zerock.life_fit.comment.dto.CommentDTO;
import org.zerock.life_fit.user.domain.User;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // ✅ 댓글 작성
    @PostMapping("/add")
    public String addComment(@ModelAttribute CommentDTO commentDTO,
                             @AuthenticationPrincipal User user
    , RedirectAttributes redirectAttributes) {

        if (user == null) {
            return "redirect:/member/login"; // 비로그인 접근 방지
        }

        commentService.addComment(commentDTO, user); // 작성자 전달
        return "redirect:/board/" + commentDTO.getBoardId();
    }

    // ✅ 댓글 삭제
    @PostMapping("/{cno}/delete")
    public String deleteComment(@PathVariable Long cno,
                                @RequestParam Long boardId,
                                @AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes
                                ) {

        if (user == null) {
            return "redirect:/member/login";
        }

        // 작성자 검증
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
                              @AuthenticationPrincipal User user,
                              RedirectAttributes redirectAttributes
                              ) {

        if (user == null) {
            return "redirect:/member/login";
        }

        // 작성자 검증
        if (!commentService.isCommentOwner(cno, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "댓글은 본인만 수정할 수 있습니다.");
            return "redirect:/board/" + boardId;
        }

        commentService.updateComment(cno, content);
        return "redirect:/board/" + boardId;
    }
}
