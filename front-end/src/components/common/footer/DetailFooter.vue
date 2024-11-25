<template>
  <div class="bottom-nav fixed-bottom d-flex justify-content-around py-2 bg-light border-top">
    <button
        v-for="(nav) in navItems"
        :key="nav.name"
        class="nav-item btn btn-link"
        @click="navigateTo(nav.name)"
    >
      <img
          :src="nav.icon"
          :alt="nav.name"
          :class="nav-icon"
      />
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      navItems: [

        { name: "찜하기", icon: "https://super.so/icon/dark/heart.svg" },
        { name: "팀채팅", icon: "https://super.so/icon/dark/message-circle.svg" },
        { name: "참가", icon: "https://super.so/icon/dark/user-plus.svg" },
      ],
    };
  },
  methods: {
    navigateTo(page) {
      if (page === "찜하기") {
        // this.$router.push({ name: "CreateTeam" });
      } else if(page === "팀채팅") {
        // this.$router.push({ name: "MyPage"});
      } else if(page === "참가") {
        // this.$router.push({ name: "ChatList"})
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

</style>
