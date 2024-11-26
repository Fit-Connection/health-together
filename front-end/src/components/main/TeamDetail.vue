<template>
  <div>
    <DetailHeader />
    <div class="team-detail container my-4">
      <!-- 팀 정보 섹션 -->
      <div class="team-info row mb-4">
        <div class="col-md-6">
          <img
              :src="team.imageUrl || defaultImage"
              class="club-image rounded"
              alt="Team Image"
          />
        </div>
        <div class="col-md-6">
          <h2 class="team-name">{{ team.teamName }}</h2>
          <p class="team-sportType">
            <strong>운동 종류:</strong> {{ team.sportType }}
          </p>
          <p class="team-location">
            <strong>위치:</strong> {{ team.facilityLocation }}
          </p>
          <p class="team-meetingDate">
            <strong>모임 날짜:</strong> {{ formatDate(team.meetingDate) }}
          </p>
          <p class="team-members">
            <strong>참여 멤버:</strong> {{ team.currentMembers }} / {{ team.maxMembers }}명
          </p>
          <p class="team-fee">
            <strong>비용:</strong> {{ team.fee || "무료" }}
          </p>
        </div>
      </div>

      <!-- 참여자 목록 섹션 -->
      <h3 class="section-title text-success">참여자 목록</h3>
      <div v-if="teamMembers.length" class="team-member-list d-flex gap-3 mb-4">
        <div
            v-for="member in teamMembers"
            :key="member.teamMemberId"
            class="team-member"
        >
          <img
              :src="member.profileImageUrl || defaultProfileImage"
              alt="Profile Image"
              class="rounded-circle"
              @mouseover="hoveredMember = member.nickname"
              @mouseleave="hoveredMember = null"
          />
          <div v-if="hoveredMember === member.nickname" class="tooltip">
            {{ member.nickname }}
          </div>
        </div>
      </div>
      <p v-else class="text-muted text-center">
        아직 참여자가 없습니다. 팀에 첫 번째로 참여해보세요!
      </p>
      <!-- 팀 세부 정보 -->
      <div class="card border-0 shadow-sm">
        <div class="card-body">
          <h4 class="card-title">팀 세부 정보</h4>
          <p class="team-description">
            {{ team.description || "설명이 없습니다." }}
          </p>
          <p class="team-preparing">
            <strong>준비물:</strong> {{ team.preparing || "준비물 없음" }}
          </p>
          <p class="team-rule">
            <strong>규칙:</strong>
            {{ team.rule || "규칙 없음" }}
          </p>
          <p class="team-invitations">
            {{ team.invitations || "환영합니다." }}
          </p>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <DetailFooter
        :isMember="isMember"
        :userId="1"
    :teamId="team.teamId"
    @updateLikes="fetchLikes"
    @updateMembers="fetchMembers"
    />

  </div>
</template>

<script>
import { ref, reactive, onMounted } from "vue";
import { useRoute } from "vue-router";
import api from "@/api";
import { useLikeStore } from "@/store/likeStore";
import DetailHeader from "@/components/common/header/DetailHeader.vue";
import DetailFooter from "@/components/common/footer/DetailFooter.vue";

export default {
  components: { DetailHeader, DetailFooter },
  setup() {
    const route = useRoute();
    const likeStore = useLikeStore();

    const team = reactive({});
    const teamMembers = ref([]);
    const hoveredMember = ref(null);

    const defaultImage = new URL("@/assets/football.jpg", import.meta.url).href;
    const defaultProfileImage = new URL("@/assets/default-profile.png", import.meta.url).href;
    const activeAction = ref(null);

    const formatDate = (date) =>
        date
            ? new Date(date).toLocaleDateString("ko-KR", {
              year: "numeric",
              month: "long",
              day: "numeric",
            })
            : "날짜 정보 없음";

    const fetchTeamData = async () => {
      try {
        const response = await api.get(`/teams/${route.params.id}`);
        Object.assign(team, response.data);
      } catch (error) {
        console.error("팀 정보를 불러오는 중 오류 발생:", error);
      }
    };

    const fetchTeamMembers = async () => {
      try {
        const response = await api.get(`/team-members/${route.params.id}`);
        teamMembers.value = response.data;
      } catch (error) {
        console.error("참여자 정보를 불러오는 중 오류 발생:", error);
      }
    };

    const toggleLike = async () => {
      try {
        const teamId = team.teamId;
        await likeStore.toggleLike({ userId: 1, teamId, facilityId: null });
        activeAction.value = "찜하기";
      } catch (error) {
        console.error("좋아요 처리 중 오류 발생:", error);
      }
    };

    const handleFooterAction = async (action) => {
      if (action === "찜하기") {
        toggleLike();
      } else if (action === "팀채팅") {
        alert("팀 채팅 기능 구현 예정");
      } else if (action === "참가") {
        try {
          await api.post(`/team-members/${route.params.id}/apply`);
          alert("참여 신청이 완료되었습니다!");
        } catch (error) {
          console.error("참여 신청 중 오류 발생:", error);
          alert("참여 신청에 실패했습니다.");
        }
      }
    };

    onMounted(() => {
      fetchTeamData();
      fetchTeamMembers();
    });

    return {
      team,
      teamMembers,
      hoveredMember,
      defaultImage,
      defaultProfileImage,
      formatDate,
      handleFooterAction,
      activeAction,
    };
  },
};
</script>

<style scoped>
/* 팀 상세 페이지 컨테이너 */
.team-detail {
  padding: 30px;
  background-color: #fafafa; /* 더 밝은 배경색으로 변경 */
  border-radius: 10px; /* 둥근 모서리 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
}

/* 팀 이미지 스타일 */
.club-image {
  width: 100%;
  max-width: 450px; /* 더 큰 이미지 크기 허용 */
  height: auto;
  object-fit: cover;
  border-radius: 10px;
  margin-bottom: 15px;
}

/* 팀 이름 */
.team-name {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333; /* 진한 회색 */
  text-align: left;
}

/* 제목 섹션 */
.section-title {
  font-size: 1.8rem;
  font-weight: bold;
  margin: 25px 0;
  color: #007bff; /* 밝은 파란색 */
}

/* 정보 섹션 */
.team-info p {
  font-size: 1rem;
  line-height: 1.6;
  margin-bottom: 10px;
  color: #555; /* 중간 회색 */
}

/* 카드 스타일 */
.card {
  border-radius: 12px;
  overflow: hidden;
  margin-top: 20px;
}

.card-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #222; /* 진한 글자색 */
  margin-bottom: 15px;
}

.card-body p {
  font-size: 1rem;
  color: #666; /* 부드러운 텍스트 */
  margin-bottom: 10px;
}

/* 툴팁 */
.tooltip {
  position: absolute;
  top: -40px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.85);
  color: #fff;
  padding: 8px 12px;
  border-radius: 6px;
  font-size: 0.9rem;
  white-space: nowrap;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  z-index: 10;
}

/* 하단 네비게이션 */
.bottom-nav {
  background-color: #fff;
  border-top: 2px solid #eaeaea;
  padding: 10px 20px;
}

.nav-item {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-icon {
  width: 32px;
  height: 32px;
  filter: grayscale(100%);
  transition: filter 0.3s ease, transform 0.3s ease;
}

.nav-icon:hover {
  filter: none;
  transform: scale(1.1); /* 확대 효과 */
}

.nav-icon.active {
  filter: none;
  color: #ff4757; /* 좋아요 활성화 */
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .team-detail {
    padding: 20px;
  }

  .club-image {
    max-width: 100%;
    margin: 0 auto 20px auto;
  }

  .team-name {
    font-size: 1.6rem;
    text-align: center;
  }

  .team-info {
    text-align: center;
  }

  .card-title {
    font-size: 1.4rem;
    text-align: center;
  }

  .bottom-nav {
    padding: 10px;
  }

  .nav-icon {
    width: 28px;
    height: 28px;
  }
}

.team-member-list {
  display: flex;
  align-items: center;
  gap: 15px;
}

.team-member img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 50%;
  cursor: pointer;
}

</style>
