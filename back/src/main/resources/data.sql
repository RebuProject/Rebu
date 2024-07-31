INSERT INTO member (birth, id, reg_date, email, name, password, gender, status)
VALUES
    ('1990-01-01', 1, '2023-07-30 12:00:00.000000', 'example1@example.com', '홍길동', 'password123', 'MALE', 'ROLE_NORMAL'),
    ('1985-05-15', 2, '2023-07-30 12:30:00.000000', 'example2@example.com', '김영희', 'password456', 'FEMALE', 'ROLE_DORMANT'),
    ('2000-12-10', 3, '2023-07-30 13:00:00.000000', 'example3@example.com', '이철수', 'password789', 'MALE', 'ROLE_DELETED');

INSERT INTO profile (is_private, member_id, recent_time, phone, nickname, introduction, image_src, status, type)
VALUES
    (b'0', 1, '2023-07-30 12:00:00.000000', '010-1234-5678', 'gildong', '안녕하세요, 저는 홍길동입니다.', '/images/gildong.png', 'ROLE_NORMAL', 'COMMON'),
    (b'1', 2, '2023-07-30 12:30:00.000000', '010-2345-6789', 'younghee', '반갑습니다, 김영희입니다.', '/images/younghee.png', 'ROLE_NORMAL', 'COMMON'),
    (b'0', 3, '2023-07-30 13:00:00.000000', '010-3456-7890', 'cheolsu', '안녕하세요, 이철수입니다.', '/images/cheolsu.png', 'ROLE_NORMAL', 'COMMON'),
    (b'0', 1, '2023-07-31 09:00:00.000000', '010-4567-8901', 'donggil', '저는 동길입니다.', '/images/donggil.png', 'ROLE_NORMAL', 'EMPLOYEE'),
    (b'1', 2, '2023-07-31 10:00:00.000000', '010-5678-9012', 'heecheol', '김희철입니다. 만나서 반갑습니다.', '/images/heecheol.png', 'ROLE_NORMAL', 'EMPLOYEE'),
    (b'0', 3, '2023-07-31 11:00:00.000000', '010-6789-0123', 'suyoung', '저는 수영입니다.', '/images/suyoung.png', 'ROLE_NORMAL', 'SHOP');

INSERT INTO employee_profile (id, shop_id)
VALUES
    (4, 6),
    (5, 6);


