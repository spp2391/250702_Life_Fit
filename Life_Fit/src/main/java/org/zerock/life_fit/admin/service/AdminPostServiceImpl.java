package org.zerock.life_fit.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.admin.dto.PostDTO;
import org.zerock.life_fit.admin.repository.AdminPostRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminPostServiceImpl implements AdminPostService {

    private final AdminPostRepository adminPostRepository;

    @Override
    public Page<PostDTO> getPosts(String userId, Pageable pageable) {
        Page<Object[]> results = (userId == null || userId.isEmpty())
                ? adminPostRepository.findAllPosts(pageable)
                : adminPostRepository.findPostsByUserId(userId, pageable);

        return results.map(this::mapToPostDTO);
    }

    @Override
    public void updatePost(PostDTO dto) {
        adminPostRepository.updatePost(dto.getBno(), dto.getTitle(), dto.getContent());
    }

    @Override
    public void deletePost(Long bno) {
        adminPostRepository.deleteByBno(bno);
    }

    private PostDTO mapToPostDTO(Object[] row) {
        return PostDTO.builder()
                .bno(((Number) row[0]).longValue())
                .title((String) row[1])
                .content((String) row[2])
                .boardType((String) row[3])
                .userId((String) row[4])
                .nickname((String) row[5])
                .regdate((java.time.LocalDateTime) row[6])
                .moddate((java.time.LocalDateTime) row[7])
                .visitcount(((Number) row[8]).intValue())
                .build();
    }
}