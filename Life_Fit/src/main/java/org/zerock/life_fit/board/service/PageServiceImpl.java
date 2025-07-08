package org.zerock.life_fit.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.dto.BoardDTO;
import org.zerock.life_fit.board.dto.PageRequestDTO;
import org.zerock.life_fit.board.dto.PageResponseDTO;
import org.zerock.life_fit.board.repository.BoardRepository;
import org.zerock.life_fit.board.repository.PageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
    private final PageRepository pageRepository;
    private final BoardRepository boardRepository;

   /* @Override
    public PageResponseDTO<BoardDTO> getFreeBoardList(PageRequestDTO requestDTO, String searchType) {
        PageRequest pageable = PageRequest.of(requestDTO.getPage() - 1, requestDTO.getSize());

       *//* Page<Board> result = pageRepository.findByBoardType("FREE", pageable);*//*
        Page<Board> result;

        if (requestDTO.getKeyword() != null && !requestDTO.getKeyword().trim().isEmpty()) {
            result = pageRepository.searchFreeBoard(requestDTO.getKeyword(), pageable);
        } else {
            result = pageRepository.findByBoardType("FREE", pageable);
        }

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(BoardDTO::new)
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(requestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardDTO> getTopicBoardList(PageRequestDTO requestDTO, Long localId,String keyword, String searchType) {
        PageRequest pageable = PageRequest.of(requestDTO.getPage() - 1, requestDTO.getSize());

        Page<Board> result;
        if (localId != null && keyword != null && !keyword.isBlank()) {
            // 지역과 키워드 모두 있을 때 (키워드 검색 메서드가 필요함)
            result = pageRepository.findByBoardTypeAndLocalCateAndKeyword("TOPIC", localId, keyword, pageable);
        } else if (localId != null) {
            // 지역만 있을 때
            result = pageRepository.findByBoardTypeAndLocalCate("TOPIC", localId, pageable);
        } else if (keyword != null && !keyword.isBlank()) {
            // 키워드만 있을 때
            result = pageRepository.findByBoardTypeAndKeyword("TOPIC", keyword, pageable);
        } else {
            // 둘 다 없을 때
            result = pageRepository.findByBoardType("TOPIC", pageable);
        }

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(BoardDTO::new)
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(requestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }*/
   @Override
   public PageResponseDTO<BoardDTO> getFreeBoardList(PageRequestDTO requestDTO, String searchType) {
       PageRequest pageable = PageRequest.of(requestDTO.getPage() - 1, requestDTO.getSize());
       Page<Board> result;

       String keyword = requestDTO.getKeyword();

       if (keyword != null && !keyword.trim().isEmpty()) {
           switch (searchType) {
               case "title":
                   result = pageRepository.findByBoardTypeAndTitleContaining("FREE", keyword, pageable);
                   break;
               case "content":
                   result = pageRepository.findByBoardTypeAndContentContaining("FREE", keyword, pageable);
                   break;
               case "title_content":
               default:
                   result = pageRepository.searchFreeBoard(keyword, pageable);
                   break;
           }
       } else {
           result = pageRepository.findByBoardType("FREE", pageable);
       }

       List<BoardDTO> dtoList = result.getContent().stream()
               .map(BoardDTO::new)
               .collect(Collectors.toList());

       return PageResponseDTO.<BoardDTO>withAll()
               .pageRequestDTO(requestDTO)
               .dtoList(dtoList)
               .total((int) result.getTotalElements())
               .build();
   }

    @Override
    public PageResponseDTO<BoardDTO> getTopicBoardList(PageRequestDTO requestDTO, Long localId, String keyword, String searchType) {
        PageRequest pageable = PageRequest.of(requestDTO.getPage() - 1, requestDTO.getSize());
        Page<Board> result;

        if (localId != null && keyword != null && !keyword.isBlank()) {
            switch (searchType) {
                case "title":
                    result = pageRepository.findByBoardTypeAndLocalCateAndTitleContaining("TOPIC", localId, keyword, pageable);
                    break;
                case "content":
                    result = pageRepository.findByBoardTypeAndLocalCateAndContentContaining("TOPIC", localId, keyword, pageable);
                    break;
                case "title_content":
                default:
                    result = pageRepository.findByBoardTypeAndLocalCateAndKeyword("TOPIC", localId, keyword, pageable);
                    break;
            }
        } else if (localId != null) {
            result = pageRepository.findByBoardTypeAndLocalCate("TOPIC", localId, pageable);
        } else if (keyword != null && !keyword.isBlank()) {
            switch (searchType) {
                case "title":
                    result = pageRepository.findByBoardTypeAndTitleContaining("TOPIC", keyword, pageable);
                    break;
                case "content":
                    result = pageRepository.findByBoardTypeAndContentContaining("TOPIC", keyword, pageable);
                    break;
                case "title_content":
                default:
                    result = pageRepository.findByBoardTypeAndKeyword("TOPIC", keyword, pageable);
                    break;
            }
        } else {
            result = pageRepository.findByBoardType("TOPIC", pageable);
        }

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(BoardDTO::new)
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(requestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }



}
