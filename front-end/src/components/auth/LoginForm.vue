<!-- src/components/auth/LoginForm.vue -->
<template>
  <form @submit.prevent="login" class="login-form">
    <h2>로그인</h2>

    <label>사용자 이름</label>
    <input type="text" v-model="username" required />
    <br>
    <label>비밀번호</label>
    <input type="password" v-model="password" required />

    <button type="submit" class="btn-primary">로그인</button>
    <button type="button" @click="goToRegist" class="btn-secondary">회원가입</button>
    <button type="button" @click="goToPassWordRecovery" class="btn-secondary">비밀번호 찾기</button>
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
        this.$router.push('/');
      } catch (error) {
        if (error.response && error.response.status === 401) {
          alert('아이디 또는 비밀번호가 잘못되었습니다.');
        } else {
          alert('로그인 실패: 서버 오류');
        }
        console.error(error);
      }
    },
    goToRegist() {
      this.$router.push('/register');
    },
    goToPassWordRecovery(){
      this.$router.push('/passwordRecovery')
    },
  },
};
</script>

<style scoped>
.login-form {
  max-width: 350px;
  margin: 50px auto;
  padding: 2rem;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

h2 {
  text-align: center;
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 1.5rem;
}

label {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 0.5rem;
}

input {
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
  margin-bottom: 1rem;
  transition: border 0.3s ease;
}

input:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0px 0px 5px rgba(0, 123, 255, 0.3);
}

button {
  padding: 0.8rem 1.2rem;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-top: 0.5rem;
}

.btn-primary {
  background-color: #007bff;
  color: white;
  width: 100%;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
  width: 100%;
}

.btn-secondary:hover {
  background-color: #565e64;
}
</style>
