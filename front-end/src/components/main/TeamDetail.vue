<template>
  <AppHeader />
  <div class="team-detail container my-4">
    <!-- 팀 정보 섹션 -->
    <div class="team-info mb-4">
      <img :src="team.imageUrl" alt="Team Image" class="team-image" v-if="team.imageUrl" />
      <div>
        <p><strong>팀 설명:</strong> {{ team.description || '팀 설명이 없습니다.' }}</p>
      </div>
    </div>

    <!-- 참여자 목록 섹션 -->
    <h3>같이해요!</h3>
    <div v-if="teamMembers.length > 0" class="list-group mb-4">
      <div v-for="member in teamMembers" :key="member.teamMemberId" class="list-group-item">
        <img :src="member.profileImageUrl" alt="Profile Image" class="profile-image" v-if="member.profileImageUrl" />
        <p>{{ member.nickname }}</p>
        <p>{{ member.bio }}</p>
      </div>
    </div>
    <p v-else>참여자가 없습니다. 팀에 첫 번째로 참여해보세요!</p>

    <!-- 팀 세부 정보 카드 -->
    <div class="card mb-4">
      <div class="card-body">
        <h2 class="card-title">{{ team.teamName }}</h2>
        <p><strong>운동 종류:</strong> {{ team.sportType }}</p>
        <p><strong>최대 멤버 수:</strong> {{ team.maxMembers }}명</p>
        <p><strong>현재 멤버 수:</strong> {{ team.currentMembers }}명</p>
        <p><strong>요금:</strong> {{ team.fee || '무료' }}</p>
        <p><strong>연령 제한:</strong> {{ team.ageLimit || '제한 없음' }}</p>
        <p><strong>모임 날짜:</strong> {{ formatDate(team.meetingDate) }} 오후 6:00</p>
        <p><strong>위치:</strong> {{ team.location || '구로구' }}</p>
        <img :src="team.mapImageUrl" alt="지도 이미지" v-if="team.mapImageUrl" />

        <!-- 신청 버튼 -->
        <div class="mt-3">
          <img src="https://super.so/icon/dark/heart.svg" alt="Like" />
          <button class="apply-button" @click="applyToTeam">신청하기</button>
        </div>
      </div>
    </div>
  </div>
  <AppFooter />
</template>

<script>
import axios from "axios";
import AppHeader from "@/components/common/AppHeader.vue";
import AppFooter from "@/components/common/AppFooter.vue";

export default {
  components: { AppHeader, AppFooter },
  data() {
    return {
      team: {},
      teamMembers: [],
      reviews: [],
    };
  },
  methods: {
    async fetchTeam() {
      try {
        const response = await axios.get(`http://localhost:9090/api/teams/${this.$route.params.id}`);
        this.team = response.data;
      } catch (error) {
        console.error("팀 정보를 불러오는 중 오류 발생:", error);
      }
    },
    async fetchTeamMembers() {
      try {
        const response = await axios.get(`http://localhost:9090/api/teams/${this.$route.params.id}/members`);
        this.teamMembers = response.data;
      } catch (error) {
        console.error("참여자 정보를 불러오는 중 오류 발생:", error);
      }
    },
    async fetchReviews() {
      try {
        const response = await axios.get(`http://localhost:9090/api/teams/${this.$route.params.id}/reviews`);
        this.reviews = response.data;
      } catch (error) {
        console.error("리뷰 정보를 불러오는 중 오류 발생:", error);
      }
    },
    formatDate(date) {
      if (!date) return "날짜 정보 없음";
      return new Date(date).toLocaleDateString("ko-KR", {
        year: "numeric",
        month: "long",
        day: "numeric",
      });
    },
    applyToTeam() {
      alert("신청이 완료되었습니다!");
    },
  },
  created() {
    this.fetchTeam();
    this.fetchTeamMembers();
    this.fetchReviews();
  },
};
</script>

<style scoped>
.team-detail {
  padding: 20px;
}

.team-image {
  width: 100%;
  height: auto;
  max-width: 500px;
  margin-bottom: 20px;
}

.card-title {
  font-size: 1.5rem;
  font-weight: bold;
}

.list-group-item {
  margin-bottom: 10px;
}

.profile-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.apply-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.apply-button:hover {
  background-color: #0056b3;
}
</style>
