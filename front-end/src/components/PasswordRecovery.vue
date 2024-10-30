<!-- src/components/PasswordRecovery.vue -->
<template>
  <form @submit.prevent="recoverPassword" class="recovery-form">
    <h2>Password Recovery</h2>

    <label>Email</label>
    <input
        type="email"
        v-model="email"
        @blur="validateEmail"
    />
    <span v-if="error">{{ error }}</span>

    <button type="submit">Send Recovery Link</button>
    <router-link to="/login">Back to Login</router-link>
  </form>
</template>

<script>
import { ref } from 'vue';

export default {
  setup() {
    const email = ref('');
    const error = ref(null);

    const validateEmail = () => {
      error.value = !email.value ? 'Email is required.' : null;
    };

    const recoverPassword = () => {
      validateEmail();
      if (!error.value) {
        alert('Password recovery link sent!');
        // Implement password recovery logic
      }
    };

    return { email, error, recoverPassword, validateEmail };
  },
};
</script>

<style scoped>
.recovery-form {
  max-width: 400px;
  margin: auto;
  padding: 1.5em;
  border: 1px solid #ccc;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
}

input {
  margin-bottom: 0.5em;
}

button {
  margin-top: 1em;
}
</style>
