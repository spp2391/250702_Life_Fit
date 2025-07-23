package org.zerock.life_fit.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.life_fit.admin.dto.PostDTO;
import org.zerock.life_fit.admin.repository.AdminCommentRepository;
import org.zerock.life_fit.admin.repository.AdminPostRepository;

@Service
@RequiredArgsConstructor
public class AdminPostService {

    private final AdminPostRepository adminPostRepository;
    private final AdminCommentRepository adminCommentRepository;

    public Page<PostDTO> getPosts(String nameText, Pageable pageable) {
        Page<Object[]> results;

        if (nameText == null || nameText.isEmpty()) {
            results = adminPostRepository.findAllPosts(pageable);
        } else {
            results = adminPostRepository.findPostsByUserName(nameText, pageable);
        }

        return results.map(this::mapToPostDTO);
    }

    @Transactional
    public void updatePost(PostDTO dto) {
        adminPostRepository.updatePost(dto.getBno(), dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void deletePost(Long bno) {
        adminCommentRepository.deleteByBoardId(bno); // 댓글 먼저 삭제
        adminPostRepository.deleteByBno(bno);        // 게시글 삭제
    }

    private PostDTO mapToPostDTO(Object[] row) {
        return PostDTO.builder()
                .bno(((Number) row[0]).longValue())
                .title((String) row[1])
                .content((String) row[2])
                .boardType((String) row[3])
                .userId(((Number) row[4]).longValue())
                .nickname((String) row[5])
                .name((String) row[6])
                .regdate((java.time.LocalDateTime) row[7])
                .moddate((java.time.LocalDateTime) row[8])
                .visitcount(((Number) row[9]).intValue())
                .build();
    }
}
