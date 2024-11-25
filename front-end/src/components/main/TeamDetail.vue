<template>
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
        <p class="team-sportType"><strong>운동 종류:</strong> {{ team.sportType }}</p>
        <p class="team-location"><strong>위치:</strong> {{ team.location }}</p>
        <p class="team-meetingDate">
          <strong>모임 날짜:</strong> {{ formatDate(team.meetingDate) }}
        </p>
        <p class="team-members">
          <strong>참여 멤버:</strong> {{ team.currentMembers }} / {{ team.maxMembers }}명
        </p>
        <p class="team-fee"><strong>비용:</strong> {{ team.fee || "무료" }}</p>
        <button class="btn btn-primary mt-3" @click="applyToTeam">참여 신청</button>
      </div>
    </div>

    <!-- 참여자 프로필 섹션 -->
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
    <p v-else class="text-muted text-center">아직 참여자가 없습니다. 팀에 첫 번째로 참여해보세요!</p>

    <!-- 팀 세부 정보 카드 -->
    <div class="card border-0 shadow-sm">
      <div class="card-body">
        <h4 class="card-title">팀 세부 정보</h4>
        <p class="team-description">
          <strong>설명:</strong> {{ team.description || "설명이 없습니다." }}
        </p>
        <p class="team-preparing">
          <strong>준비물:</strong> {{ team.preparing || "준비물 없음" }}
        </p>
        <p class="team-rule">
          <strong>규칙:</strong> {{ team.rule || "규칙 없음" }}
        </p>
        <p class="team-invitations">
          <strong>초대 메시지:</strong> {{ team.invitations || "초대 메시지가 없습니다." }}
        </p>
      </div>
    </div>
  </div>
  <DetailFooter />
</template>

<script>
import { ref, reactive, onMounted } from "vue";
import { useRoute } from "vue-router";
import api from "@/api";
import DetailHeader from "@/components/common/header/DetailHeader.vue";
import DetailFooter from "@/components/common/footer/DetailFooter.vue";

export default {
  components: { DetailHeader, DetailFooter },
  setup() {
    const route = useRoute();
    const team = reactive({});
    const teamMembers = ref([]);
    const hoveredMember = ref(null);
    const defaultImage = new URL("@/assets/football.jpg", import.meta.url).href;
    const defaultProfileImage = new URL("@/assets/default-profile.png", import.meta.url).href;

    const formatDate = (date) => {
      return date
          ? new Date(date).toLocaleDateString("ko-KR", {
            year: "numeric",
            month: "long",
            day: "numeric",
          })
          : "날짜 정보 없음";
    };

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

    const applyToTeam = async () => {
      try {
        await api.post(`/team-members/${route.params.id}/apply`);
        alert("참여 신청이 완료되었습니다!");
      } catch (error) {
        console.error("참여 신청 중 오류 발생:", error);
        alert("참여 신청에 실패했습니다. 다시 시도해주세요.");
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
      applyToTeam,
    };
  },
};
</script>

<style scoped>
.team-detail {
  padding: 20px;
  background-color: #f9f9f9;
}

.club-image {
  width: 100%;
  max-width: 400px;
  object-fit: cover;
}

.team-name {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 15px;
}

.section-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin: 20px 0;
}

.team-member-list {
  display: flex;
  align-items: center;
  gap: 15px;
}

.team-member {
  position: relative;
}

.team-member img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 50%;
  cursor: pointer;
}

.tooltip {
  position: absolute;
  top: -30px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.8);
  color: #fff;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 0.8rem;
  white-space: nowrap;
}
</style>
