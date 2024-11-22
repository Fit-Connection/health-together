<template>
    <div class = "find-friend" @click="search">
      <img src='@/assets/people.png' class="people-img">
      <p class="find-friend-title">친구 찾기</p>
    </div>
  <div class="friend-list-container">
    <h2 class="page-title">친구 목록</h2>
    <hr>
    <div v-if="friends.length === 0" class="no-friends">
      <p>친구 목록이 비어있습니다.</p>
    </div>
    <div class="friends-container">
      <div class="friend-card" v-for="friend in friends" :key="friend.friendId">
        <div class="friend-card-content">
          <!-- 프로필 이미지 -->
          <img :src="friend.profileImage || defaultImage" alt="Profile Image" class="profile-img" @click="openProfilePopup(friend.friendId)"/>
          <!-- 친구 정보 -->
          <div class="friend-info">
            <h3><strong>{{ friend.name }}</strong></h3>
          </div>
          <div class="friend-actions">
            <button @click="startChat(friend.friendId)" class="chat-button">채팅 하기</button>
            <button @click="removeFriend(friend.friendId)" class="remove-button">친구 끊기</button>
          </div>
        </div>
      </div>
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
      userId: localStorage.getItem("userId") || null, // 사용자 ID 가져오기
      friends: [], // 친구 목록 데이터
      defaultImage: require('@/assets/Weblogo.png'), // 기본 프로필 이미지
      showPopup: false, // 팝업 표시 여부
      selectedProfile: {}, // 선택된 프로필 데이터
    };
  },
  methods: {
    // 친구 목록 가져오기
    async fetchFriends() {
      try {
        const response = await axios.get(`http://localhost:9090/api/friends/list/${this.userId}`);

        // 데이터 변환: API 응답에서 필요한 정보를 추출
        this.friends = response.data.map(friend => ({
          friendId: friend.friendId,
          name: friend.name || "알 수 없음",
          location: friend.location || "지역 정보 없음",
          sports: friend.sports || "선호 종목 없음",
          profileImage: friend.profileImage || this.defaultImage
        }));
      } catch (error) {
        console.error("Error fetching friends:", error);
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
    // 채팅 시작
    startChat(friendId) {
      this.$router.push({ name: "ChatFriend", params: {id: friendId } }); // 채팅 페이지로 이동
    },
    // 친구찾기
    search() {
      this.$router.push({ name: "SearchPage"});
    },
    // 친구 끊기
    async removeFriend(friendId) {
      if (confirm("정말로 친구를 끊으시겠습니까?")) {
        try {
          await axios.post(`http://localhost:9090/api/friends/remove/${this.userId}/${friendId}`);
          this.fetchFriends().then(() => {
            this.fetchAllFriendsProfiles();  // 친구 목록 갱신
          });
        } catch (error) {
          console.error("Error removing friend:", error);
        }
      }
    },
    // 특정 친구 프로필 정보 가져오기
    async friendsProfileInfo(friendId) {
      try {
        const response = await axios.get(`http://localhost:9090/api/profiles/${friendId}`);
        return response.data; // 프로필 데이터 반환
      } catch (error) {
        console.error("Error fetching friend's profile:", error);
        return null;
      }
    },
    // 모든 친구들의 프로필 가져오기
    async fetchAllFriendsProfiles() {
      try {
        const profiles = await Promise.all(
            this.friends.map(async friend => {
              const profile = await this.friendsProfileInfo(friend.friendId);
              return { ...friend, ...profile }; // 기존 친구 데이터와 프로필 병합
            })
        );
        this.friends = profiles; // 업데이트된 친구 목록
        console.log("All friends profiles loaded");
      } catch (error) {
        console.error("Error fetching all friends' profiles:", error);
      }
    }

  },
  mounted() {
    // 컴포넌트 로드 시 친구 목록 가져오기
    if (this.userId) {
      this.fetchFriends().then(() => {
        this.fetchAllFriendsProfiles(); // 모든 친구들의 프로필 가져오기
      });
    } else {
      alert("로그인이 필요합니다.");
      this.$router.push("/login");
    }
  }
};
</script>

<style scoped>
/* 전체 컨테이너 */
.friend-list-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 8px;
}

/* 제목 스타일 */
.page-title {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.find-friend-title {
  display: flex;
  text-align: center;
  align-content: center;
  align-items: center;
  color: #333;
  margin: 5px;
}

.people-img {
  width: 45px;
  height: 45px;
  margin-left: 15px;
}

/* 친구 찾기 스타일 */
.find-friend {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 5px;
  border: 1px solid #ddd;
}

/* 친구 목록이 비어있는 경우 메시지 */
.no-friends {
  text-align: center;
  color: #888;
  font-size: 1.2rem;
}

/* 친구 목록 컨테이너 */
.friends-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
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
  border-radius: 50%; /* 원형 이미지 */
  border: 1px solid #ddd;
}

/* 친구 정보 */
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

.friend-actions {
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 버튼 정렬 */
  margin-left: auto; /* 오른쪽 끝으로 밀기 */
  gap: 10px;
}

.chat-button, .remove-button, .close-button{
  padding: 8px 15px;
  font-size: 0.9rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.chat-button {
  background-color: #ffffff;
  color: #0056b3;
  border: 1px solid #0056b3;
  font-size: 0.9rem;
}

.chat-button:hover {
  background-color: #ffffff;
  color: #0056b3;
  border: 1px solid #0056b3;
}

.remove-button, .close-button{
  background-color: #ffffff;
  color: #dc3545;
  border: 1px solid #dc3545;
  font-size: 0.9rem;
}

.remove-button:hover, .close-button:hover{
  background-color: #ffffff;
  color: #dc3545;
  border: 1px solid #dc3545;
}
</style>
