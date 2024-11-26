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

    </div>
  </header>
</template>

<script>
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();

    // 뒤로가기 함수
    const goBack = () => {
      router.back();
    };

    return {
      goBack,
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
