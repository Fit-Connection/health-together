# Health Together 🏋️‍♂️🤝  

**Health Together**는 운동 커뮤니티 플랫폼입니다. 운동을 하고 싶어도 함께할 사람을 찾기 어렵거나, 운동시설을 찾기 번거로워하는 사람들을 위해 기획되었습니다.  
사용자가 실시간으로 주변 운동시설을 찾고, 운동 파트너를 모집하며, 함께 운동할 수 있도록 돕는 것을 목표로 합니다.

---

## 📋 **목차**
1. [프로젝트 소개](#-프로젝트-소개)
2. [주요 기능](#-주요-기능)
3. [기술 스택](#-기술-스택)
4. [프로젝트 구조](#-프로젝트-구조)
   - [백엔드 구조](#백엔드-구조)
   - [프론트엔드 구조](#프론트엔드-구조)
5. [설치 및 실행 방법](#-설치-및-실행-방법)
6. [팀원 정보](#-팀원-정보)

---

## 🌟 **프로젝트 소개**
Health Together는 건강한 라이프스타일을 추구하는 사용자들이 함께 운동하며 소셜 활동을 즐길 수 있도록 설계된 플랫폼입니다.  
- 사용자들은 팀을 구성하고 챌린지에 참여할 수 있습니다.
- 친구 추가, 팀 채팅, 좋아요 등 다양한 소셜 기능을 제공합니다.

---

## 🔑 **주요 기능**
### **사용자**
- 로그인/회원가입
- 이메일 인증 및 비밀번호 찾기
- 사용자 프로필 관리

### **팀과 챌린지**
- 팀 생성, 수정, 삭제 및 모집 관리
- 운동 대회, 이벤트 및 미션 참여

### **운동 시설**
- 운동 시설 검색 및 모임 개설
- 위치 기반 시설 정보 제공

### **소셜**
- 친구 추가 및 관리
- 팀 멤버 간 채팅
- 좋아요 기능

---

## 💻 **기술 스택**

### **프론트엔드**
- **Vue.js**: 컴포넌트 기반 프레임워크
- **Pinia**: 상태 관리 라이브러리
- **Axios**: HTTP 클라이언트
- **Bootstrap**: UI 스타일링

### **백엔드**
- **Spring Boot**: 백엔드 애플리케이션 프레임워크
- **MyBatis**: SQL Mapper 라이브러리
- **Spring Security**: 인증 및 권한 관리
- **Redis**: 이메일 인증 코드 캐싱
- **MySQL**: 데이터베이스

### **기타**
- **AWS S3**: 파일 업로드/저장
- **CloudFront**: 정적 파일 CDN
- **Kakao API**: 지도/위치 서비스

---

## 📁 **프로젝트 구조**

### 백엔드 구조
<details>
<summary>📂 <strong>클릭하여 백엔드 구조 보기</strong></summary>

```
back-end/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.ssafy.health/
│   │   │       ├── config/                    # 설정 파일 (AWS, Redis 등)
│   │   │       ├── controller/               # API 엔드포인트
│   │   │       ├── dao/                      # MyBatis 레포지토리
│   │   │       ├── domain/                   # 엔티티 클래스
│   │   │       ├── dto/                      # 요청 및 응답 데이터 전송 객체
│   │   │       ├── emailverify/              # 이메일 인증 모듈
│   │   │       ├── service/                  # 비즈니스 로직
│   │   │       └── HealthApplication.java    # 메인 애플리케이션
│   │   ├── resources/
│   │   │   ├── mybatis.mapper/               # MyBatis XML 매퍼
│   │   │   └── application.properties        # 환경 설정 파일
├── test/                                     # 테스트 코드
└── pom.xml                                   # Maven 설정




back-end/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.ssafy.health/
│   │   │       ├── config/
│   │   │       │   ├── AwsS3Config.java
│   │   │       │   ├── EmailConfig.java
│   │   │       │   ├── MyBatisConfig.java
│   │   │       │   ├── RedisConfig.java
│   │   │       │   ├── WebConfig.java
│   │   │       │   └── WebSocketConfig.java
│   │   │       ├── controller/
│   │   │       │   ├── AwsS3Controller.java
│   │   │       │   ├── ChallengeController.java
│   │   │       │   ├── ChatController.java
│   │   │       │   ├── FacilityController.java
│   │   │       │   ├── FacilityLocationController.java
│   │   │       │   ├── FriendController.java
│   │   │       │   ├── LikeController.java
│   │   │       │   ├── ProfileController.java
│   │   │       │   ├── TeamController.java
│   │   │       │   ├── TeamMemberController.java
│   │   │       │   └── UserController.java
│   │   │       ├── dao/
│   │   │       │   ├── ChallengeRepository.java
│   │   │       │   ├── ChatMessageRepository.java
│   │   │       │   ├── FacilityLocationRepository.java
│   │   │       │   ├── FacilityRepository.java
│   │   │       │   ├── FriendRepository.java
│   │   │       │   ├── LikeRepository.java
│   │   │       │   ├── ProfileRepository.java
│   │   │       │   ├── TeamMemberRepository.java
│   │   │       │   ├── TeamRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── domain/
│   │   │       │   ├── ActivityScore.java
│   │   │       │   ├── AwsS3.java
│   │   │       │   ├── Challenge.java
│   │   │       │   ├── Character.java
│   │   │       │   ├── Facility.java
│   │   │       │   ├── FacilityLocation.java
│   │   │       │   ├── Friend.java
│   │   │       │   ├── Like.java
│   │   │       │   ├── Message.java
│   │   │       │   ├── Payment.java
│   │   │       │   ├── Profile.java
│   │   │       │   ├── Reservation.java
│   │   │       │   ├── Review.java
│   │   │       │   ├── Team.java
│   │   │       │   ├── TeamMember.java
│   │   │       │   └── User.java
│   │   │       ├── dto/
│   │   │       │   ├── request/
│   │   │       │   │   ├── emailcheck/
│   │   │       │   │   │   ├── EmailCheckRequest.java
│   │   │       │   │   │   ├── EmailCheckResponse.java
│   │   │       │   │   ├── ChangePasswordRequest.java
│   │   │       │   │   ├── ChatMessage.java
│   │   │       │   │   ├── FacilityLocationRequest.java
│   │   │       │   │   ├── FacilityRequest.java
│   │   │       │   │   ├── LikeRequest.java
│   │   │       │   │   ├── ProfileDto.java
│   │   │       │   │   ├── TeamMemberRequest.java
│   │   │       │   │   ├── TeamRequest.java
│   │   │       │   │   └── UserDto.java
│   │   │       │   ├── response/
│   │   │       │       ├── ChallengeResponse.java
│   │   │       │       ├── FacilityLocationResponse.java
│   │   │       │       ├── FacilityResponse.java
│   │   │       │       ├── LikeResponse.java
│   │   │       │       ├── TeamMemberResponse.java
│   │   │       │       └── TeamResponse.java
│   │   │       ├── emailverify/
│   │   │       │   ├── Email.java
│   │   │       │   ├── EmailController.java
│   │   │       │   ├── EmailDto.java
│   │   │       │   ├── EmailRepository.java
│   │   │       │   ├── EmailService.java
│   │   │       │   └── RedisUtil.java
│   │   │       ├── service/
│   │   │       │   ├── AwsS3Service.java
│   │   │       │   ├── ChallengeService.java
│   │   │       │   ├── ChatMessageService.java
│   │   │       │   ├── FacilityLocationService.java
│   │   │       │   ├── FacilityLocationServiceImpl.java
│   │   │       │   ├── FacilityService.java
│   │   │       │   ├── FacilityServiceImpl.java
│   │   │       │   ├── FriendService.java
│   │   │       │   ├── LikeService.java
│   │   │       │   ├── LikeServiceImpl.java
│   │   │       │   ├── ProfileService.java
│   │   │       │   ├── TeamMemberService.java
│   │   │       │   ├── TeamMemberServiceImpl.java
│   │   │       │   ├── TeamService.java
│   │   │       │   ├── TeamServiceImpl.java
│   │   │       │   └── UserService.java
│   │   │       └── HealthApplication.java
│   │   ├── resources/
│   │   │   ├── mybatis.mapper/
│   │   │   │   ├── ChallengeMapper.xml
│   │   │   │   ├── ChatMessageMapper.xml
│   │   │   │   ├── FacilityLocationMapper.xml
│   │   │   │   ├── FacilityMapper.xml
│   │   │   │   ├── FriendMapper.xml
│   │   │   │   ├── LikeMapper.xml
│   │   │   │   ├── ProfileMapper.xml
│   │   │   │   ├── TeamMapper.xml
│   │   │   │   ├── TeamMemberMapper.xml
│   │   │   │   └── UserMapper.xml
│   │   │   └── application.properties
├── test/
├── target/
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

</details>

---

### 프론트엔드 구조
<details>
<summary>📂 <strong>클릭하여 프론트엔드 구조 보기</strong></summary>

```
front-end/
├── public/                  # 정적 파일 디렉토리
│   ├── favicon.ico
│   └── index.html
├── src/                     # 소스 코드 디렉토리
│   ├── assets/              # 정적 자산 파일 (이미지 등)
│   ├── components/          # Vue 컴포넌트 디렉토리
│   │   ├── auth/            # 로그인/회원가입 컴포넌트
│   │   ├── challenge/       # 챌린지 관련 컴포넌트
│   │   ├── chat/            # 채팅 관련 컴포넌트
│   │   ├── common/          # 공용 헤더 및 푸터
│   │   ├── friend/          # 친구 관련 컴포넌트
│   │   ├── main/            # 팀 관리 관련 컴포넌트
│   │   ├── mypage/          # 마이페이지 관련 컴포넌트
│   ├── router/              # Vue Router 설정
│   ├── store/               # Pinia 상태 관리
│   ├── views/               # 전체 페이지 뷰
│   ├── App.vue              # 루트 Vue 컴포넌트
│   └── main.js              # 진입점
├── .env                     # 환경변수 파일
└── package.json             # 프로젝트 종속성 및 스크립트




front-end/
├── node_modules/         # Node.js 패키지 모듈
├── public/               # 정적 파일 디렉토리
│   ├── favicon.ico
│   └── index.html
├── src/                  # 소스 코드 디렉토리
│   ├── assets/           # 정적 자산 파일
│   │   ├── default-profile.png
│   │   ├── football.jpg
│   │   ├── logo.png
│   │   ├── people.png
│   │   └── Weblogo.png
│   ├── components/       # Vue 컴포넌트 디렉토리
│   │   ├── auth/
│   │   │   ├── LoginForm.vue
│   │   │   └── RegisterForm.vue
│   │   ├── challenge/
│   │   │   ├── ChallengeTeam.vue
│   │   │   └── CreateChallengeModal.vue
│   │   ├── chat/
│   │   │   ├── ChatFriend.vue
│   │   │   ├── ChatList.vue
│   │   │   └── ChatTeam.vue
│   │   ├── common/       # 공용 컴포넌트
│   │   │   ├── footer/
│   │   │   │   ├── AppFooter.vue
│   │   │   │   └── DetailFooter.vue
│   │   │   ├── header/
│   │   │   │   ├── AppHeader.vue
│   │   │   │   ├── CreateHeader.vue
│   │   │   │   └── DetailHeader.vue
│   │   ├── friend/
│   │   │   ├── FriendList.vue
│   │   │   ├── FriendPage.vue
│   │   │   ├── FriendRequestForm.vue
│   │   │   ├── FriendRequests.vue
│   │   │   └── FriendSearch.vue
│   │   ├── headerfunction/
│   │   │   ├── LikePage.vue
│   │   │   ├── NotificationPage.vue
│   │   │   └── SearchPage.vue
│   │   ├── main/
│   │   │   ├── CreateTeam.vue
│   │   │   ├── TeamDetail.vue
│   │   │   └── TeamSearch.vue
│   │   ├── map/
│   │   │   └── MapSearchModal.vue
│   │   ├── mypage/
│   │   │   ├── MyPageMain.vue
│   │   │   ├── MyPageMyGroup.vue
│   │   │   ├── MyPagePastGroup.vue
│   │   │   ├── MyPageWishGroup.vue
│   │   │   ├── ActivityTracker.vue
│   │   │   ├── ChallengePage.vue
│   │   │   ├── FriendsList.vue
│   │   │   ├── PasswordRecovery.vue
│   │   │   └── TeamReservation.vue
│   ├── router/           # Vue Router 설정
│   │   └── index.js
│   ├── store/            # Vuex 또는 Pinia 스토어
│   │   ├── index.js
│   │   └── likeStore.js
│   ├── utils/            # 유틸리티 함수
│   │   └── kakao.js
│   ├── views/            # 전체 페이지 뷰
│   │   ├── LoginView.vue
│   │   └── RegisterView.vue
│   ├── App.vue           # 루트 Vue 컴포넌트
│   ├── main.js           # 진입점
├── .env                  # 환경변수 파일
├── .gitignore            # Git 제외 파일
├── babel.config.js       # Babel 설정
├── front-end.iml         # IntelliJ IDEA 설정 파일
├── jsconfig.json         # JavaScript 설정
├── package.json          # 프로젝트 종속성 및 스크립트
├── package-lock.json     # 종속성 잠금 파일
└── vue.config.js         # Vue CLI 설정

```

</details>

---

## 🚀 **설치 및 실행 방법**

### 1. **백엔드**
1. **환경 변수 설정**  
   `.env` 파일을 생성하고 다음과 같은 키를 설정합니다:
   ```env
   AWS_ACCESS_KEY=${AWS_ACCESS_KEY}
   AWS_SECRET_KEY=${AWS_SECRET_KEY}
   DATABASE_URL=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?serverTimezone=UTC
   DATABASE_USERNAME=${DB_USERNAME}
   DATABASE_PASSWORD=${DB_PASSWORD}
   EMAIL_HOST=${EMAIL_HOST}
   EMAIL_PORT=${EMAIL_PORT}
   EMAIL_USERNAME=${EMAIL_USERNAME}
   EMAIL_PASSWORD=${EMAIL_PASSWORD}
   ```
2. **프로젝트 빌드 및 실행**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

### 2. **프론트엔드**
1. **의존성 설치**
   ```bash
   npm install
   ```
2. **환경 변수 설정**  
   `.env` 파일을 생성하고 API URL 및 지도 API 키를 설정합니다:
   ```env
   VUE_APP_API_URL=http://localhost:9090/api
   VUE_APP_KAKAO_MAP_API_KEY=${KAKAO_MAP_API_KEY}
   ```
3. **개발 서버 실행**
   ```bash
   npm run serve
   ```

---

## 👥 **팀원 정보**
- **팀장**: 김강민 ([이메일](mailto:lnewgatel@gmail.com))
- **팀원**: 김범준 ([이메일](mailto:kbj7565@naver.com))

---
