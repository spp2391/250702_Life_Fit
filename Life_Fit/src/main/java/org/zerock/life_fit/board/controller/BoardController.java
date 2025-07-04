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
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /*@GetMapping
    public ResponseEntity<List<Board>> getFreeBoards() {
        List<Board> boards = boardService.findByBoardType("FREE");  // "FREE" = 자유게시판 구분값
        return ResponseEntity.ok(boards);
    }*/
    @GetMapping
    public ResponseEntity<List<BoardDTO>> getFreeBoards() {
        List<Board> boards = boardService.findByBoardType("FREE");
        List<BoardDTO> dtoList = boards.stream()
                .map(BoardDTO::new) // 또는 fromEntity() 메서드
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO dto) {
        // 테스트용 임시 User 객체 생성 (필요에 따라 수정)
        User user = new User();
        user.setUserId(dto.getUserId());
        // 다른 필드도 필요하면 세팅

        Board savedBoard = boardService.save(dto, user);

        BoardDTO responseDto = new BoardDTO(savedBoard);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @PutMapping("/{bno}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long bno, @RequestBody BoardDTO dto) {
        // 1. 기존 게시글 조회
        Board existingBoard = boardService.findById(bno);

        // 2. 수정할 데이터 반영 (예: 제목, 내용)
        existingBoard.setTitle(dto.getTitle());
        existingBoard.setContent(dto.getContent());

        // 3. 저장
        Board updatedBoard = boardService.save(existingBoard); // saveBoard는 Board 엔티티 받는 저장 메서드

        // 4. DTO 변환 후 반환
        BoardDTO responseDto = new BoardDTO(updatedBoard);

        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping("/{bno}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long bno) {
        boardService.delete(bno);
        return ResponseEntity.noContent().build();
    }



}
