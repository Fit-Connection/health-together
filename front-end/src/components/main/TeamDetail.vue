<template>
  <AppHeader />
  <div class="team-detail container my-4">
    <!-- 팀 정보 섹션 -->
    <div class="card mb-4">
      <div class="card-body">
        <h2 class="card-title">{{ team.teamName }}</h2>
        <p><strong>운동 종류:</strong> {{ team.sportType }}</p>
        <p><strong>팀 설명:</strong> {{ team.description }}</p>
        <p><strong>최대 멤버 수:</strong> {{ team.maxMembers }}명</p>
        <p><strong>현재 멤버 수:</strong> {{ team.currentMembers }}명</p>
        <p><strong>팀 생성일:</strong> {{ formatDate(team.createdAt) }}</p>
      </div>
    </div>

    <!-- 참여자 목록 섹션 -->
    <h3>참여자 목록</h3>
    <div v-if="teamMembers.length > 0" class="list-group mb-4">
      <div v-for="member in teamMembers" :key="member.teamMemberId" class="list-group-item">
        <p><strong>참여자 ID:</strong> {{ member.userId }}</p>
        <p><strong>참여 상태:</strong> {{ member.status }}</p>
        <p><strong>참여 날짜:</strong> {{ formatDate(member.joinedAt) }}</p>
      </div>
    </div>
    <p v-else class="text-muted">참여자가 없습니다.</p>

    <!-- 리뷰 섹션 -->
    <h3>리뷰</h3>
    <div v-if="reviews.length > 0" class="list-group">
      <div v-for="review in reviews" :key="review.reviewId" class="list-group-item">
        <p><strong>작성자:</strong> {{ review.reviewerId }}</p>
        <p><strong>대상자:</strong> {{ review.reviewedUserId }}</p>
        <p><strong>평점:</strong> {{ review.scoreChange }}</p>
        <p>{{ review.comment }}</p>
        <small class="text-muted">{{ formatDate(review.createdAt) }}</small>
      </div>
    </div>
    <p v-else class="text-muted">리뷰가 없습니다.</p>
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
      return new Date(date).toLocaleDateString();
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

.card-title {
  font-size: 1.5rem;
  font-weight: bold;
}

.list-group-item {
  margin-bottom: 10px;
}
</style>
