<template>
  <header class="header d-flex align-items-center justify-content-between mb-4">
    <div v-if="isMyPage" class="profile-label">프로필</div>
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
  },
  data() {
    return {
      selectedLocation: "역삼1동",
      icons: [
        { name: "검색", img: "https://super.so/icon/dark/search.svg", route: "/search-page" },
        { name: "좋아요", img: "https://super.so/icon/dark/heart.svg", route: "/friend-page"},
        { name: "알림", img: "https://super.so/icon/dark/bell.svg", route: "/notification-page" },
      ],
    };
  },
  methods: {
    navigateTo(route) {
      this.$router.push(route);
    },
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
  padding: 10px;
}
</style>
