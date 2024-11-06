<!-- src/components/common/AppFooter.vue -->
<template>
  <div class="bottom-nav fixed-bottom d-flex justify-content-around py-2 bg-light border-top">
    <button v-for="nav in navItems" :key="nav.name" class="nav-item btn btn-link">
      <img :src="nav.icon" :alt="nav.name" class="nav-icon" />
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      navItems: [
        { name: "홈", icon: "https://img.icons8.com/ios/250/000000/home.png" },
        { name: "지도", icon: "https://img.icons8.com/ios/250/000000/map.png" },
        { name: "즐겨찾기", icon: "https://img.icons8.com/ios/250/000000/star.png" },
        { name: "설정", icon: "https://img.icons8.com/ios/250/000000/settings.png" },
      ],
    };
  },
};
</script>

<style scoped>
.bottom-nav {
  background-color: #ffffff;
}

.nav-icon {
  width: 24px;
  height: 24px;
}
</style>
