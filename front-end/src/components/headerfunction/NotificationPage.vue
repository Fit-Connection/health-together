<template>
  <AppHeader :isMyPage="false" />
  <div>
    <FriendRequests/>
  </div>
  <AppFooter />
</template>

<script>

import FriendRequests from "@/components/friend/FriendRequests.vue";
import AppHeader from "@/components/common/header/AppHeader.vue";
import AppFooter from "@/components/common/footer/AppFooter.vue";


export default {
  components: { FriendRequests, AppHeader, AppFooter }
};

</script>

<style scoped>

</style>