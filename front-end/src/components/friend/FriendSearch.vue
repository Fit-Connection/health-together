<template>
  <div class="search-page-container">
    <h2>사용자 검색</h2>
    <hr />
    <!-- 검색 결과 영역 -->
    <div class="result-container">
      <div v-if="searchResult" class="result-card">
        <p class="result-username">{{ searchResult.username }}</p>
        <button @click="sendFriendRequest(searchResult.userId)" class="request-button">
          친구 요청
        </button>
      </div>
      <div v-else>
        <p>검색 결과가 없습니다.</p>
      </div>
    </div>

    <!-- 검색 입력란 -->
    <div class="search-input-bar">
      <input
          v-model="searchEmail"
          placeholder="이메일로 친구 검색"
          class="search-input"
          @keyup.enter="searchFriend"
      />
      <button @click="searchFriend" class="search-button">검색</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      searchEmail: "jane@example.com", // 기본 검색값 (테스트용)
      searchResult: null // 검색 결과
    };
  },
  methods: {
    // 이메일로 친구 검색
    async searchFriend() {
      try {
        const response = await axios.get(`http://localhost:9090/user/api/search/${this.searchEmail}`);
        this.searchResult = response.data;
      } catch (error) {
        console.error("Error searching friend:", error);
        this.searchResult = null;
      }
    },
    async sendFriendRequest(friendId) {
      const userId = localStorage.getItem("userId");
      try {
        await axios.post(`http://localhost:9090/api/friends/request/${userId}/${friendId}`);
        alert("친구 요청을 보냈습니다.");
        this.searchResult = null; // 검색 결과 초기화
      } catch (error) {
        console.error("Error sending friend request:", error);
      }
    }
  }
};
</script>

<style scoped>
/* 전체 페이지 컨테이너 */
.search-page-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

/* 제목 스타일 */
.search-page-container h2 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

/* 결과 컨테이너 */
.result-container {
  margin: 20px 0;
}

/* 결과 카드 스타일 */
.result-card {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 결과 이름 스타일 */
.result-username {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

/* 요청 버튼 스타일 */
.request-button {
  padding: 8px 15px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.request-button:hover {
  background-color: #218838;
}

/* 검색 입력란 바 (푸터 위에 고정) */
.search-input-bar {
  position: fixed;
  bottom: 80px; /* 푸터 위에 위치 */
  left: 0;
  right: 0;
  background-color: #fff;
  border-top: 1px solid #ddd;
  padding: 10px 20px;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  gap: 10px;
  align-items: center;
}

/* 검색 입력란 스타일 */
.search-input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

/* 검색 버튼 스타일 */
.search-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.search-button:hover {
  background-color: #0056b3;
}
</style>
