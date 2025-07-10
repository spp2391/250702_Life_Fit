package org.zerock.life_fit.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.notice.domain.NoticeBoard;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

}
