<template>
  <AppHeader :isMyPage="false" />
  <div>
    <FriendList />
  </div>
  <AppFooter />
</template>

<script>
import FriendList from "@/components/friend/FriendList.vue";
import AppHeader from "@/components/common/header/AppHeader.vue";
import AppFooter from "@/components/common/footer/AppFooter.vue";

export default {
  components: { FriendList, AppHeader, AppFooter}
};
</script>
