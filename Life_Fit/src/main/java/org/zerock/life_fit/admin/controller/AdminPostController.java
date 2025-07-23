package org.zerock.life_fit.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.admin.dto.CommentDTO;
import org.zerock.life_fit.admin.dto.PostDTO;
import org.zerock.life_fit.admin.service.AdminPostService;
import org.zerock.life_fit.admin.service.AdminCommentService;

import java.util.List;

@Controller
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

    private final AdminPostService adminPostService;
    private final AdminCommentService adminCommentService;

    @GetMapping
    public String redirectToHtml() {
        return "redirect:/api/admin/posts/html";
    }

    @GetMapping("/html")
    public String getPostListHtml(@RequestParam(required = false) String userName,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        Page<PostDTO> postPage = adminPostService.getPosts(userName, PageRequest.of(page, size));
        model.addAttribute("postPage", postPage);
        model.addAttribute("userName", userName);
        return "admin/adminpost";
    }

    @GetMapping("/json")
    @ResponseBody
    public Page<PostDTO> getAllPosts(@RequestParam(required = false) String userId,
                                     Pageable pageable) {
        return adminPostService.getPosts(userId, pageable);
    }

    @PutMapping("/{bno}")
    @ResponseBody
    public ResponseEntity<String> updatePost(@PathVariable Long bno, @RequestBody PostDTO dto) {
        dto.setBno(bno);
        adminPostService.updatePost(dto);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @PostMapping("/delete/{bno}")
    public String deletePost(@PathVariable Long bno) {
        adminPostService.deletePost(bno);
        return "redirect:/api/admin/posts/html";
    }

    @GetMapping("/{bno}/comments")
    @ResponseBody
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long bno) {
        List<CommentDTO> comments = adminCommentService.getCommentsByPost(bno);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{cno}")
    @ResponseBody
    public ResponseEntity<String> deleteComment(@PathVariable Long cno) {
        adminCommentService.deleteComment(cno);
        return ResponseEntity.ok("댓글 삭제 완료");
    }

    @PutMapping("/comments/{cno}")
    @ResponseBody
    public ResponseEntity<String> updateComment(@PathVariable Long cno, @RequestBody CommentDTO dto) {
        adminCommentService.updateComment(cno, dto.getContent());
        return ResponseEntity.ok("댓글 수정 완료");
    }
}
