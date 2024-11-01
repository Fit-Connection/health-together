<template>
  <div class="email-verification">
    <h2>이메일 인증</h2>

    <!-- 이메일 입력 폼 -->
    <label for="email">이메일</label>
    <input
        type="email"
        v-model="email"
        @blur="validateEmail"
        placeholder="이메일 주소를 입력하세요"
    />
    <span v-if="emailError">{{ emailError }}</span>

    <!-- 인증 코드 입력 폼 -->
    <div v-if="isCodeSent">
      <label for="verificationCode">인증 코드</label>
      <input
          type="text"
          v-model="verificationCode"
          placeholder="이메일로 받은 인증 코드를 입력하세요"
      />
    </div>

    <!-- 버튼들 -->
    <button v-if="!isCodeSent" @click="sendVerificationEmail">인증 코드 전송</button>
    <button v-if="isCodeSent" @click="verifyEmailCode">인증</button>

    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      email: "",
      verificationCode: "",
      emailError: null,
      isCodeSent: false,
      message: "",
    };
  },
  methods: {
    validateEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      this.emailError = !emailPattern.test(this.email) ? "유효한 이메일 형식을 입력하세요." : null;
    },
    async sendVerificationEmail() {
      this.validateEmail();
      if (this.emailError) return;

      try {
        const response = await axios.post("/api/verify-email", { email: this.email });
        this.message = response.data.message;
        this.isCodeSent = true;
      } catch (error) {
        this.message = "이메일 전송 중 오류가 발생했습니다.";
      }
    },
    async verifyEmailCode() {
      try {
        const response = await axios.post("/api/verification-email-sent", {
          email: this.email,
          verificationCode: this.verificationCode,
        });
        if (response.data.valid) {
          this.message = "이메일 인증이 성공했습니다!";
        } else {
          this.message = "인증 코드가 올바르지 않습니다.";
        }
      } catch (error) {
        this.message = "인증 과정에서 오류가 발생했습니다.";
      }
    },
  },
};
</script>

<style scoped>
.email-verification {
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
  padding: 0.5em;
  font-size: 1em;
}

button {
  margin-top: 1em;
  padding: 0.5em;
  font-size: 1em;
  cursor: pointer;
}
</style>
