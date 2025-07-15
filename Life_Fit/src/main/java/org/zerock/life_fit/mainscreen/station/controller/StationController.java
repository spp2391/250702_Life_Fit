package org.zerock.life_fit.mainscreen.station.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.mainscreen.station.domain.BusanStation;
import org.zerock.life_fit.mainscreen.station.dto.StationSimpleDTO;
import org.zerock.life_fit.mainscreen.station.repository.StationRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StationController {

    private final StationRepository busanStationRepository;

    @GetMapping("/stations")
    public List<StationSimpleDTO> getStations() {
        return busanStationRepository.findAll().stream()
                .map(station -> new StationSimpleDTO(
                        spliceText(station.getStationName()),
                        spliceText(station.getStationAddress()).replace("부산광역시", "")
                ))
                .collect(Collectors.toList());
    }

    public String spliceText(String text) {
        return (text.contains("(") ? text.substring(0, text.indexOf("(")) : text);
    }
}
