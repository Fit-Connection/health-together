<template>
  <AppHeader />
  <div class="challenge-container container my-4">
    <h1 class="text-center">나의 챌린지</h1>

    <!-- 챌린지 필터 -->
    <div class="filters d-flex justify-content-center gap-3 my-4">
      <button
          class="btn btn-outline-primary"
          @click="filterChallenges('ALL')"
          :class="{ active: selectedFilter === 'ALL' }"
      >
        전체
      </button>
      <button
          class="btn btn-outline-success"
          @click="filterChallenges('TEAM_COMPETITION')"
          :class="{ active: selectedFilter === 'TEAM_COMPETITION' }"
      >
        팀 대회
      </button>
      <button
          class="btn btn-outline-warning"
          @click="filterChallenges('QUEST')"
          :class="{ active: selectedFilter === 'QUEST' }"
      >
        퀘스트
      </button>
    </div>

    <!-- 챌린지 리스트 -->
    <div class="challenge-list">
      <div
          class="card mb-3"
          v-for="challenge in filteredChallenges"
          :key="challenge.challengeId"
      >
        <div class="card-body">
          <h5 class="card-title">{{ challenge.goal }}</h5>
          <p class="card-text">보상: {{ challenge.reward }}</p>
          <p class="card-text">상태: {{ challenge.status }}</p>
          <button
              v-if="challenge.status === 'ONGOING'"
              class="btn btn-success"
              @click="markAsCompleted(challenge.challengeId)"
          >
            완료하기
          </button>
        </div>
      </div>
    </div>

    <!-- 챌린지 추가 버튼 -->
    <button class="btn btn-primary" @click="openCreateModal">
      챌린지 추가
    </button>

    <!-- 챌린지 생성 모달 -->
    <CreateChallengeModal
        v-if="isCreateModalVisible"
        @close="isCreateModalVisible = false"
        @challengeCreated="fetchChallenges"
    />
  </div>
  <AppFooter />
</template>

<script>
import { ref, computed, onMounted } from "vue";
import api from "@/api";
import AppHeader from "@/components/common/header/AppHeader.vue";
import AppFooter from "@/components/common/footer/AppFooter.vue";
import CreateChallengeModal from "@/components/challenge/CreateChallengeModal.vue";

export default {
  components: { AppHeader, AppFooter, CreateChallengeModal },
  setup() {
    const challenges = ref([]);
    const selectedFilter = ref("ALL");
    const isCreateModalVisible = ref(false);

    const filteredChallenges = computed(() => {
      if (selectedFilter.value === "ALL") return challenges.value;
      return challenges.value.filter((c) => c.type === selectedFilter.value);
    });

    const fetchChallenges = async () => {
      try {
        const response = await api.get("/challenges", {
          params: { userId: localStorage.getItem("userId") },
        });
        challenges.value = response.data;
      } catch (error) {
        console.error("챌린지 목록을 불러오는 중 오류 발생:", error);
      }
    };

    const filterChallenges = (type) => {
      selectedFilter.value = type;
    };

    const markAsCompleted = async (challengeId) => {
      try {
        await api.put(`/challenges/${challengeId}`, { status: "COMPLETED" });
        fetchChallenges();
      } catch (error) {
        console.error("챌린지 상태 업데이트 중 오류 발생:", error);
      }
    };

    const openCreateModal = () => {
      isCreateModalVisible.value = true;
    };

    onMounted(fetchChallenges);

    return {
      challenges,
      filteredChallenges,
      selectedFilter,
      filterChallenges,
      markAsCompleted,
      isCreateModalVisible,
      openCreateModal,
      fetchChallenges,
    };
  },
};
</script>

<style scoped>
.filters .btn {
  transition: all 0.3s ease;
}
.filters .btn.active {
  background-color: #007bff;
  color: #fff;
}
.card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}
</style>
