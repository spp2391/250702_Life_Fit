package org.zerock.life_fit.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.life_fit.admin.dto.PostDTO;

public interface AdminPostService {
    Page<PostDTO> getPosts(String userId, Pageable pageable);
    void updatePost(PostDTO dto);
    void deletePost(Long bno);
}
