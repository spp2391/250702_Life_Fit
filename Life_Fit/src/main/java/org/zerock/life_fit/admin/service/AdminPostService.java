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

    // ✅ 닉네임 기준 게시글 목록 조회
    public Page<PostDTO> getPostsByNickname(String nickname, Pageable pageable) {
        Page<Object[]> results;

        if (nickname == null || nickname.isEmpty()) {
            results = adminPostRepository.findAllPosts(pageable);
        } else {
            results = adminPostRepository.findPostsByNickname(nickname, pageable);
        }

        return results.map(this::mapToPostDTO);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(PostDTO dto) {
        adminPostRepository.updatePost(dto.getBno(), dto.getTitle(), dto.getContent());
    }

    // 게시글 삭제 + 관련 댓글 삭제
    @Transactional
    public void deletePost(Long bno) {
        adminCommentRepository.deleteByBoardId(bno); // 댓글 먼저 삭제
        adminPostRepository.deleteByBno(bno);        // 게시글 삭제
    }

    // Object[] → PostDTO 매핑
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
