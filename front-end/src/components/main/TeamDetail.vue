<template>
  <DetailHeader />

  <div class="team-detail container my-4">
    <!-- 팀 정보 섹션 -->
    <div class="team-info mb-4 row">
      <div class="col-md-6">
        <img :src="team.imageUrl || '@/assets/football.jpg'" class="club-image rounded-start" alt="Club Image" />
<!--        <img v-if="team.imageUrl" :src="team.imageUrl" alt="Team Image" class="img-fluid rounded shadow-sm" />-->
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

    <p v-if="team.currentMembers === 0" class="text-muted text-center">참여자가 없습니다. 팀에 첫 번째로 참여해보세요!</p>

    <!-- 팀 세부 정보 카드 -->
    <div class="card border-0 shadow-sm mb-4">
      <div class="card-body">
        <h2 class="card-title">{{ team.teamName }}</h2>
        <p class="team-description"><strong>팀 설명:</strong> {{ team.description || '팀 설명이 없습니다.' }}</p>
        <div class="team-details">
          <p><strong>운동 종류:</strong> {{ team.sportType }}</p>
          <p><strong>비용:</strong> {{ team.fee || '무료' }}</p>
          <p><strong>연령 제한:</strong> {{ team.ageLimit || '제한 없음' }}</p>
          <p><strong>모임 날짜:</strong> {{ formatDate(team.meetingDate) }} 오후 6:00</p>
          <p><strong>위치:<img src="https://super.so/icon/dark/map-pin.svg"></strong> {{ team.location}}</p>
<!--          <img v-if="team.mapImageUrl" :src="team.mapImageUrl" alt="지도 이미지" class="img-fluid mt-3 rounded" />-->
          <p><strong>모임장 : {{team.userId}}</strong></p>
          <p><strong>참여 멤버:</strong> {{ team.currentMembers + ' / ' + team.maxMembers }}명</p>
<!--          <p v-for="member in teamMembers" key="member.id">-->
<!--            {{member.imageUrl}}-->
<!--          </p>-->
          <p><strong>준비물 : {{team.preparing}}</strong></p>
          <p><strong>[규칙]{{team.rule}}<br/><br/>
            1. 부상 유발 금지 (안전 최우선) <br/>
            2. 준비 운동 반드시 하기<br/>
            3. 전반전 30분 휴식 10분<br/>
            후반전 30분 휴식 10분<br/>

          </strong></p>
          <p><strong>{{team.invitations}}
            주기적으로 함께 참여하실 분 <br>팔로우 환영합니다!<br>팀 채팅으로 언제든 문의주세요!</strong></p>

        </div>

      </div>
    </div>
  </div>
  <DetailFooter/>

</template>

<script>
import { ref, reactive, onMounted } from "vue";
import { useRoute } from "vue-router";
import api from "@/api";
import DetailHeader from "@/components/common/DetailHeader.vue";
import DetailFooter from "@/components/common/DetailFooter.vue";

export default {
  components: {DetailHeader, DetailFooter },
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
        const membersResponse = await api.get(`/teams/${route.params.id}/members`);
        teamMembers.value = membersResponse.data;
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
  margin: 40px;
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
