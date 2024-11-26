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
        { name: "챌린지", icon: "data:image/svg+xml,%3Csvg%20viewBox%3D%220%200%20256%20256%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%3E%3Crect%20fill%3D%22none%22%20height%3D%22256%22%20width%3D%22256%22/%3E%3Cpath%20d%3D%22M56%2C56v55.1c0%2C39.7%2C31.8%2C72.6%2C71.5%2C72.9A72%2C72%2C0%2C0%2C0%2C200%2C112V56a8%2C8%2C0%2C0%2C0-8-8H64A8%2C8%2C0%2C0%2C0%2C56%2C56Z%22%20fill%3D%22none%22%20stroke%3D%22%23000%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22%20stroke-width%3D%2212%22/%3E%3Cline%20fill%3D%22none%22%20stroke%3D%22%23000%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22%20stroke-width%3D%2212%22%20x1%3D%2296%22%20x2%3D%22160%22%20y1%3D%22224%22%20y2%3D%22224%22/%3E%3Cline%20fill%3D%22none%22%20stroke%3D%22%23000%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22%20stroke-width%3D%2212%22%20x1%3D%22128%22%20x2%3D%22128%22%20y1%3D%22184%22%20y2%3D%22224%22/%3E%3Cpath%20d%3D%22M198.2%2C128H208a32%2C32%2C0%2C0%2C0%2C32-32V80a8%2C8%2C0%2C0%2C0-8-8H200%22%20fill%3D%22none%22%20stroke%3D%22%23000%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22%20stroke-width%3D%2212%22/%3E%3Cpath%20d%3D%22M58%2C128H47.9a32%2C32%2C0%2C0%2C1-32-32V80a8%2C8%2C0%2C0%2C1%2C8-8h32%22%20fill%3D%22none%22%20stroke%3D%22%23000%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22%20stroke-width%3D%2212%22/%3E%3C/svg%3E" },
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
      } else if(page === "챌린지") {
        this.$router.push({ name: "ChallengePage"})
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
