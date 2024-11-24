<template>
  <div class="search-page-container">
    <h2>사용자 검색</h2>
    <hr />
    <div class="result-container">
      <template v-if="searchResult.length">
        <div
            v-for="result in searchResult"
            :key="result.userId"
            class="friend-card"
        >
          <div class="friend-card-content">
            <!-- 프로필 이미지 -->
            <img
                :src="result.profileImage || defaultImage"
                alt="Profile Image"
                class="profile-img"
                @click="openProfilePopup(result.userId)"
            />
            <div class="friend-info">
              <h3><strong>{{ result.name }}</strong></h3>
            </div>
            <div class="friend-actions">
              <!-- 이미 친구인 경우 -->
              <template v-if="isFriend(result.userId)">
                <button
                    @click="startChat(result.userId)"
                    class="icon-button"
                >
                  <img src="https://super.so/icon/dark/send.svg" alt="메세지" class="icon" />
                </button>
              </template>
              <!-- 받은 친구 요청 상태 -->
              <template v-else-if="isPendingRequest(result.userId)">
                <button
                    @click="respondToRequest(result.userId, 'ACCEPTED')"
                    class="icon-button"
                >
                  <img src="https://super.so/icon/dark/user-check.svg" alt="수락" class="icon" />
                </button>
                <button
                    @click="respondToRequest(result.userId, 'REJECTED')"
                    class="icon-button"
                >
                  <img src="https://super.so/icon/dark/user-x.svg" alt="거절" class="icon" />
                </button>
              </template>
              <!-- 친구 요청 상태 -->
              <template v-else>
                <button
                    @click="toggleFriendRequest(result.userId)"
                    class="icon-button"
                >
                  <img
                      :src="result.requestSent ? 'https://super.so/icon/dark/user-minus.svg' : 'https://super.so/icon/dark/user-plus.svg'"
                      :alt="result.requestSent ? '요청 취소' : '친구 요청'"
                      class="icon"
                  />
                </button>
              </template>
            </div>
          </div>
        </div>
      </template>
      <p v-else>검색 결과가 없습니다.</p>
    </div>
    <div class="search-input-bar">
      <input
          v-model="searchName"
          placeholder="이름으로 사용자 검색"
          class="search-input"
          @keyup.enter="searchFriend"
      />
      <button @click="searchFriend" class="button button-blue">검색</button>
    </div>
    <div v-if="showPopup" class="profile-popup">
      <div class="popup-content">
        <h3 class="popup-head">프로필
          <button class="icon-button close-button" @click="closePopup">
            <img src="https://super.so/icon/dark/x.svg" alt="닫기" class="icon" />
          </button>
        </h3>
        <hr />
        <img
            :src="selectedProfile.profileImage || defaultImage"
            alt="Profile Image"
            class="profile-img"
        />
        <p><strong>{{ selectedProfile.name }}</strong></p>
        <p><strong>{{ selectedProfile.location }}</strong></p>
        <p><strong>{{ selectedProfile.sports }}</strong></p>
        <p>{{ selectedProfile.introduce }}</p>
        <p v-if="isFriend(selectedProfile.userId)" class="remove-button">
          <button
              @click="removeFriend(selectedProfile.userId)"
              class="icon-button"
          >
            <img src="https://super.so/icon/dark/user-minus.svg" alt="친구 끊기" class="icon" />
          </button>
        </p>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      searchName: "알렉스",
      searchResult: [],
      friends: [], // 친구 목록
      pendingRequests: [], // 받은 친구 요청 목록
      sentRequests: [], // 보낸 친구 요청 목록
      defaultImage: require("@/assets/Weblogo.png"),
      showPopup: false,
      selectedProfile: {},
    };
  },
  methods: {
    // 사용자 검색
    async searchFriend() {
      try {
        // 친구 요청 상태 동기화
        await Promise.all([this.fetchPendingRequests(), this.fetchSentRequests()]);

        // 사용자 검색
        const encodedName = encodeURIComponent(this.searchName);
        const response = await axios.get(
            `http://localhost:9090/api/profiles/search/${encodedName}`
        );

        this.searchResult = response.data.map((profile) => ({
          userId: profile.userId,
          name: profile.name || "알 수 없음",
          location: profile.location || "지역 정보 없음",
          sports: profile.sports || "선호 종목 없음",
          profileImage: profile.profileImage || this.defaultImage,
          introduce: profile.introduce || "",
          requestSent: this.sentRequests.includes(profile.userId), // 보낸 요청 상태 반영
        }));
      } catch (error) {
        console.error("Error searching friend:", error);
        this.searchResult = [];
      }
    },
    // 친구 목록 가져오기
    async fetchFriends() {
      try {
        const userId = localStorage.getItem("userId");

        const response = await axios.get(
            `http://localhost:9090/api/friends/list/${userId}`
        );
        this.friends = response.data.map((friend) => ({
          userId: friend.friendId,
        }));
      } catch (error) {
        console.error("Error fetching friends:", error);
      }
    },
    // 보낸 요청 목록 가져오기
    async fetchSentRequests() {
      try {
        const userId = localStorage.getItem("userId");
        const response = await axios.get(
            `http://localhost:9090/api/friends/requests/sent/${userId}`
        );

        // 필터링하여 userId 또는 friendId가 유효한 경우만 처리
        this.sentRequests = response.data
            .filter((request) => request.userId !== null || request.friendId !== null) // 유효한 데이터만 필터링
            .map((request) => request.userId || request.friendId); // userId가 없으면 friendId 사용
      } catch (error) {
        console.error("Error fetching sent requests:", error);
        this.sentRequests = [];
      }
    }
    ,
    // 받은 요청 목록 가져오기
    async fetchPendingRequests() {
      try {
        const userId = localStorage.getItem("userId");
        const response = await axios.get(
            `http://localhost:9090/api/friends/requests/${userId}`
        );
        this.pendingRequests = response.data
            .filter((request) => request.userId !== null || request.friendId !== null)
            .map((request) => request.userId || request.friendId);
      } catch (error) {
        console.error("Error fetching pending requests:", error);
      }
    },
    // 친구인지 확인
    isFriend(userId) {
      return this.friends.some((friend) => friend.userId === userId);
    },
    // 친구 요청을 보낸 적 있는지 확인
    isPendingRequest(userId) {
      return this.pendingRequests.includes(userId);
    },

    // 친구 요청 취소 또는 요청 보내기
    async toggleFriendRequest(friendId) {
      const userId = localStorage.getItem("userId");

      const userIndex = this.searchResult.findIndex(
          (result) => result.userId === friendId
      );
      if (userIndex === -1) return;

      try {
        if (this.searchResult[userIndex].requestSent) {
          await axios.post(
              `http://localhost:9090/api/friends/remove/${userId}/${friendId}`
          );
        } else {
          await axios.post(
              `http://localhost:9090/api/friends/request/${userId}/${friendId}`
          );
        }
        this.searchResult[userIndex].requestSent = !this.searchResult[userIndex]
            .requestSent;
      } catch (error) {
        console.error("Error toggling friend request:", error);
      }
    },
    // 친구 요청 응답
    async respondToRequest(friendId, status) {
      const userId = localStorage.getItem("userId");
      try {
        await axios.post(
            `http://localhost:9090/api/friends/respond/${userId}/${friendId}/${status}`
        );
        this.fetchPendingRequests();
        this.fetchFriends();
      } catch (error) {
        console.error("Error responding to request:", error);
      }
    },
    // 친구 끊기
    async removeFriend(friendId) {
      const userId = localStorage.getItem("userId");
      if (confirm("정말로 친구를 끊으시겠습니까?")) {
        try {
          await axios.post(
              `http://localhost:9090/api/friends/remove/${userId}/${friendId}`
          );
          this.fetchFriends();
        } catch (error) {
          console.error("Error removing friend:", error);
        }
      }
    },
    // 프로필 팝업 열기
    async openProfilePopup(userId) {
      try {
        const response = await axios.get(
            `http://localhost:9090/api/profiles/${userId}`
        );
        this.selectedProfile = response.data;
        this.showPopup = true;
      } catch (error) {
        console.error("Error fetching user profile:", error);
      }
    },
    // 프로필 팝업 닫기
    closePopup() {
      this.showPopup = false;
      this.selectedProfile = {};
    },
    // 채팅 시작
    startChat(friendId) {
      this.$router.push({ name: "ChatFriend", params: { id: friendId } });
    },
  },
  mounted() {
    this.fetchPendingRequests()
        .then(() => {
          this.fetchFriends();
        })
        .catch((error) => {
          console.error("Error during initialization:", error);
        });
  },
};
</script>

<style scoped>
/* 검색 페이지 컨테이너 */
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

/* 친구 카드 스타일 */
.friend-card {
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
.friend-card-content {
  display: flex;
  align-items: center;
  gap: 15px;
  flex: 1; /* 정보 영역 확장 */
}

/* 프로필 이미지 스타일 */
.profile-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 50%;
  border: 1px solid #ddd;
  cursor: pointer;
}

/* 사용자 정보 */
.friend-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.friend-info h3 {
  margin: 5px 0;
  font-size: 1.2rem;
  color: #555;
  cursor: pointer;
}

/* 검색 입력란 바 */
.search-input-bar {
  position: fixed;
  bottom: 80px;
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

.popup-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.popup-content h3 {
  display: flex;
  font-size: 1.5em;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
}

.popup-content p {
  margin: 10px 0;
  font-size: 1rem;
  color: #555;
}

.friend-actions{
  display: flex;
  align-items: flex-end; /* 버튼 정렬 */
  margin-left: auto; /* 오른쪽 끝으로 밀기 */
  gap: 10px;
}

/* 공통 버튼 스타일 */
.button-blue, button-blue:hover {
  padding: 8px 15px;
  font-size: 0.9rem;
  border-radius: 4px;
  cursor: pointer;
  background-color: #ffffff;
  color: #0056b3;
  border: 1px solid;
}


.icon-button{
  padding: 8px 15px;
  border: none;
  cursor: pointer;
  background-color: #ffffff;
}

.remove-button{
  display: flex;
  justify-content: flex-end;
}

</style>
