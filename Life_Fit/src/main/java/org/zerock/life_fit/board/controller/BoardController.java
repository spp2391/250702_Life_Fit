package org.zerock.life_fit.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.dto.BoardDTO;
import org.zerock.life_fit.board.service.BoardService;
import org.zerock.life_fit.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    /*@GetMapping
    public ResponseEntity<List<Board>> getFreeBoards() {
        List<Board> boards = boardService.findByBoardType("FREE");  // "FREE" = 자유게시판 구분값
        return ResponseEntity.ok(boards);
    }*/
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody BoardDTO dto) {
        User dummy = User.builder().userId("testUser").build();
        Board saved = boardService.save(dto, dummy);
        return ResponseEntity.ok(saved.getBno());
    }

    /*@PutMapping("/{bno}")
    public ResponseEntity<Void> update(@PathVariable Long bno, @RequestBody BoardDTO dto) {
        boardService.update(dto.toEntity());
        return ResponseEntity.ok().build();
    }*/

    @DeleteMapping("/{bno}")
    public ResponseEntity<Void> delete(@PathVariable Long bno) {
        boardService.delete(bno);
        return ResponseEntity.noContent().build();

    }

}
