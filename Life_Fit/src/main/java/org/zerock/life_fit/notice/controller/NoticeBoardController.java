package org.zerock.life_fit.notice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.notice.NoticeBoardService;
import org.zerock.life_fit.notice.dto.NoticeBoardDTO;

import java.util.List;


@Controller
@RequestMapping("/notice")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }
    @GetMapping
    public String list(Model model) {
        model.addAttribute("noticeList", noticeBoardService.getList());
        return "notice_list"; // notice_list.html
}

// 글쓰기 페이지 이동
@GetMapping("/create")
public String createForm() {
    return "notice_create"; // notice_create.html
}

// 글 등록 처리
@PostMapping
public String register(@RequestParam String title, @RequestParam String content) {
    NoticeBoardDTO dto = new NoticeBoardDTO();
    dto.setTitle(title);
    dto.setContent(content);
    noticeBoardService.register(dto);
    return "redirect:/notice";
}

// 상세보기 페이지
@GetMapping("/{nbno}")
public String detail(@PathVariable Long nbno, Model model) {
    NoticeBoardDTO dto = noticeBoardService.get(nbno);
    model.addAttribute("notice", dto);
    return "notice_detail"; // notice_detail.html
}

// 수정 처리
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

// 삭제 처리
@PostMapping("/{nbno}/delete")
public String delete(@PathVariable Long nbno) {
    noticeBoardService.remove(nbno);
    return "redirect:/notice";
}
}


