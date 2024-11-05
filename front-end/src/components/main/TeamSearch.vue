<template>
  <div class="team-search">
    <!-- 상단 지역 선택과 아이콘 -->
    <div class="header">
      <select v-model="selectedLocation" class="location-select">
        <option>역삼1동</option>
        <option>강남구</option>
        <option>서초구</option>
      </select>
      <div class="icons">
        <button v-for="icon in icons" :key="icon.name" class="icon">
          <img :src="icon.img" :alt="icon.name" />
          <span>{{ icon.name }}</span>
        </button>
      </div>
    </div>

    <!-- 동호회 목록 섹션 -->
    <div class="sections">
      <div class="section">
        <h3>활발한 동호회 <span class="more">더보기</span></h3>
        <div class="club-card" v-for="club in activeClubs" :key="club.teamId">
          <img :src="club.image || 'default.jpg'" class="club-image" />
          <div class="club-info">
            <h4>{{ club.sportType }} - {{ club.teamName }}</h4>
            <p>{{ club.description }}</p>
            <div class="details">
              <span>{{ club.location }}</span>
              <span>멤버 {{ club.currentMembers }} / {{ club.maxMembers }}명</span>
              <span>{{ club.createdAt }}</span>
            </div>
          </div>
          <button class="join-button" @click="viewTeamDetail(club.teamId)">상세 보기</button>
        </div>
      </div>
    </div>

    <!-- 하단 네비게이션 -->
    <div class="bottom-nav">
      <button v-for="nav in navItems" :key="nav.name" class="nav-item">
        <img :src="nav.icon" :alt="nav.name" />
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      selectedLocation: "역삼1동",
      icons: [
        { name: "야구", img: "path/to/baseball.png" },
        { name: "축구", img: "path/to/soccer.png" },
        { name: "농구", img: "path/to/basketball.png" },
        { name: "골프", img: "path/to/golf.png" },
        { name: "러닝", img: "path/to/running.png" },
      ],
      activeClubs: [],
      navItems: [
        { name: "홈", icon: "path/to/home.png" },
        { name: "지도", icon: "path/to/map.png" },
        { name: "즐겨찾기", icon: "path/to/favorite.png" },
        { name: "설정", icon: "path/to/settings.png" },
      ],
    };
  },
  methods: {
    async fetchAllTeams() {
      try {
        const response = await axios.get("http://localhost:9090/api/teams");
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
.team-search {
  padding: 20px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.location-select {
  padding: 5px;
}

.icons {
  display: flex;
  gap: 10px;
}

.icon {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.sections {
  margin-top: 20px;
}

.section h3 {
  font-size: 1.4em;
  margin-bottom: 10px;
}

.club-card {
  display: flex;
  align-items: center;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 15px;
}

.club-image {
  width: 60px;
  height: 60px;
  border-radius: 5px;
}

.club-info {
  flex: 1;
  margin-left: 10px;
}

.details {
  font-size: 0.9em;
  color: #666;
}

.join-button {
  padding: 5px 10px;
  cursor: pointer;
}

.bottom-nav {
  display: flex;
  justify-content: space-around;
  position: fixed;
  bottom: 0;
  width: 100%;
  padding: 10px 0;
  background-color: #fff;
  border-top: 1px solid #ddd;
}
</style>
