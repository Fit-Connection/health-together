<template>
  <header class="header d-flex align-items-center justify-content-between mb-4">
    <!-- 위치 선택 -->
    <select v-model="selectedLocation" class="form-select w-auto" @change="onLocationChange">
      <option>역삼1동</option>
      <option>강남구</option>
      <option>서초구</option>
    </select>

    <!-- 검색 및 아이콘 섹션 -->
    <div class="search-icons-container d-flex align-items-center">
      <!-- 검색 버튼 -->
      <button
          class="btn btn-light d-flex align-items-center"
          @click="toggleSearch"
      >
        <img src="https://super.so/icon/dark/search.svg" alt="검색" class="icon-img" />
      </button>
      <!-- 검색창 -->
      <input
          v-if="isSearchVisible"
          v-model="searchQuery"
          type="text"
          class="form-control search-input"
          placeholder="검색어를 입력하세요"
          @input="onSearch"
      />
    </div>

    <!-- 오른쪽 아이콘 -->
    <div class="icons d-flex gap-3">
      <button
          v-for="icon in icons"
          :key="icon.name"
          class="btn btn-light d-flex flex-column align-items-center"
          @click="navigateTo(icon.route)"
      >
        <img :src="icon.img" :alt="icon.name" class="icon-img" />
      </button>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      selectedLocation: "역삼1동", // 초기 위치
      isSearchVisible: false, // 검색창 표시 상태
      searchQuery: "", // 검색어
      icons: [
        { name: "좋아요", img: "https://super.so/icon/dark/user-check.svg", route: "/friend-page" },
        { name: "알림", img: "https://super.so/icon/dark/bell.svg", route: "/notification-page" },
      ],
    };
  },
  methods: {
    navigateTo(route) {
      this.$router.push(route); // 라우터 이동
    },
    toggleSearch() {
      this.isSearchVisible = !this.isSearchVisible; // 검색창 표시/숨기기
    },
    onSearch() {
      this.$emit("search", { query: this.searchQuery, location: this.selectedLocation }); // 검색 이벤트 발생
    },
    onLocationChange() {
      this.$emit("locationChange", this.selectedLocation); // 위치 변경 이벤트 발생
    },
  },
};
</script>

<style scoped>
.header {
  background-color: #f8f9fa;
  padding: 10px;
  border-radius: 5px;
}

.icon-img {
  width: 24px;
  height: 24px;
}

.search-icons-container {
  display: flex;
  align-items: center;
  gap: 8px;
}

.search-input {
  width: 200px;
}
</style>
