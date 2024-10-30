<!-- src/views/LoginView.vue -->
<template>
  <div class="login-view">
    <LoginForm />
  </div>
</template>

<script>
import LoginForm from '../components/auth/LoginForm.vue';

export default {
  components: {
    LoginForm,
  },
};
</script>

<style scoped>
.login-view {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
