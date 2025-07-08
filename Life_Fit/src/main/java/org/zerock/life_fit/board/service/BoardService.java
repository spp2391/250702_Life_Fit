package org.zerock.life_fit.board.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.domain.LocalCate;
import org.zerock.life_fit.board.dto.BoardDTO;
import org.zerock.life_fit.board.repository.BoardRepository;
import org.zerock.life_fit.board.repository.LocalCateRepository;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final LocalCateRepository localCateRepository;

    public BoardService(BoardRepository boardRepository, LocalCateRepository localCateRepository) {
        this.boardRepository = boardRepository;
        this.localCateRepository = localCateRepository;
    }

    public Board findById(Long bno) {
        return boardRepository.findById(bno)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다. bno=" + bno));
    }//bno로 상세보기

    public Board save(Board board) {
        return boardRepository.save(board);
    }//게시글 저장


        @Transactional
        public Board save(BoardDTO dto, User user) {
            LocalCate localCate = null;

            // boardType을 문자열 그대로 사용
            String boardType = dto.getBoardType();

            if ("TOPIC".equals(boardType)) {
                localCate = localCateRepository.findById(dto.getLocalCateId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 지역이 존재하지 않습니다."));
            }

            Board board = Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .boardType(boardType)  // String 필드에 바로 저장
                    .localCate(localCate)
                    .visitcount(0)
                    .likes(0)
                    .regdate(LocalDateTime.now())
                    .moddate(LocalDateTime.now())
                    .build();

            return boardRepository.save(board);
        }

    public List<Board> findByBoardType(String boardType) {
        // enum 변환 없이 String으로 바로 검색
        return boardRepository.findByBoardType(boardType);
    }

    public List<Board> findByLocalAndBoardType(Long localCateId, String boardType) {
        return boardRepository.findByLocalCate_LocalnumAndBoardType(localCateId, boardType);
    }


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
        entity.setLocalCate(board.getLocalCate());
        return entity; //
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
