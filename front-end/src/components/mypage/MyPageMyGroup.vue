<template>
  <div class="group-head">
    <h3>내 모임</h3>
  </div>

  <!-- 동호회 목록 섹션 -->
  <div class="sections">
    <div class="section mb-4">
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
                <span>{{ club.createdAt }}</span>
              </div>
            </div>
          </div>
          <div class="col-auto">
            <button class="btn btn-outline-primary btn-sm" @click="viewTeamDetail(club.teamId)">상세 보기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import api from "@/api";

  export default {
  setup() {
    const defaultImage = new URL('@/assets/football.jpg', import.meta.url).href;
    return {defaultImage};
  },
    data() {
      return {
        activeClubs: []
      };
    },
  methods: {
    async fetchAllTeams() {
    try {
    const response = await api.get("/teams");
    this.activeClubs = response.data;
  } catch (error) {
    console.error("팀 목록을 불러오는 중 오류 발생:", error);
  }
  },
    viewTeamDetail(teamId) {
    this.$router.push({name: "TeamDetail", params: {id: teamId}});
  },
  },
    created() {
    this.fetchAllTeams();
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