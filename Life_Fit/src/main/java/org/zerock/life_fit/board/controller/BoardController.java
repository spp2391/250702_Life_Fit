package org.zerock.life_fit.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getFreeBoards() {
        List<Board> boards = boardService.findByBoardType("FREE");  // "FREE" = 자유게시판 구분값
        return ResponseEntity.ok(boards);
    }

}
