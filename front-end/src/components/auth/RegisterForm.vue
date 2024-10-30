<template>
  <form @submit.prevent="register" class="register-form">
    <h2>회원가입</h2>
    <label>사용자 이름</label>
    <input type="text" v-model="username" required />

    <label>이메일</label>
    <input type="email" v-model="email" required />

    <label>비밀번호</label>
    <input type="password" v-model="password" required />

    <label>전화번호</label>
    <input type="text" v-model="phone" required />

    <button type="submit">회원가입</button>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      phone: '',
      status: 'ACTIVE', // 기본 상태 값
      createdAt: new Date(), // 가입 시간
      updatedAt: new Date(), // 업데이트 시간 초기화
    };
  },
  methods: {
    async register() {
      try {
        await axios.post('http://localhost:9090/user/api/register', {
          username: this.username,
          email: this.email,
          password: this.password,
          phone: this.phone,
          status: this.status,
          createdAt: this.createdAt,
          updatedAt: this.updatedAt,
        });
        alert('회원가입 성공');
        this.$router.push('/login'); // 회원가입 성공 후 로그인 페이지로 이동
      } catch (error) {
        if (error.response && error.response.status === 400) {
          alert('회원가입 실패: 입력 데이터를 확인해주세요.');
        } else {
          alert('회원가입 실패: 서버 오류');
        }
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
.register-form {
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
