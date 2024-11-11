<template>
  <div class="register-form">
    <h1>HealthTogether</h1>
    <img src="@/assets/Weblogo.png" class="img-size" alt="HealthTogether Logo" />
    <div class="write-form">
      <form @submit.prevent="onSubmit">
        <div class="form-group">
          <label>이메일</label>
          <input
              type="email"
              v-model="email"
              @blur="validateEmail"
              @input="clearError('emailError')"
              placeholder="이메일을 입력하세요"
              :class="{ 'input-error': emailError }"
          />
          <span v-if="emailError" class="error-message">{{ emailError }}</span>
        </div>

        <div class="form-group">
          <label>비밀번호</label>
          <input
              type="password"
              v-model="password"
              @input="clearError('passwordError')"
              placeholder="비밀번호를 입력하세요"
              :class="{ 'input-error': passwordError }"
          />
          <span v-if="passwordError" class="error-message">{{ passwordError }}</span>
        </div>

        <div class="form-options">
          <a href="#" @click.prevent="goToPasswordRecovery" class="find-password">비밀번호 찾기</a>
        </div>

        <div class="button-group">
          <button type="button" @click="onSubmit" class="submit-button">로그인</button>
          <button type="button" @click="goToRegister" class="submit-button secondary">회원가입</button>
        </div>
      </form>
    </div>

    <div class="footer">
      <span>© 2024 HealthTogether</span>
      <br />
      <span>All rights reserved.</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginForm',
  data() {
    return {
      email: '',
      password: '',
      emailError: '',
      passwordError: '', // Password error message state
    };
  },
  methods: {
    validateEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      this.emailError = emailPattern.test(this.email) ? '' : '유효한 이메일 형식을 입력하세요.';
    },

    clearError(field) {
      this[field] = '';
    },

    async onSubmit() {
      // Validate email and password before submitting
      this.validateEmail();
      this.passwordError = this.password ? '' : '비밀번호를 입력하세요.';
      if (this.emailError || this.passwordError) return;

      try {
        await axios.post('http://localhost:9090/user/api/login', {
          email: this.email,
          password: this.password,
        });
        alert('로그인 성공');
        this.$router.push('/');
      } catch (error) {
        if (error.response && error.response.status === 403) {
          alert('이메일 또는 비밀번호가 잘못되었습니다.');
        } else {
          alert('로그인 실패: 서버 오류');
        }
        console.error(error);
      }
    },
    goToRegister() {
      this.$router.push('/register');
    },
    goToPasswordRecovery() {
      this.$router.push('/passwordRecovery');
    },
  },
};
</script>


<style scoped>

.img-size {
  width: 250px;
  height: auto;
  display: block;
  margin: 0 auto; /* 수평 중앙 정렬 */
}

.register-form {
  max-width: 400px;
  margin: 10px auto; /* 상단 여백을 줄임 */
  padding: 20px;
  font-family: Arial, sans-serif;
  text-align: center;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

h1 {
  color: #3d5afe;
}

.write-form {
  margin-bottom: 20px; /* 하단 여백 조정 */
  flex-grow: 1;
}

.form-group {
  margin-bottom: 5px; /* 입력란과 다음 요소 간의 간격을 줄임 */
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}

.input-error {
  border-color: #ff4d4f;
}

/* 오류 메시지 스타일 */
.error-message {
  color: #ff4d4f;
  font-size: 0.9rem;
  margin-top: 2px; /* 입력란과 메시지 사이의 간격 */
  margin-bottom: 5px; /* 오류 메시지와 다음 요소 사이의 간격 */
  display: block;
}

.form-options {
  text-align: right;
  margin-top: -5px; /* 링크와 비밀번호 필드 사이의 간격을 줄임 */
  margin-bottom: 15px;
}

.find-password {
  font-size: 0.9em;
  color: #3d5afe;
  text-decoration: none;
}

.button-group {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  margin-top: 10px;
}

.submit-button {
  flex: 1;
  padding: 10px;
  background-color: #3d5afe;
  border: none;
  border-radius: 5px;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

.submit-button.secondary {
  background-color: #f9f9f9;
  color: #3d5afe;
  border: 1px solid #3d5afe;
}

.footer {
  margin-top: auto;
  text-align: center;
  font-size: 0.9em;
  padding: 10px 0;
}

.footer span {
  display: block;
  color: #666;
}
</style>
