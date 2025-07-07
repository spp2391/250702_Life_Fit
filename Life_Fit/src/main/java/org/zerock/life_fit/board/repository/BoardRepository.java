package org.zerock.life_fit.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.board.domain.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByBoardType(String boardType);
    List<Board> findByBoardTypeAndLocalCate_Localnum(String boardType, Long localnum);
    List<Board> findByLocalCate_Localnum(Long localnum);
    List<Board> findByLocalCate_LocalnumAndBoardType(Long localCateId, String boardType);

}
