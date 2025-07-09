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
import org.zerock.life_fit.admin.service.CommentService;

import java.util.List;

@Controller
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

    private final AdminPostService adminPostService;
    private final CommentService commentService;

    //  기본 진입 경로 → html로 리다이렉트
    @GetMapping
    public String redirectToHtml() {
        return "redirect:/api/admin/posts/html";
    }

    //  게시글 목록 + 검색 + 페이징
    @GetMapping("/html")
    public String getPostListHtml(@RequestParam(required = false) String userId,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        Page<PostDTO> postPage = adminPostService.getPosts(userId, PageRequest.of(page, size));
        model.addAttribute("postPage", postPage);
        model.addAttribute("userId", userId);
        return "admin/adminpost";
    }

    // JSON 응답 API (선택)
    @GetMapping("/json")
    @ResponseBody
    public Page<PostDTO> getAllPosts(@RequestParam(required = false) String userId,
                                     Pageable pageable) {
        return adminPostService.getPosts(userId, pageable);
    }

    //  게시글 수정 (기존 form 방식은 제거됨) → fetch용
    @PutMapping("/{bno}")
    @ResponseBody
    public ResponseEntity<String> updatePost(@PathVariable Long bno, @RequestBody PostDTO dto) {
        dto.setBno(bno);
        adminPostService.updatePost(dto);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    // 게시글 삭제
    @PostMapping("/delete/{bno}")
    public String deletePost(@PathVariable Long bno) {
        adminPostService.deletePost(bno);
        return "redirect:/api/admin/posts/html";
    }

    // 댓글 목록 조회 (JSON)
    @GetMapping("/{bno}/comments")
    @ResponseBody
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long bno) {
        List<CommentDTO> comments = commentService.getCommentsByPost(bno);
        return ResponseEntity.ok(comments);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{cno}")
    @ResponseBody
    public ResponseEntity<String> deleteComment(@PathVariable Long cno) {
        commentService.deleteComment(cno);
        return ResponseEntity.ok("댓글 삭제 완료");
    }

    // 댓글 수정
    @PutMapping("/comments/{cno}")
    @ResponseBody
    public ResponseEntity<String> updateComment(@PathVariable Long cno, @RequestBody CommentDTO dto) {
        commentService.updateComment(cno, dto.getComment());
        return ResponseEntity.ok("댓글 수정 완료");
    }

}
