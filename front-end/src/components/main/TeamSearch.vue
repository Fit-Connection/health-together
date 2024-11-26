<template>
  <AppHeader
      @search="onSearch"
      @filter-location="onFilterLocation"
  />
  <div class="team-search container my-4">
    <!-- 이모지 필터 아이콘 섹션 -->
    <div class="list-icon d-flex justify-content-center gap-3 mb-4">
      <button
          v-for="icon in icons"
          :key="icon.name"
          class="icon-filter btn btn-light"
          @click="filterBySportType(icon.name)"
      >
        <img :src="icon.img" :alt="icon.name" class="icon-img" />
      </button>
    </div>

    <!-- 동호회 목록 섹션 -->
    <div class="sections">
      <div class="section mb-4">
        <div
            class="card mb-3 clickable-card"
            v-for="club in filteredClubs"
            :key="club.teamId"
            @click="viewTeamDetail(club.teamId)"
        >
          <div class="row g-0 align-items-stretch">
            <!-- 이미지 및 좋아요 -->
            <div class="col-auto d-flex flex-column align-items-center">
              <img
                  :src="club.teamImageUrl || defaultImage"
                  class="club-image rounded-start"
                  alt="Club Image"
              />
              <!-- 좋아요 버튼 -->
              <img
                  src="https://super.so/icon/dark/heart.svg"
                  alt="Like Icon"
                  :class="{ liked: hasLiked(club.teamId) }"
                  class="like-icon mt-3"
                  @click.stop="toggleLike(club.teamId)"
              />
            </div>

            <!-- 메인 콘텐츠 -->
            <div class="col d-flex flex-column justify-content-between">
              <!-- 태그 섹션 -->
              <div class="tags d-flex flex-wrap gap-2 mt-2">
                <span class="badge bg-primary">{{ club.sportType }}</span>
                <span class="badge bg-secondary">{{ club.facilityName }}</span>
                <span class="badge bg-light text-dark">{{ club.ageLimit }}</span>
              </div>
              <!-- 텍스트 섹션 -->
              <div class="content mt-2">
                <h5 class="card-title">{{ club.teamName }}</h5>
                <p class="card-text">{{ club.description }}</p>
              </div>
              <!-- 참여 현황 및 시간 -->
              <div class="details text-muted small">
                <p>멤버 {{ club.currentMembers }} / {{ club.maxMembers }}명 &emsp; &emsp; {{ formatElapsedTime(club.createdAt) }}</p>
              </div>
            </div>
          </div>
          <h3 class="h5">
            <span class="more text-primary" @click="fetchAllTeams">더보기</span>
          </h3>
        </div>
      </div>
    </div>
    <!-- 공용 Footer 컴포넌트 -->
    <AppFooter />
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import AppHeader from "@/components/common/header/AppHeader.vue";
import AppFooter from "@/components/common/footer/AppFooter.vue";
import api from "@/api";
import router from "@/router";
import { useLikeStore } from "@/store/likeStore"; // Pinia Store

export default {
  components: { AppHeader, AppFooter },
  setup() {
    const defaultImage = new URL("@/assets/football.jpg", import.meta.url).href;

    // Pinia Store 연결
    const likeStore = useLikeStore();

    // 좋아요 상태 확인 함수
    const hasLiked = (teamId) => {
      return likeStore.hasUserLiked(localStorage.getItem("userId"), teamId);
    };
    console.log(hasLiked(1));
    // 좋아요 토글
    const toggleLike = async (teamId) => {
      try {
        await likeStore.toggleLike({
          userId: localStorage.getItem("userId"),
          teamId,
        });
        // 좋아요 상태가 변경되면 필터링된 데이터도 갱신
        applyFilters();
      } catch (error) {
        console.error("좋아요 상태 변경 중 오류:", error);
      }
    };


    // 상태 관리
    const teams = ref([]); // 모든 팀 데이터
    const filteredClubs = ref([]); // 필터링된 클럽 데이터
    const selectedSportType = ref(""); // 선택된 스포츠 유형
    const currentSearch = ref(""); // 현재 검색어
    const currentLocation = ref(""); // 현재 위치 필터

    const icons = [
      { name: "", img: "https://img.icons8.com/ios/250/000000/conference-background-selected.png" },
      { name: "야구", img: "https://img.icons8.com/ios/250/000000/baseball.png" },
      { name: "풋살", img: "https://img.icons8.com/ios/250/000000/football2.png" },
      { name: "농구", img: "https://img.icons8.com/ios/250/000000/basketball.png" },
      { name: "골프", img: "https://img.icons8.com/ios/250/000000/golf-ball.png" },
      { name: "러닝", img: "https://img.icons8.com/ios/250/000000/sports-mode.png" },
    ];

    // 데이터 필터링
    const applyFilters = () => {
      filteredClubs.value = teams.value.filter((team) => {
        const matchesSearch = currentSearch.value === "" || team.teamName.includes(currentSearch.value);
        const matchesLocation = currentLocation.value === "" || team.location === currentLocation.value;
        const matchesSportType = selectedSportType.value === "" || team.sportType === selectedSportType.value;

        return matchesSearch && matchesLocation && matchesSportType;
      });
    };


    // 데이터 가져오기
    const fetchAllTeams = async () => {
      try {
        const response = await api.get("/teams");
        teams.value = response.data;
        filteredClubs.value = teams.value; // 초기에는 전체 데이터를 표시
      } catch (error) {
        console.error("팀 목록을 불러오는 중 오류 발생:", error);
      }
    };

    // 검색 이벤트 처리
    const onSearch = (searchQuery) => {
      currentSearch.value = searchQuery;
      applyFilters();
    };

    // 위치 필터 처리
    const onFilterLocation = (location) => {
      currentLocation.value = location;
      applyFilters();
    };

    // 스포츠 유형 필터
    const filterBySportType = (sportType) => {
      selectedSportType.value = sportType;
      applyFilters();
    };

    // 상세 페이지 이동
    const viewTeamDetail = (teamId) => {
      router.push({ name: "TeamDetail", params: { id: teamId } });
    };

    // 시간 경과 포맷
    const formatElapsedTime = (dateString) => {
      const now = new Date();
      const past = new Date(dateString);
      const diff = Math.floor((now - past) / 1000);

      if (diff < 60) return `${diff}초 전`;
      if (diff < 3600) return `${Math.floor(diff / 60)}분 전`;
      if (diff < 86400) return `${Math.floor(diff / 3600)}시간 전`;
      return `${Math.floor(diff / 86400)}일 전`;
    };

    // 초기화
    onMounted(async () => {
      await fetchAllTeams();
      await likeStore.fetchLikes(); // 좋아요 데이터 로드
    });

    return {
      defaultImage,
      icons,
      filteredClubs,
      filterBySportType,
      viewTeamDetail,
      formatElapsedTime,
      onSearch,
      onFilterLocation,
      hasLiked,
      toggleLike,
    };
  },
};
</script>

<style scoped>
/* 아이콘 필터 섹션 */
.list-icon {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-bottom: 20px;
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
  transform: scale(1.1);
}

.icon-img {
  width: 100%;
  height: auto;
  max-width: 40px;
  max-height: 40px;
}

/* 이미지 및 좋아요 */
.club-image {
  margin: 10px;
  width: 90px;
  height: 120px;
  object-fit: cover;
  border-radius: 5px;
  position: relative;
}



/* 배지 스타일 */
.badge {
  font-size: 0.8rem;
  padding: 5px 10px;
  border-radius: 15px;
  font-weight: bold;
}

.badge.bg-primary {
  background: linear-gradient(135deg, #ff7eb9, #ff65a3); /* 핑크 그라데이션 */
  color: white;
}

.badge.bg-secondary {
  background: linear-gradient(135deg, #88e1f2, #5cbde9); /* 하늘색 그라데이션 */
  color: white;
}

.badge.bg-light {
  background-color: #f5d0c5; /* 파스텔 코랄 */
  color: #6f1d1b;
}

/* 기타 스타일 */
.clickable-card {
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.clickable-card:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.details {
  font-size: 0.9em;
  color: #6c757d;
}

/* 좋아요 */
.like-icon {
  width: 28px;
  height: 28px;
  cursor: pointer;
  position: absolute;
  bottom: 10px;
  left: 10px;
  background-color: white;
  border-radius: 50%;
  padding: 4px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
  filter: grayscale(100%) brightness(1.5); /* 기본 회색 처리 */
  transition: filter 0.3s ease;
}

.like-icon.liked {
  filter: none; /* 원래 색상(빨간색) 표시 */
}
</style>

