<!-- src/components/auth/LoginForm.vue -->
<template>
  <form @submit.prevent="login" class="login-form">
    <h2>로그인</h2>
    <label>사용자 이름</label>
    <input type="text" v-model="username" required />

    <label>비밀번호</label>
    <input type="password" v-model="password" required />

    <button type="submit">로그인</button>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        await axios.post('http://localhost:9090/user/api/login', {
          username: this.username,
          password: this.password,
        });
        alert('로그인 성공');
        // JWT 토큰을 localStorage에 저장하거나 전역 상태로 관리 가능
        // localStorage.setItem('token', response.data.token);
        this.$router.push('/'); // 로그인 후 홈으로 리다이렉트
      } catch (error) {
        if (error.response && error.response.status === 401) {
          alert('아이디 또는 비밀번호가 잘못되었습니다.');
        } else {
          alert('로그인 실패: 서버 오류');
        }
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
.login-form {
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
