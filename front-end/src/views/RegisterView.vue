<!-- src/views/RegisterView.vue -->
<template>
  <div class="register-view">
    <RegisterForm />
  </div>
</template>

<script>
import RegisterForm from '../components/auth/RegisterForm.vue';

export default {
  components: {
    RegisterForm,
  },
};
</script>

<style scoped>
.register-view {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
