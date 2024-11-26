CREATE SCHEMA `TESTDB`;
USE TESTDB;

-- 1. 사용자 테이블
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
);

-- 2. 운동 시설 예약 테이블
CREATE TABLE facilities (
    facility_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL, -- 시설 이름
    location VARCHAR(255), -- 시설 상세 주소
    type VARCHAR(50), -- 시설 종류 (예: 풋살장, 농구장)
    contact VARCHAR(50), -- 시설 연락처
    operating_hours VARCHAR(100) DEFAULT NULL, -- 운영 시간
    availability BOOLEAN DEFAULT TRUE, -- 사용 가능 여부
    rating_score DECIMAL(3, 2) DEFAULT 0.0, -- 평균 평점
    review_count INT DEFAULT 0, -- 리뷰 개수
    popularity INT DEFAULT 0, -- 예약 횟수
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. 시설 위치 테이블
CREATE TABLE facilities_locations (
    facility_id INT PRIMARY KEY, -- 시설 ID
    latitude DECIMAL(10, 8) NOT NULL, -- 위도
    longitude DECIMAL(11, 8) NOT NULL, -- 경도
    status BOOLEAN DEFAULT TRUE, -- 위치 유효 여부
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE CASCADE
);

-- 4. 팀 모집 테이블
CREATE TABLE teams (
    team_id INT AUTO_INCREMENT PRIMARY KEY,
    writer_id INT NOT NULL,
    team_name VARCHAR(100) NOT NULL,
    sport_type VARCHAR(50) NOT NULL,
    description TEXT,
    team_image_url VARCHAR(255),
    max_members INT NOT NULL,
    current_members INT DEFAULT 0,
    facility_id INT DEFAULT NULL, -- 시설과 연결
    meeting_date DATETIME DEFAULT NULL,
    fee VARCHAR(50) DEFAULT '무료',
    age_limit VARCHAR(20) DEFAULT '제한 없음',
    preparing TEXT DEFAULT NULL,
    rule TEXT DEFAULT NULL,
    invitations TEXT DEFAULT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (writer_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE SET NULL
);

-- 5. 팀원 모집 테이블
CREATE TABLE team_members (
    team_member_id INT AUTO_INCREMENT PRIMARY KEY,
    team_id INT NOT NULL,
    user_id INT NOT NULL,
    joined_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 마지막 변경 시간
    status ENUM('ACTIVE', 'LEFT', 'PENDING', 'REMOVED') DEFAULT 'ACTIVE', -- 상태 추가
    FOREIGN KEY (team_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 6. 친구 테이블
CREATE TABLE friends (
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED') DEFAULT 'PENDING',
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 7. 챌린지 테이블
CREATE TABLE challenges (
    challenge_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    goal VARCHAR(100) NOT NULL,
    reward VARCHAR(100),
    status ENUM('ONGOING', 'COMPLETED') DEFAULT 'ONGOING',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 8. 개인 프로필
CREATE TABLE profile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL, -- 사용자 고유 ID (유저users 테이블과 연동)
    name VARCHAR(255) NULL,
    location VARCHAR(255) NULL,
    sports VARCHAR(255) NULL,
    introduce VARCHAR(255) NULL,
    profile_image VARCHAR(255) NULL, -- 저장된 이미지 경로
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 9. 팀 채팅
CREATE TABLE chat_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,       -- 메시지 고유 ID
    sender VARCHAR(255) NOT NULL,               -- 메시지 보낸 사람
    content TEXT NOT NULL,                      -- 메시지 내용
    room_id VARCHAR(255) NOT NULL,              -- 채팅방 ID
    timestamp DATETIME NOT NULL DEFAULT NOW()   -- 메시지 전송 시간
);

-- 10. 좋아요 테이블
CREATE TABLE likes (
    like_id INT AUTO_INCREMENT PRIMARY KEY, -- 고유 ID
    user_id INT NOT NULL, -- 좋아요를 누른 사용자 ID
    team_id INT DEFAULT NULL, -- 좋아요한 팀 (NULL 허용: 팀에만 국한되지 않도록 확장 가능)
    facility_id INT DEFAULT NULL, -- 좋아요한 시설 (선택적으로 추가 가능)
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- 좋아요 누른 시간
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE CASCADE,
    CONSTRAINT unique_like UNIQUE (user_id, team_id, facility_id) -- 사용자별 중복 좋아요 방지
);

INSERT INTO users (username, email, password, phone, status)
VALUES 
('john_doe', 'john@example.com', 'password123', '010-1234-5678', 'ACTIVE'),
('jane_smith', 'jane@example.com', 'password123', '010-2345-6789', 'ACTIVE'),
('alex_kim', 'alex@example.com', 'password123', '010-3456-7890', 'INACTIVE'),
('sskiee', 'sskiee@example.com', '1234', '010-9915-8238', 'INACTIVE');

INSERT INTO facilities (name, location, type, contact, operating_hours, availability, rating_score, review_count, popularity)
VALUES 
('서울 풋살장', '서울시 강남구', '풋살장', '010-1234-5678', '09:00-21:00', TRUE, 4.5, 10, 50),
('강남 농구장', '서울시 강남구', '농구장', '010-2345-6789', '08:00-20:00', TRUE, 4.2, 8, 30),
('홍대 배드민턴장', '서울시 마포구', '배드민턴장', '010-3456-7890', '10:00-22:00', FALSE, 4.8, 12, 20);

INSERT INTO facilities_locations (facility_id, latitude, longitude, status)
VALUES 
(1, 37.5665, 126.9780, TRUE), -- 서울 풋살장
(2, 37.4979, 127.0276, TRUE), -- 강남 농구장
(3, 37.5563, 126.9220, TRUE); -- 홍대 배드민턴장

INSERT INTO teams 
(writer_id, team_name, sport_type, description, max_members, current_members, facility_id, meeting_date, fee, age_limit, preparing, rule, invitations)
VALUES 
(1, '강남 풋살팀', '풋살', '주말 풋살팀입니다. 매주 토요일에 모여요.', 10, 3, 1, '2024-11-10 15:00:00', '5000원', '20~40세', '풋살화, 운동복', '1. 부상 방지\n2. 준비 운동 필수\n3. 즐겁게 하기', '팔로우 환영합니다! 팀 채팅으로 문의주세요.'),
(2, '서초 농구팀', '농구', '평일 저녁 농구 모임입니다.', 8, 2, 2, '2024-11-12 18:00:00', '무료', '제한 없음', '농구화, 물', '1. 예의 준수\n2. 시간 엄수\n3. 적극적인 참여', '매주 모임에 함께할 분을 모집합니다.');

INSERT INTO team_members (team_id, user_id, status)
VALUES 
(1, 2, 'ACTIVE'),
(1, 3, 'ACTIVE'),
(1, 4, 'PENDING'),
(2, 1, 'ACTIVE'),
(2, 3, 'PENDING');

INSERT INTO friends (user_id, friend_id, status)
VALUES 
(1, 2, 'PENDING'),
(1, 3, 'PENDING'),
(2, 3, 'PENDING');

INSERT INTO challenges (user_id, goal, reward, status)
VALUES 
(1, '일주일에 한 번 운동하기', '포인트 100점', 'ONGOING'),
(2, '다양한 운동 시도해 보기', '포인트 200점', 'COMPLETED');

INSERT INTO profile (user_id, name, location, sports, profile_image, created_at, updated_at)
SELECT user_id, NULL, NULL, NULL, NULL, NOW(), NOW()
FROM users
WHERE user_id NOT IN (SELECT user_id FROM profile);

INSERT INTO likes (user_id, team_id, facility_id)
VALUES 
(1, 1, NULL),  -- user_id 1이 team_id 1에 좋아요
(2, 2, NULL),  -- user_id 2가 team_id 2에 좋아요
(3, NULL, 1),  -- user_id 3이 facility_id 1에 좋아요
(4, NULL, 2),  -- user_id 4가 facility_id 2에 좋아요
(1, NULL, 3);  -- user_id 1이 facility_id 3에 좋아요
