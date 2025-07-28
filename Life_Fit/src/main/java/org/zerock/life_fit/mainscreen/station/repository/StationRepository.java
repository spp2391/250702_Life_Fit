package org.zerock.life_fit.mainscreen.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.mainscreen.station.domain.BusanStation;

public interface StationRepository extends JpaRepository<BusanStation, Long> {
}
