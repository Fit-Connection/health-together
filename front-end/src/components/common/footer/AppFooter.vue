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
      activePage: "홈", // 기본값
      navItems: [
        { name: "홈", icon: "https://super.so/icon/dark/home.svg" },
        { name: "챌린지", icon: "https://super.so/icon/dark/list.svg" },
        { name: "글추가", icon: "https://super.so/icon/dark/plus-circle.svg" }, // 가운데 아이콘
        { name: "채팅", icon: "https://super.so/icon/dark/message-circle.svg" },
        { name: "마이페이지", icon: "https://super.so/icon/dark/user.svg" },
      ],
    };
  },
  methods: {
    navigateTo(page) {
      this.activePage = page; // 활성화 상태 업데이트
      switch (page) {
        case "글추가":
          this.$router.push({ name: "CreateTeam" });
          break;
        case "마이페이지":
          this.$router.push({ name: "MyPage" });
          break;
        case "홈":
          this.$router.push({ name: "TeamSearch" });
          break;
        case "채팅":
          this.$router.push({ name: "ChatList" });
          break;
        case "챌린지":
          this.$router.push({ name: "ChallengeTeam" });
          break;
        default:
          console.warn("Unknown navigation target:", page);
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
