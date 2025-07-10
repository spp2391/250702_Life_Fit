package org.zerock.life_fit.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.dto.BoardDTO;
import org.zerock.life_fit.board.dto.PageRequestDTO;
import org.zerock.life_fit.board.dto.PageResponseDTO;

public interface PageService {
    PageResponseDTO<BoardDTO> getFreeBoardList(PageRequestDTO requestDTO, String searchType);
    PageResponseDTO<BoardDTO> getTopicBoardList(PageRequestDTO requestDTO, Long localId,String keyword, String searchType);

}
