<template>
  <div class="bottom-nav fixed-bottom d-flex justify-content-around py-2 bg-light border-top">
    <button
        v-for="(nav, index) in navItems"
        :key="nav.name"
        class="nav-item btn btn-link"
        @click="navigateTo(nav.name)"
    >
      <img
          :src="nav.icon"
          :alt="nav.name"
          :class="['nav-icon', { 'nav-icon-large': index === 2 }]"
      />
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      navItems: [
        { name: "홈", icon: "https://super.so/icon/dark/home.svg" },
        { name: "글목록", icon: "https://super.so/icon/dark/list.svg" },
        { name: "글추가", icon: "https://super.so/icon/dark/plus-circle.svg" }, // 가운데 아이콘
        { name: "채팅", icon: "https://super.so/icon/dark/message-circle.svg" },
        { name: "마이페이지", icon: "https://super.so/icon/dark/user.svg" },
      ],
    };
  },
  methods: {
    navigateTo(page) {
      if (page === "글추가") {
        this.$router.push({ name: "CreateTeam" });
      } else if(page === "마이페이지") {
        this.$router.push({ name: "MyPage"});
      } else if(page === "홈") {
        this.$router.push({ name: "TeamSearch"})
      } else if(page === "채팅") {
        this.$router.push({ name: "ChatList"})
      } else{
        console.log(`${page} 페이지로 이동`);
      }
    },
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
.nav-icon-large {
  width: 36px; /* 가운데 아이콘만 크게 */
  height: 36px;
}
</style>
