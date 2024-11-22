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
            <img :src="request.profileImage || defaultImage" alt="Profile" class="profile-img" />
            <div class="request-info">
              <!-- 친구 정보 -->
              <div class="profile-details">
                <p><strong>이름:</strong> {{ request.name || "알 수 없음" }}</p>
                <p><strong>지역:</strong> {{ request.location || "지역 정보 없음" }}</p>
                <p><strong>선호 종목:</strong> {{ request.sports || "선호 종목 없음" }}</p>
              </div>
            </div>
            <!-- 액션 버튼 -->
            <div class="request-actions">
              <button @click="respondToRequest(request.userId, 'ACCEPTED')" class="accept-button">
                수락
              </button>
              <button @click="respondToRequest(request.userId, 'REJECTED')" class="reject-button">
                거절
              </button>
            </div>
          </div>
        </li>
      </ul>
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
      defaultImage: require('@/assets/Weblogo.png') // 기본 프로필 이미지
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
                const profileResponse = await axios.get(`http://localhost:9090/api/profiles/${request.userId}`);
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

.profile-details p {
  margin: 5px 0;
  font-size: 0.9rem;
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

.accept-button, .reject-button {
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

.reject-button {
  background-color: #ffffff;
  color: #c82333;
  border-color: #c82333;
}

.reject-button:hover {
  color: white;
  background-color: #c82333;
}
</style>
