package org.zerock.life_fit.mainscreen.cctv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.mainscreen.cctv.domain.CCTV;

public interface CCTVRepository extends JpaRepository<CCTV, Long> {
}
