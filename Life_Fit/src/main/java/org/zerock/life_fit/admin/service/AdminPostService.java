package org.zerock.life_fit.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.admin.dto.PostDTO;
import org.zerock.life_fit.admin.repository.AdminPostRepository;

@Service
@RequiredArgsConstructor
public class AdminPostService {

    private final AdminPostRepository adminPostRepository;

    public Page<PostDTO> getPosts(String userIdText, Pageable pageable) {
        Page<Object[]> results;

        if (userIdText == null || userIdText.isEmpty()) {
            results = adminPostRepository.findAllPosts(pageable);
        } else {
            Long userId = Long.parseLong(userIdText);
            results = adminPostRepository.findPostsByUserId(userId, pageable);
        }

        return results.map(this::mapToPostDTO);
    }

    public void updatePost(PostDTO dto) {
        adminPostRepository.updatePost(dto.getBno(), dto.getTitle(), dto.getContent());
    }

    public void deletePost(Long bno) {
        adminPostRepository.deleteByBno(bno);
    }

    private PostDTO mapToPostDTO(Object[] row) {
        return PostDTO.builder()
                .bno(((Number) row[0]).longValue())
                .title((String) row[1])
                .content((String) row[2])
                .boardType((String) row[3])
                .userId(((Number) row[4]).longValue())
                .nickname((String) row[5])
                .regdate((java.time.LocalDateTime) row[6])
                .moddate((java.time.LocalDateTime) row[7])
                .visitcount(((Number) row[8]).intValue())
                .build();
    }
}
