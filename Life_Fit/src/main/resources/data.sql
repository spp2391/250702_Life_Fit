-- 지역 카테고리 (중복 방지 삽입)
INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 1, '중구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 1);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 2, '서구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 2);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 3, '동구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 3);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 4, '영도구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 4);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 5, '부산진구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 5);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 6, '동래구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 6);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 7, '남구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 7);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 8, '북구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 8);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 9, '해운대구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 9);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 10, '사하구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 10);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 11, '금정구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 11);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 12, '강서구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 12);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 13, '연제구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 13);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 14, '수영구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 14);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 15, '사상구') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 15);

INSERT INTO localcate(localnum, localname)
SELECT * FROM (SELECT 16, '기장군') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM localcate WHERE localnum = 16);

-- 🔒 FREEBOARD INSERT는 현재 주석 처리 상태 유지 (문제 없이 서버 실행되도록)
-- 필요 시 중복 방지 조건으로 다시 활성화해드릴 수 있습니다.
