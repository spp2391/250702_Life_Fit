package org.zerock.life_fit.board.service;

import org.zerock.life_fit.board.domain.LocalCate;
import org.zerock.life_fit.board.repository.LocalCateRepository;

import java.util.List;

public class LocalCateService {
    private final LocalCateRepository localCateRepository;

    public LocalCateService(LocalCateRepository localCateRepository) {
        this.localCateRepository = localCateRepository;
    }

    public List<LocalCate> getAllLocalCates() {
        return localCateRepository.findAll();
    }

    public LocalCate getLocalCate(Long localnum) {
        return localCateRepository.findById(localnum)
                .orElseThrow(() -> new IllegalArgumentException("지역이 존재하지 않습니다."));
    }
}
