<template>
  <div class="register-form">
    <h1>HealthTogether</h1>
    <h2>회원가입</h2>
    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <label>이름</label>
        <input
            type="text"
            v-model="name"
            placeholder="이름을 입력하세요"
            :class="{ 'input-error': nameError }"
            @input="clearError('nameError')"
        />
        <span v-if="nameError" class="error-message">{{ nameError }}</span>
      </div>

      <div class="form-group">
        <label>전화번호</label>
        <input
            type="tel"
            v-model="phone"
            placeholder="전화번호를 입력하세요"
            :class="{ 'input-error': phoneError }"
            @input="clearError('phoneError')"
        />
        <span v-if="phoneError" class="error-message">{{ phoneError }}</span>
      </div>

      <div class="form-group">
        <label>이메일</label>
        <div class="email-input-group">
          <input
              type="email"
              v-model="email"
              @blur="validateEmail"
              @input="clearError('emailError')"
              placeholder="이메일을 입력하세요"
              :class="{ 'input-error': emailError || emailConflictError }"
              :disabled="emailVerified"
          />
          <button type="button" @click="sendVerificationCode" :disabled="emailVerified || verificationSent">
            인증번호 전송
          </button>
        </div>
        <span v-if="emailError" class="error-message">{{ emailError }}</span>
        <span v-if="emailConflictError" class="error-message">{{ emailConflictError }}</span>
        <span v-if="verificationSentMessage" class="success-message">{{ verificationSentMessage }}</span>
      </div>

      <div class="form-group" v-if="emailVerificationSent && !emailVerified">
        <div class="email-input-group">
          <input
              type="text"
              v-model="emailVerification"
              placeholder="인증번호 입력"
              @input="clearError('emailVerificationError')"
          />
          <button type="button" @click="verifyEmailCode">인증번호 확인</button>
        </div>
        <span v-if="emailVerificationError" class="error-message">{{ emailVerificationError }}</span>
      </div>

      <div class="form-group">
        <label>비밀번호</label>
        <input
            type="password"
            v-model="password"
            placeholder="비밀번호를 입력하세요"
            :class="{ 'input-error': passwordError }"
            @input="clearError('passwordError')"
        />
      </div>

      <div class="form-group">
        <label>비밀번호 확인</label>
        <input
            type="password"
            v-model="passwordConfirm"
            placeholder="비밀번호를 재입력하세요"
            :class="{ 'input-error': passwordError }"
            @input="clearError('passwordError')"
        />
        <span v-if="passwordError" class="error-message">{{ passwordError }}</span>
      </div>

      <button type="submit" class="submit-button">회원가입 완료</button>
      <span v-if="generalError" class="error-message">{{ generalError }}</span>
    </form>

    <div class="footer-links">
      <span>HealthTogether </span>
      <a href="#">이용약관</a>, <a href="#">정책</a>
    </div>

    <div class="login-link">
      <hr />
      <p>이미 아이디가 있으신가요? <br> <a href="/login">로그인</a></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RegistForm',
  data() {
    return {
      name: '',
      phone: '',
      email: '',
      emailVerification: '',
      password: '',
      passwordConfirm: '',
      emailVerificationSent: false,
      emailVerified: false,
      nameError: '',
      phoneError: '',
      emailError: '',
      passwordError: '',
      emailVerificationError: '',
      emailConflictError: '',
      generalError: '',
      verificationSent: false,
      verificationSentMessage: '', // Message for verification status
    };
  },
  methods: {
    validateEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!this.email) {
        this.emailError = '이메일을 입력하세요.';
      } else if (!emailPattern.test(this.email)) {
        this.emailError = '유효한 이메일 형식을 입력하세요.';
      } else {
        this.emailError = '';
      }
    },

    clearError(field) {
      this[field] = '';
    },

    async sendVerificationCode() {
      if (this.emailError || this.verificationSent) return;
      try {
        await axios.post('http://localhost:9090/user/api/email/send', { mail: this.email });
        this.emailVerificationSent = true;
        this.verificationSent = true;
        this.verificationSentMessage = '인증번호가 전송되었습니다.'; // Show initial success message
      } catch (error) {
        this.generalError = '인증번호 전송 실패: 서버 오류';
        console.error(error);
      }
    },

    async verifyEmailCode() {
      try {
        const response = await axios.post('http://localhost:9090/user/api/email/verify', {
          mail: this.email,
          verifyCode: this.emailVerification,
        });
        if (response.data.status === "success") {
          this.emailVerified = true;
          this.emailVerificationError = '';
          this.verificationSentMessage = '인증이 완료되었습니다.'; // Update message on successful verification
        } else {
          this.emailVerificationError = '인증번호가 일치하지 않습니다.';
        }
      } catch (error) {
        this.emailVerificationError = '이메일 인증 실패: 서버 오류';
        console.error(error);
      }
    },

    async onSubmit() {
      let isValid = true;

      if (!this.name) {
        this.nameError = '이름을 입력하세요.';
        isValid = false;
      }

      if (!this.phone) {
        this.phoneError = '전화번호를 입력하세요.';
        isValid = false;
      }

      if (!this.email) {
        this.emailError = '이메일을 입력하세요.';
        isValid = false;
      }

      if (!this.password) {
        this.passwordError = '비밀번호를 입력하세요.';
        isValid = false;
      } else if (this.password !== this.passwordConfirm) {
        this.passwordError = '비밀번호가 일치하지 않습니다.';
        isValid = false;
      }

      if (!this.emailVerified) {
        this.emailVerificationError = '이메일 인증을 완료해 주세요.';
        isValid = false;
      }

      if (!isValid) return;

      try {
        await axios.post('http://localhost:9090/user/api/register', {
          username: this.name,
          email: this.email,
          phone: this.phone,
          password: this.password,
        });
        alert('회원가입이 완료되었습니다.');
        this.$router.push('/login');
      } catch (error) {
        if (error.response && error.response.status === 409) {
          alert('이미 존재하는 이메일입니다.\n비밀번호 찾기로 이동합니다.');
          this.$router.push('/passwordRecovery');
        } else {
          this.generalError = '회원가입 실패: 서버 오류';
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
  padding: 20px;
  font-family: Arial, sans-serif;
  text-align: center;
}

h1 {
  color: #3d5afe;
}

h2 {
  font-size: 1.5em;
  margin: 10px 0 20px;
}

.form-group {
  margin-bottom: 15px;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
input[type="tel"],
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

.error-message {
  color: #ff4d4f;
  font-size: 0.9rem;
  margin-top: 2px;
  margin-bottom: 5px;
  display: block;
}

.success-message {
  color: #4caf50;
  font-size: 0.9rem;
  margin-top: 5px;
  display: block;
}

.email-input-group {
  display: flex;
  gap: 5px;
}

.email-input-group input[type="email"],
.email-input-group input[type="text"] {
  flex: 1;
}

.email-input-group button {
  padding: 10px 15px;
  border: 1px solid #ddd;
  background-color: #f9f9f9;
  border-radius: 5px;
  cursor: pointer;
}

.submit-button {
  width: 100%;
  padding: 10px;
  background-color: #90caf9;
  border: none;
  border-radius: 5px;
  color: white;
  font-weight: bold;
  cursor: pointer;
  margin-top: 10px;
}

.footer-links {
  margin-top: 15px;
  font-size: 0.9em;
}

.footer-links a {
  color: #3d5afe;
  text-decoration: none;
}

.login-link {
  margin-top: 20px;
  font-size: 0.9em;
}

.login-link hr {
  margin-top: 15px;
  margin-bottom: 10px;
}

.login-link a {
  color: #3d5afe;
  text-decoration: none;
}
</style>
