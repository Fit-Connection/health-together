<template>
  <DetailHeader />

  <div class="team-detail container my-4">
    <!-- 팀 정보 섹션 -->
    <div class="team-info mb-4 row">
      <div class="col-md-6">
        <img v-if="team.imageUrl" :src="team.imageUrl" alt="Team Image" class="img-fluid rounded shadow-sm" />
      </div>
      <div class="col-md-6">
        <p class="team-description"><strong>팀 설명:</strong> {{ team.description || '팀 설명이 없습니다.' }}</p>
      </div>
    </div>

    <!-- 참여자 목록 섹션 -->
    <h3 class="section-title text-success">같이해요!</h3>
    <div v-if="teamMembers.length" class="list-group mb-4">
      <div v-for="member in teamMembers" :key="member.teamMemberId" class="list-group-item d-flex align-items-center">
        <img v-if="member.profileImageUrl" :src="member.profileImageUrl" alt="Profile Image" class="rounded-circle me-3" width="50" height="50" />
        <div>
          <p class="fw-bold mb-1">{{ member.nickname }}</p>
          <p class="mb-0 text-muted">{{ member.bio }}</p>
        </div>
      </div>
    </div>
    <p v-else class="text-muted text-center">참여자가 없습니다. 팀에 첫 번째로 참여해보세요!</p>

    <!-- 팀 세부 정보 카드 -->
    <div class="card border-0 shadow-sm mb-4">
      <div class="card-body">
        <h2 class="card-title">{{ team.teamName }}</h2>
        <div class="team-details">
          <p><strong>운동 종류:</strong> {{ team.sportType }}</p>
          <p><strong>최대 멤버 수:</strong> {{ team.maxMembers }}명</p>
          <p><strong>현재 멤버 수:</strong> {{ team.currentMembers }}명</p>
          <p><strong>요금:</strong> {{ team.fee || '무료' }}</p>
          <p><strong>연령 제한:</strong> {{ team.ageLimit || '제한 없음' }}</p>
          <p><strong>모임 날짜:</strong> {{ formatDate(team.meetingDate) }} 오후 6:00</p>
          <p><strong>위치:</strong> {{ team.location || '구로구' }}</p>
        </div>
        <img v-if="team.mapImageUrl" :src="team.mapImageUrl" alt="지도 이미지" class="img-fluid mt-3 rounded" />

        <!-- 신청 버튼 -->
        <div class="d-flex align-items-center mt-4">
          <img src="https://super.so/icon/dark/heart.svg" alt="Like" class="me-2" width="24" height="24" />
          <button class="btn btn-primary ms-auto" @click="applyToTeam">신청하기</button>
        </div>
      </div>
    </div>
  </div>

  <AppFooter />
</template>

<script>
import { ref, reactive, onMounted } from "vue";
import { useRoute } from "vue-router";
import api from "@/api";
import AppFooter from "@/components/common/AppFooter.vue";
import DetailHeader from "@/components/common/DetailHeader.vue";

export default {
  components: {DetailHeader, AppFooter },
  setup() {
    const route = useRoute(); // useRoute 훅 사용
    const team = reactive({});
    const teamMembers = ref([]);

    // 날짜 형식 함수
    const formatDate = (date) => {
      return date
          ? new Date(date).toLocaleDateString("ko-KR", {
            year: "numeric",
            month: "long",
            day: "numeric"
          })
          : "날짜 정보 없음";
    };

    // 팀 정보와 참여자 목록 불러오기
    const fetchTeamData = async () => {
      try {
        const teamResponse = await api.get(`/teams/${route.params.id}`);
        Object.assign(team, teamResponse.data);
      } catch (error) {
        console.error("팀 정보를 불러오는 중 오류 발생:", error);
      }
    };

    const fetchTeamMembers = async () => {
      try {
        // const membersResponse = await api.get(`/teams/${route.params.id}/members`);
        // teamMembers.value = membersResponse.data;
      } catch (error) {
        console.error("참여자 정보를 불러오는 중 오류 발생:", error);
      }
    };

    // 신청 버튼 클릭 핸들러
    const applyToTeam = () => {
      alert("신청이 완료되었습니다!");
    };

    // 컴포넌트 로드 시 API 호출
    onMounted(() => {
      fetchTeamData();
      fetchTeamMembers();
    });

    return {
      team,
      teamMembers,
      formatDate,
      applyToTeam
    };
  },
};
</script>

<style scoped>
.team-detail {
  padding: 20px;
  background-color: #f9f9f9;
}

.team-description {
  font-size: 1.1rem;
  color: #333;
}

.section-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-top: 20px;
  margin-bottom: 15px;
}

.card-title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
}

.team-details p {
  margin: 8px 0;
  font-size: 1rem;
  color: #555;
}
</style>
