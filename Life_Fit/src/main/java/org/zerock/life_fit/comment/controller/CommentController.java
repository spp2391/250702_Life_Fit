
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

