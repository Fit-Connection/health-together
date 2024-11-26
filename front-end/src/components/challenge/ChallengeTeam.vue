<template>
  <AppHeader />
  <div class="challenge-container container my-4">
    <h1 class="text-center">대회 이벤트</h1>

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
          @click="filterChallenges('EVENT')"
          :class="{ active: selectedFilter === 'EVENT' }"
      >
        이벤트
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
            참여하기
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
import AppHeader from "@/components/common/header/AppHeader.vue";
import AppFooter from "@/components/common/footer/AppFooter.vue";
import CreateChallengeModal from "@/components/challenge/CreateChallengeModal.vue";

export default {
  components: { AppHeader, AppFooter, CreateChallengeModal },
  setup() {
    const challenges = ref([
      {
        challengeId: 1,
        goal: "5km 마라톤 대회",
        reward: "포인트 100점",
        status: "ONGOING",
        type: "EVENT",
      },
      {
        challengeId: 2,
        goal: "풋살 경기 대회",
        reward: "포인트 200점",
        status: "ONGOING",
        type: "TEAM_COMPETITION",
      },
      {
        challengeId: 3,
        goal: "일주일 동안 매일 운동 챌린지 참여 이벤트",
        reward: "모임 배지",
        status: "ONGOING",
        type: "EVENT",
      },
      {
        challengeId: 4,
        goal: "길거리 농구 3:3 팀 대회",
        reward: "상품권 1만원",
        status: "ONGOING",
        type: "TEAM_COMPETITION",
      },
      {
        challengeId: 5,
        goal: "30일간 매일 스트레칭 챌린지 이벤트",
        reward: "포인트 300점",
        status: "COMPLETED",
        type: "EVENT",
      },
    ]);

    const selectedFilter = ref("ALL");
    const isCreateModalVisible = ref(false);

    const filteredChallenges = computed(() => {
      if (selectedFilter.value === "ALL") return challenges.value;
      return challenges.value.filter((c) => c.type === selectedFilter.value);
    });

    const fetchChallenges = async () => {
      // 여기에 서버 호출 대신 정적인 데이터를 사용
      console.log("챌린지 데이터를 로드합니다.");
    };

    const filterChallenges = (type) => {
      selectedFilter.value = type;
    };

    const markAsCompleted = (challengeId) => {
      const challenge = challenges.value.find((c) => c.challengeId === challengeId);
      if (challenge) {
        challenge.status = "COMPLETED";
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
