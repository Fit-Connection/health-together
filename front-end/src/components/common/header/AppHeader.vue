<template>
  <header class="header d-flex align-items-center justify-content-between mb-4">
    <div v-if="isMyPage" class="profile-label">프로필</div>
    <div v-else-if="isChatPage" class="profile-label">
      <button class="btn btn-link" @click="goBack">
        <img src="https://super.so/icon/dark/arrow-left.svg" alt="뒤로가기" class="icon-img" />
      </button>
    </div>
    <select v-else v-model="selectedLocation" class="form-select w-auto">
      <option>역삼1동</option>
      <option>강남구</option>
      <option>서초구</option>
    </select>

    <div class="icons d-flex gap-3">
      <button v-for="icon in icons" :key="icon.name" class="btn btn-light d-flex flex-column align-items-center" @click="navigateTo(icon.route)">
        <img :src="icon.img" :alt="icon.name" class="icon-img" />
      </button>
    </div>
  </header>
</template>

<script>
export default {
  props: {
    isMyPage: {
      type: Boolean,
      default: false,
    },
    isChatPage: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      selectedLocation: "역삼1동",
      icons: [
        { name: "검색", img: "https://super.so/icon/dark/search.svg"},
        { name: "좋아요", img: "https://super.so/icon/dark/user-check.svg", route: "/friend-page"},
        { name: "알림", img: "https://super.so/icon/dark/bell.svg", route: "/notification-page" },
      ],
    };
  },
  methods: {
    navigateTo(route) {
      this.$router.push(route);
    },
    goBack() {
      // 이전 페이지로 이동
      this.$router.go(-1);
    }
  }
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

.profile-label {
  font-size: 1.2rem;
  font-weight: bold;
  margin-left: 10px;
}
</style>
