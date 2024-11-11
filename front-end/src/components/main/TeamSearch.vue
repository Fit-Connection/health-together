<template>
  <AppHeader />
  <div class="team-search container my-4">
    <!-- 공용 Header 컴포넌트 -->

    <!-- 이모지 필터 아이콘 섹션 -->
    <div class="list-icon d-flex justify-content-center gap-3 mb-4">
      <button v-for="icon in icons" :key="icon.name" class="icon-filter btn btn-light">
        <img :src="icon.img" :alt="icon.name" class="icon-img" />
      </button>
    </div>

    <!-- 동호회 목록 섹션 -->
    <div class="sections">
      <div class="section mb-4">
        <h3 class="h5">활발한 동호회 <span class="more text-primary">더보기</span></h3>
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

    <!-- 공용 Footer 컴포넌트 -->
    <AppFooter />
  </div>
</template>

<script>
import AppHeader from "@/components/common/AppHeader.vue";
import AppFooter from "@/components/common/AppFooter.vue";
import api from "@/api";

export default {
  components: { AppHeader, AppFooter },
  setup() {
    const defaultImage = new URL('@/assets/football.jpg', import.meta.url).href;
    return { defaultImage };
  },
  data() {
    return {
      icons: [
        { name: "야구", img: "https://img.icons8.com/ios/250/000000/baseball.png" },
        { name: "축구", img: "https://img.icons8.com/ios/250/000000/football2.png" },
        { name: "농구", img: "https://img.icons8.com/ios/250/000000/basketball.png" },
        { name: "골프", img: "https://img.icons8.com/ios/250/000000/golf-ball.png" },
        { name: "러닝", img: "https://img.icons8.com/ios/250/000000/sports-mode.png" },
      ],
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
      this.$router.push({ name: "TeamDetail", params: { id: teamId } });
    },
  },
  created() {
    this.fetchAllTeams();
  },
};
</script>


<style scoped>
/* 전체 레이아웃 조정 */
.team-search {
  padding-bottom: 80px; /* 하단 네비게이션 높이에 맞춘 패딩 */
}

/* 아이콘 필터 섹션 */
.list-icon {
  display: flex;
  justify-content: center;
  gap: 15px; /* 아이콘 간격 */
  margin-bottom: 20px; /* 아래 컨텐츠와 간격 */
}

.icon-filter {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  padding: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.icon-filter:hover {
  transform: scale(1.1); /* 마우스 오버 시 약간 확대 효과 */
}

.icon-img {
  width: 100%;
  height: auto;
  max-width: 40px;
  max-height: 40px;
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

