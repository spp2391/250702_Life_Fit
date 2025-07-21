package org.zerock.life_fit.notice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.notice.NoticeBoardService;
import org.zerock.life_fit.notice.dto.NoticeBoardDTO;

@Controller
@RequestMapping("/notice")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    /** 공지사항 목록 */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("noticeList", noticeBoardService.getList());
        return "noticelist"; // noticelist.html
    }

    /** 글쓰기 페이지 (ADMIN만 접근 가능) */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createForm() {
        return "notice_create"; // notice_create.html
    }

    /** 글 등록 처리 (ADMIN만 가능) */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String register(@RequestParam String title, @RequestParam String content) {
        NoticeBoardDTO dto = new NoticeBoardDTO();
        dto.setTitle(title);
        dto.setContent(content);
        noticeBoardService.register(dto);
        return "redirect:/notice";
    }

    /** 상세보기 페이지 */
    @GetMapping("/{nbno}")
    public String detail(@PathVariable Long nbno, Model model) {
        NoticeBoardDTO dto = noticeBoardService.get(nbno);
        model.addAttribute("notice", dto);
        return "notice_detail"; // notice_detail.html
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{nbno}/edit")
    public String editForm(@PathVariable Long nbno, Model model) {
        NoticeBoardDTO dto = noticeBoardService.get(nbno);
        model.addAttribute("notice", dto);
        return "notice_edit"; // notice_edit.html
    }
    /** 글 수정 처리 (ADMIN만 가능) */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{nbno}/edit")
    public String update(@PathVariable Long nbno,
                         @RequestParam String title,
                         @RequestParam String content) {
        NoticeBoardDTO dto = new NoticeBoardDTO();
        dto.setTitle(title);
        dto.setContent(content);
        noticeBoardService.modify(nbno, dto);
        return "redirect:/notice/" + nbno;
    }

    /** 글 삭제 처리 (ADMIN만 가능) */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{nbno}/delete")
    public String delete(@PathVariable Long nbno) {
        noticeBoardService.remove(nbno);
        return "redirect:/notice";
    }
}
