package org.zerock.life_fit.board.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.dto.BoardDTO;
import org.zerock.life_fit.board.repository.BoardRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board findById(Long bno) {
        return boardRepository.findById(bno)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다. bno=" + bno));
    }//bno로 상세보기

    public Board save(Board board) {
        return boardRepository.save(board);
    }//게시글 저장

    public List<Board> findByBoardType(String boardType) {
        return boardRepository.findByBoardType(boardType);
    }//자유게시판인지,주제게시판인지


    public List<Board> findlocal(Long localnum) {
        return boardRepository.findByLocalCate_Localnum(localnum);
    }//지역기준으로 리스트보여줌

    public List<Board> findAll() {
        return boardRepository.findAll();
    }//전체 게시글 보여줌. 관리자가 혹시 쓸지몰라서 적어둠.

    public void delete(Long bno) {
        boardRepository.deleteById(bno);
    }//삭제

    public Board update(Board board) {
        Board entity = findById(board.getBno()); // 없으면 예외 발생

        // 필요한 필드만 수정
        entity.setTitle(board.getTitle());
        entity.setContent(board.getContent());
        return entity; // JPA의 변경 감지(dirty checking)로 자동 반영됨
    }

    // 좋아요 1 증가
    public Board increaseLikes(Long bno) {
        Board entity = findById(bno);
        entity.setLikes(entity.getLikes() + 1);
        return entity;
    }

    // 방문 수 1 증가
    public Board increaseVisitCount(Long bno) {
        Board entity = findById(bno);
        entity.setVisitcount(entity.getVisitcount() + 1);
        return entity;
    }

}
