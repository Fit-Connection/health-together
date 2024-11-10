<template>
  <div class="recovery-form">
    <h1>HealthTogether</h1>
    <img src="@/assets/Weblogo.png" class="img-size" alt="HealthTogether Logo" />
    <h2>비밀번호 찾기</h2>
    <form @submit.prevent="recoverPassword">
      <!-- 이메일 입력 -->
      <div class="form-group">
        <label>Email</label>
        <input
            type="email"
            v-model="email"
            @blur="validateEmail"
            placeholder="이메일을 입력하세요"
            :class="{ 'input-error': emailError }"
            :disabled="emailVerified"
        />
        <span v-if="emailError" class="error-message">{{ emailError }}</span>
      </div>

      <div v-if="emailVerificationSent && !emailVerified" class="form-group">
        <label>인증 번호</label>
        <input
            type="text"
            v-model="emailVerification"
            placeholder="인증 번호를 입력하세요"
        />
      </div>

      <span v-if="verificationSentMessage"
            :class="{
        'success-message': emailVerified,
        'error-message': !emailVerified && verificationSentMessage.includes('일치하지 않습니다.'),
        'send-message': !emailVerified && !verificationSentMessage.includes('일치하지 않습니다.')
      }">
        {{ verificationSentMessage }}
      </span>

      <div v-if="!emailVerified">
        <button
            type="button"
            class="submit-button"
            @click="emailVerificationSent ? verifyEmailCode() : sendVerificationEmail()"
            :disabled="sendingVerification"
        >
          {{ emailVerificationSent ? '인증' : '인증 메일 전송' }}
        </button>
      </div>

      <div v-if="emailVerified">
        <div class="form-group">
          <label>비밀번호 입력</label>
          <input
              type="password"
              v-model="password"
              placeholder="새 비밀번호를 입력하세요"
              required
          />
        </div>
        <div class="form-group">
          <label>비밀번호 재입력</label>
          <input
              type="password"
              v-model="passwordConfirm"
              placeholder="새 비밀번호를 다시 입력하세요"
              required
          />
        </div>
        <span v-if="passwordError" class="error-message">{{ passwordError }}</span>
        <button type="button" @click="changePassword" class="submit-button">비밀번호 변경</button>
      </div>
    </form>
    <router-link to="/login" class="link-to-login">로그인</router-link>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RegistForm',
  data() {
    return {
      email: '',
      emailVerification: '',
      password: '',
      passwordConfirm: '',
      emailVerificationSent: false,
      emailVerified: false,
      emailError: '',
      passwordError: '',
      verificationSentMessage: '', // 상태 메시지
      sendingVerification: false,  // 중복 클릭 방지
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

    async sendVerificationEmail() {
      if (this.emailError || this.sendingVerification) return;
      this.sendingVerification = true;
      this.verificationSentMessage = '';

      this.validateEmail();
      if (!this.emailError) {
        try {
          await axios.post('http://localhost:9090/user/api/email/send', { mail: this.email });
          this.emailVerificationSent = true;
          this.verificationSentMessage = '인증번호가 전송되었습니다.';
        } catch (error) {
          this.verificationSentMessage = '인증 메일 전송 실패: 서버 오류';
          console.error(error);
        } finally {
          this.sendingVerification = false;
        }
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
          this.verificationSentMessage = '인증이 완료되었습니다.';
        } else {
          this.verificationSentMessage = '인증번호가 일치하지 않습니다.';
        }
      } catch (error) {
        this.verificationSentMessage = '이메일 인증 실패: 서버 오류';
        console.error(error);
      }
    },

    async changePassword() {
      if (!this.password || !this.passwordConfirm) {
        this.passwordError = '비밀번호를 입력하세요.';
        return;
      }
      if (this.password !== this.passwordConfirm) {
        this.passwordError = '비밀번호가 일치하지 않습니다.';
        return;
      }
      this.passwordError = '';
      try {
        await axios.post('http://localhost:9090/user/api/change-password', {
          email: this.email,
          password: this.password,
        });
        alert('비밀번호가 변경되었습니다.');
        this.$router.push('/login');
      } catch (error) {
        this.passwordError = '비밀번호 변경에 실패했습니다.';
        console.error(error);
      }
    },

    async recoverPassword() {
      if (!this.emailVerificationSent) {
        await this.sendVerificationEmail();
      } else if (!this.emailVerified) {
        await this.verifyEmailCode();
      } else {
        await this.changePassword();
      }
    },
  },
};
</script>

<style scoped>
h1 {
  color: #3d5afe;
}

.img-size {
  width: 250px;
  height: auto;
  display: block;
  margin: 0 auto;
}

.recovery-form {
  max-width: 400px;
  width: 290px;
  margin: auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  text-align: center;
}

h2 {
  text-align: center;
  font-size: 1.5rem;
  color: #333;
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

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}

.input-error {
  border-color: #ff4d4f;
}

input:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0px 0px 5px rgba(0, 123, 255, 0.3);
}

.error-message {
  color: #ff4d4f;
  font-size: 0.9rem;
  margin-top: 2px;
  margin-bottom: 2px;
  display: block;
}

.success-message {
  color: #4caf50;
  font-size: 0.9rem;
  margin-top: 2px;
  margin-bottom: 2px;
  display: block;
}

.send-message {
  font-size: 0.9rem;
  margin-top: 2px;
  margin-bottom: 2px;
  display: block;
}

button {
  padding: 0.8rem;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

.link-to-login {
  text-align: center;
  display: block;
  margin-top: 1.5rem;
  font-size: 0.9rem;
  color: #007bff;
  text-decoration: none;
}

.link-to-login:hover {
  text-decoration: underline;
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
</style>
