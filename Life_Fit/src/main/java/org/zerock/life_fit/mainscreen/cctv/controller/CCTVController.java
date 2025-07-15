package org.zerock.life_fit.mainscreen.cctv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.mainscreen.cctv.domain.CCTV;
import org.zerock.life_fit.mainscreen.cctv.dto.CCTVDTO;
import org.zerock.life_fit.mainscreen.cctv.repository.CCTVRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CCTVController {

    private final CCTVRepository cctvRepository;

    @GetMapping("/cctv")
    public List<CCTVDTO> getCCTVList() {
        return cctvRepository.findAll().stream()
                .map(cctv -> new CCTVDTO(
                        "CCTV",
                        cctv.getAddressName()
                ))
                .collect(Collectors.toList());
    }
}
