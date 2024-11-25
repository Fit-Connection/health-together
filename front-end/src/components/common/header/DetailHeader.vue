<template>
  <header class="header d-flex align-items-center justify-content-between px-3 py-2">
    <!-- 뒤로가기 버튼 -->
    <button class="btn btn-link" @click="goBack">
      <img src="https://super.so/icon/dark/arrow-left.svg" alt="뒤로가기" class="icon-img" />
    </button>

    <!-- 제목 또는 빈 공간 -->
    <div class="flex-grow-1 text-center"></div>

    <!-- 오른쪽 공유 및 설정 아이콘 -->
    <div class="icons d-flex gap-3 position-relative">
      <!-- 공유하기 버튼 -->
      <button class="btn btn-link">
        <img src="https://super.so/icon/dark/share.svg" alt="공유하기" class="icon-img" />
      </button>

      <!-- 설정 버튼 (점 3개) -->
      <button class="btn btn-link" @click="toggleMenu">
        <img src="https://super.so/icon/dark/more-horizontal.svg" alt="설정" class="icon-img" />
      </button>

      <!-- 설정 메뉴 -->
      <div v-if="menuVisible" class="menu-overlay">
        <button class="btn btn-outline-primary w-100 mb-2" @click="editTeam">수정하기</button>
        <button class="btn btn-outline-danger w-100" @click="deleteTeam">삭제하기</button>
      </div>
    </div>
  </header>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import api from "@/api"; // API 호출 파일

export default {
  setup() {
    const router = useRouter();
    const menuVisible = ref(false);

    // 뒤로가기 함수
    const goBack = () => {
      router.back();
    };

    // 메뉴 표시 토글
    const toggleMenu = () => {
      menuVisible.value = !menuVisible.value;
    };

    // 수정 버튼 클릭 시 수정 페이지로 이동
    const editTeam = () => {
      const teamId = router.currentRoute.value.params.id;
      router.push({ name: "CreateTeam", params: { id: teamId } });
      menuVisible.value = false; // 메뉴 닫기
    };

    // 삭제 버튼 클릭 시 팀 삭제
    const deleteTeam = async () => {
      const teamId = router.currentRoute.value.params.id;
      if (confirm("정말로 이 모임을 삭제하시겠습니까?")) {
        try {
          await api.delete(`/teams/${teamId}`);
          alert("모임이 삭제되었습니다.");
          router.push({ name: "TeamSearch" }); // 삭제 후 목록 페이지로 이동
        } catch (error) {
          console.error("모임 삭제 중 오류 발생:", error);
          alert("모임 삭제에 실패했습니다.");
        } finally {
          menuVisible.value = false; // 메뉴 닫기
        }
      }
    };

    return {
      goBack,
      toggleMenu,
      editTeam,
      deleteTeam,
      menuVisible,
    };
  },
};
</script>

<style scoped>
.header {
  background-color: #f8f9fa;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
  height: 50px;
}

.icon-img {
  width: 24px;
  height: 24px;
}

.position-relative {
  position: relative;
}

.menu-overlay {
  position: absolute;
  right: 0;
  top: 40px;
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  padding: 8px;
  z-index: 1000;
  width: 120px;
}
</style>
