<template>
  <AppHeader :isMyPage="false" />
  <div>
    <FriendRequests/>
  </div>
  <AppFooter />
</template>

<script>

import FriendRequests from "@/components/friend/FriendRequests.vue";
import AppHeader from "@/components/common/AppHeader.vue";
import AppFooter from "@/components/common/AppFooter.vue";


export default {
  components: { FriendRequests, AppHeader, AppFooter }
};

</script>

<style scoped>

</style>