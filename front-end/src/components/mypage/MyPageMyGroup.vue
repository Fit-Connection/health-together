<template>
  <div class="group-head">
    <h3>내 모임</h3>
  </div>

  <!-- 동호회 목록 섹션 -->
  <div class="sections">
    <div v-if="loading" class="text-center my-4">
      <span>Loading...</span>
    </div>
    <div v-else>
      <div v-if="activeClubs.length" class="section mb-4">
        <div class="card mb-3" v-for="club in activeClubs" :key="club.teamId">
          <div class="row g-0 align-items-center">
            <div class="col-auto">
              <img :src="club.image || defaultImage" class="club-image rounded-start" alt="Club Image" />
            </div>
            <div class="col">
              <div class="card-body">
                <h5 class="card-title">{{ club.sportType }} - {{ club.teamName }}</h5>
                <p class="card-text">{{ club.description }}</p>
                <div class="details text-muted small">
                  <span>{{ club.location }}</span> •
                  <span>멤버 {{ club.currentMembers }} / {{ club.maxMembers }}명</span> •
                  <span>{{ formatDate(club.createdAt) }}</span>
                </div>
              </div>
            </div>
            <div class="col-auto">
              <div>
              <button class="btn btn-outline-primary btn-sm" @click="viewTeamDetail(club.teamId)">상세 보기</button>
              </div>
              <br>
              <div>
              <button class="btn btn-outline-primary btn-sm" @click="viewChatTeam(club.teamId)">채팅 하기</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center my-4">
        <span>참여 중인 모임이 없습니다.</span>
        <a href="/">모임 찾기</a>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  setup() {
    const defaultImage = new URL('@/assets/football.jpg', import.meta.url).href;
    return { defaultImage };
  },
  data() {
    return {
      activeClubs: [], // API로 받아온 동호회 데이터
      loading: false, // 로딩 상태
    };
  },
  methods: {
    // userId로 동호회 목록 가져오기
    async fetchTeamsByUserId(userId) {
      this.loading = true;
      try {
        const response = await axios.get(`http://localhost:9090/api/teams/user/${userId}`);
        this.activeClubs = response.data;
      } catch (error) {
        console.error("팀 목록을 불러오는 중 오류 발생:", error);
        this.activeClubs = [];
      } finally {
        this.loading = false;
      }
    },
    // 팀 상세 페이지로 이동
    viewTeamDetail(teamId) {
      this.$router.push({ name: "TeamDetail", params: { id: teamId } });
    },
    // 날짜 형식 포맷터
    formatDate(dateString) {
      const options = { year: "numeric", month: "long", day: "numeric" };
      return new Date(dateString).toLocaleDateString("ko-KR", options);
    },
    // 팀 채팅 페이지로 이동
    viewChatTeam(teamId) {
      this.$router.push({ name: "ChatTeam", params: {id: teamId} });
    }
  },
  created() {
    const userId = this.$route.params.userId; // userId를 경로에서 가져옴
    this.fetchTeamsByUserId(userId);
  },
};
</script>

<style scoped>
.group-head {
  justify-content: left;
  text-align: left;
  margin-left: 5%;
}

/* 동호회 카드 스타일 */
.club-image {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 5px;
}

.details {
  font-size: 0.9em;
  color: #6c757d;
}

.more {
  cursor: pointer;
}

</style>
