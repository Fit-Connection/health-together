<template>
  <AppHeader :isMyPage="false" />
  <div>
    <FriendSearch />
  </div>
  <AppFooter />
</template>

<script setup>

import FriendSearch from "@/components/friend/FriendSearch.vue";
import AppHeader from "@/components/common/AppHeader.vue";
import AppFooter from "@/components/common/AppFooter.vue";
</script>

<style scoped>

</style>