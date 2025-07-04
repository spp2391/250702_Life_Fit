package org.zerock.life_fit.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.service.BoardService;

import java.util.List;

    @Controller
    @RequiredArgsConstructor
public class RestBoardController {
        public final BoardService boardService;
    @GetMapping("/free")
    public String listFreeBoards(Model model) {
        List<Board> freeBoards = boardService.findByBoardType("FREE");  // 자유게시판 글만 조회
        model.addAttribute("boards", freeBoards);
        return "boardList";
    }
        @GetMapping("/topic")
        public String listTopicBoards(Model model) {
            List<Board> topicBoards = boardService.findByBoardType("TOPIC");  // "TOPIC" 글만 조회
            model.addAttribute("boards", topicBoards);
            return "topicList"; // templates/topicList.html
        }
}
