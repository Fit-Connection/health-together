<template>
  <div class="bottom-nav fixed-bottom d-flex justify-content-around py-2 bg-light border-top">
    <button
        v-for="nav in navItems"
        :key="nav.name"
        class="nav-item btn btn-link"
        @click="navigateTo(nav.name)"
    >
      <!-- 좋아요 버튼 -->
      <img
          v-if="nav.name === '찜하기'"
          src="https://super.so/icon/dark/heart.svg"
          alt="Like Icon"
          class="nav-icon"
          :class="{ liked: hasLiked }"
      />
      <!-- 일반 버튼 -->
      <img
          v-else
          :src="nav.icon"
          :alt="nav.name"
          class="nav-icon"
      />
    </button>
  </div>
</template>

<script>
import { useLikeStore } from "@/store/likeStore";
import { computed } from "vue";
import api from "@/api";

export default {
  props: {
    isMember: {
      type: Boolean,
      default: false, // 참여 여부 기본값
    },
    userId: {
      type: Number,
      required: true, // 현재 사용자 ID
    },
    teamId: {
      type: Number,
      required: true, // 현재 팀 ID
    },
  },
  setup(props, { emit }) {
    const likeStore = useLikeStore();

    // 좋아요 상태 확인
    const hasLiked = computed(() => likeStore.hasUserLiked(props.userId, props.teamId));

    // 좋아요 토글 함수
    const toggleLike = async () => {
      const message = hasLiked.value
          ? "정말로 이 팀을 찜 해제하시겠습니까?"
          : "이 팀을 찜하시겠습니까?";
      if (!confirm(message)) return;

      try {
        await likeStore.toggleLike({
          userId: props.userId,
          teamId: props.teamId,
          facilityId: null,
        });
        emit("updateLikes"); // 부모 컴포넌트에 좋아요 상태 업데이트 알림
      } catch (error) {
        console.error("좋아요 처리 중 오류:", error.message);
      }
    };

    // 팀 참가 신청 함수
    const applyToTeam = async () => {
      if (!confirm("이 팀에 참여 신청하시겠습니까?")) return;

      try {
        await api.post("/team-members", {
          userId: props.userId,
          teamId: props.teamId,
        });
        alert("참여 신청이 완료되었습니다!");
        emit("updateMembers"); // 부모 컴포넌트에 멤버 업데이트 알림
      } catch (error) {
        if (error.response?.status === 409) {
          alert("이미 팀에 참가 신청을 하셨습니다.");
        } else {
          alert("참여 신청 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
        console.error("참여 신청 오류:", error.message);
      }
    };

    // 팀 탈퇴 처리 함수
    const leaveTeam = async () => {
      if (!confirm("정말로 이 팀에서 탈퇴하시겠습니까?")) return;

      try {
        await api.delete(`/team-members/${props.teamId}`, {
          data: { userId: props.userId },
        });
        alert("팀에서 탈퇴되었습니다.");
        emit("updateMembers"); // 부모 컴포넌트에 멤버 업데이트 알림
      } catch (error) {
        console.error("팀 탈퇴 중 오류 발생:", error.message);
        alert("탈퇴 처리 중 문제가 발생했습니다. 다시 시도해주세요.");
      }
    };

    // 네비게이션 처리
    const navigateTo = async (page) => {
      if (page === "찜하기") {
        await toggleLike();
      } else if (page === "참가") {
        await applyToTeam();
      } else if (page === "탈퇴") {
        await leaveTeam();
      } else if (page === "팀채팅") {
        alert("팀 채팅 기능이 준비 중입니다.");
      }
    };

    return {
      hasLiked,
      navigateTo,
    };
  },
  computed: {
    navItems() {
      const baseItems = [
        { name: "찜하기", icon: "https://super.so/icon/dark/heart.svg" },
      ];
      if (this.isMember) {
        baseItems.push(
            { name: "팀채팅", icon: "https://super.so/icon/dark/message-circle.svg" },
            { name: "탈퇴", icon: "https://super.so/icon/dark/user-minus.svg" }
        );
      } else {
        baseItems.push({ name: "참가", icon: "https://super.so/icon/dark/user-plus.svg" });
      }
      return baseItems;
    },
  },
};
</script>

<style scoped>
.bottom-nav {
  background-color: #ffffff;
  box-shadow: 0 -1px 5px rgba(0, 0, 0, 0.1);
}

.nav-icon {
  width: 28px;
  height: 28px;
  transition: transform 0.3s ease, filter 0.3s ease;
}

/* 좋아요 상태에 따라 색상 변경 */
.nav-icon.liked {
  filter: invert(39%) sepia(74%) saturate(3222%) hue-rotate(350deg) brightness(93%) contrast(96%);
  transform: scale(1.2);
}
</style>
