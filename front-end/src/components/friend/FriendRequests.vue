<template>
  <div class="friend-requests-container">
    <div v-if="friendRequests.length === 0" class="no-requests">
      <p>친구 요청이 없습니다.</p>
    </div>
    <div v-else>
      <h2 class="page-title">친구 요청</h2>
      <hr />
      <ul class="friend-request-list">
        <li v-for="request in friendRequests" :key="request.userId" class="friend-request-card">
          <div class="friend-request-card-content">
            <!-- 프로필 이미지 -->
            <img :src="request.profileImage || defaultImage" alt="Profile" class="profile-img" @click="openProfilePopup(request.userId)"/>
            <div class="request-info">
              <!-- 친구 정보 -->
              <div class="profile-details">
                <h3><strong>{{ request.name }}</strong></h3>
              </div>
            </div>
            <!-- 액션 버튼 -->
            <div class="request-actions">
              <button @click="respondToRequest(request.friendId, 'ACCEPTED')" class="accept-button">수락</button>
              <button @click="respondToRequest(request.friendId, 'REJECTED')" class="reject-button">거절</button>
            </div>
          </div>
        </li>
      </ul>
    </div>

    <div v-if="showPopup" class="profile-popup">
      <div class="popup-content">
        <h3>프로필</h3>
        <hr>
        <img :src="selectedProfile.profileImage || defaultImage" alt="Profile Image" class="profile-img"/>
        <p><strong>{{ selectedProfile.name }}</strong></p>
        <p><strong>{{ selectedProfile.location }}</strong></p>
        <p><strong>{{ selectedProfile.sports }}</strong></p>
        <p>{{ selectedProfile.introduce }}</p>
        <button class="close-button" @click="closePopup">닫기</button>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userId: localStorage.getItem("userId"),
      friendRequests: [], // 친구 요청 목록
      defaultImage: require('@/assets/Weblogo.png'), // 기본 프로필 이미지
      showPopup: false, // 팝업 표시 여부
      selectedProfile: {}, // 선택된 프로필 데이터
    };
  },
  methods: {
    // 친구 요청 가져오기 및 프로필 정보 확장
    async fetchFriendRequests() {
      try {
        const response = await axios.get(`http://localhost:9090/api/friends/requests/${this.userId}`);
        const requests = response.data;

        // 각 요청에 대한 프로필 정보 가져오기
        const requestsWithProfiles = await Promise.all(
            requests.map(async (request) => {
              try {
                const profileResponse = await axios.get(`http://localhost:9090/api/profiles/${request.friendId}`);
                return { ...request, ...profileResponse.data }; // 요청 데이터와 프로필 데이터 병합
              } catch (error) {
                console.error(`Error fetching profile for userId ${request.userId}:`, error);
                return { ...request }; // 실패 시 요청 데이터만 반환
              }
            })
        );

        this.friendRequests = requestsWithProfiles;
      } catch (error) {
        console.error("Error fetching friend requests:", error);
      }
    },
    // 팝업 열기
    async openProfilePopup(friendId) {
      try {
        const response = await axios.get(`http://localhost:9090/api/profiles/${friendId}`);
        this.selectedProfile = response.data; // 선택된 친구의 프로필 데이터
        this.showPopup = true; // 팝업 표시
      } catch (error) {
        console.error("Error fetching friend's profile:", error);
      }
    },
    // 팝업 닫기
    closePopup() {
      this.showPopup = false;
      this.selectedProfile = {}; // 선택된 프로필 데이터 초기화
    },
    // 친구 요청 응답
    async respondToRequest(friendId, status) {
      try {
        await axios.post(`http://localhost:9090/api/friends/respond/${this.userId}/${friendId}/${status}`);
        await this.fetchFriendRequests(); // 요청 목록 갱신
      } catch (error) {
        console.error("Error responding to request:", error);
      }
    }
  },
  // 컴포넌트 로드 시 친구 요청 가져오기
  mounted() {
    this.fetchFriendRequests();
  }
};
</script>

<style scoped>
/* 전체 컨테이너 스타일 */
.friend-requests-container {
  max-width: 600px;
  padding: 20px;
  margin: 0 auto;
  border-radius: 8px;
}

/* 제목 스타일 */
.page-title {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

/* 친구 요청이 없는 경우 메시지 */
.no-requests {
  text-align: center;
  color: #888;
  font-size: 1.2rem;
}

/* 요청 목록 스타일 */
.friend-request-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

/* 요청 카드 스타일 */
.friend-request-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

/* 카드 내용 */
.friend-request-card-content {
  display: flex;
  align-items: center;
  gap: 15px;
  flex: 1; /* 정보 영역 확장 */
}

/* 요청 정보 */
.request-info {
  display: flex;
  gap: 10px;
  align-items: center;
}

.profile-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 50%;
  border: 1px solid #ddd;
}

.profile-details {
  display: flex;
  flex-direction: column;
}

.profile-details h3 {
  margin: 5px 0;
  font-size: 1.2rem;
  color: #555;
}

/* 팝업 스타일 */
.profile-popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.profile-popup h3 {
  margin: 0;
  font-size: 1.2rem;
  color: #007BFF;
  cursor: pointer;
}

.popup-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.popup-content h3 {
  margin-top: 0;
  color: #333;
}

.popup-content p {
  margin: 10px 0;
  font-size: 1rem;
  color: #555;
}

/* 요청 액션 버튼들 */
.request-actions {
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 버튼 정렬 */
  margin-left: auto; /* 오른쪽 끝으로 밀기 */
  gap: 10px;
}

.accept-button, .reject-button, .close-button{
  padding: 8px 15px;
  border: 1px solid;
  border-radius: 4px;
  font-size: 0.9rem;
  cursor: pointer;
}

.accept-button {
  background-color: #ffffff;
  color: #0056b3;
  border-color: #0056b3;
}

.accept-button:hover {
  color: white;
  background-color: #0056b3;
}

.reject-button, .close-button{
  background-color: #ffffff;
  color: #c82333;
  border-color: #c82333;
}

.reject-button:hover, .close-button:hover{
  color: white;
  background-color: #c82333;
}
</style>
