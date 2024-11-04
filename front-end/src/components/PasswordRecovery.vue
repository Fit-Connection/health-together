<template>
  <form @submit.prevent="recoverPassword" class="recovery-form">
    <h2>비밀번호 찾기</h2>

    <label>Email</label>
    <input
        type="email"
        v-model="email"
        @blur="validateEmail"
    />
    <span v-if="error">{{ error }}</span>

    <!-- 인증 코드 입력란과 인증 버튼 표시 여부를 조건부로 설정 -->
    <div v-if="isCodeSent">
      <label>인증 번호</label>
      <input
          type="text"
          v-model="verificationCode"
          placeholder="인증 번호를 입력하세요"
      />
    </div>

    <!-- 버튼 변경 -->
    <button type="button" @click="isCodeSent ? verifyCode() : sendVerificationEmail()">
      {{ isCodeSent ? '인증' : '인증 메일 전송' }}
    </button>

    <router-link to="/login">로그인 화면</router-link>
  </form>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const email = ref('');
    const verificationCode = ref('');
    const error = ref(null);
    const isCodeSent = ref(false);

    const validateEmail = () => {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 이메일 형식 정규식
      if (!email.value) {
        error.value = '이메일을 입력하세요.';
      } else if (!emailPattern.test(email.value)) {
        error.value = '유효한 이메일 형식을 입력하세요.';
      } else {
        error.value = null;
      }
    };

    const checkEmailExists = async () => {
      try {
        const response = await axios.post('/user/api/check-email', { email: email.value });
        return response.data.exists; // 서버에서 이메일 존재 여부를 반환한다고 가정
      } catch (e) {
        error.value = '서버와의 통신에 실패했습니다.';
        return false;
      }
    };

    const sendVerificationEmail = async () => {
      validateEmail();
      if (!error.value) {
        const exists = await checkEmailExists();
        if (exists) {
          alert('인증 메일이 전송되었습니다.');
          isCodeSent.value = true;
          // 이메일 인증 메일 전송 로직을 여기에 추가
        } else {
          error.value = '입력하신 이메일이 존재하지 않습니다.';
        }
      }
    };

    const verifyCode = () => {
      if (verificationCode.value === '1234') { // 실제 인증 코드 로직으로 대체 필요
        alert('인증이 완료되었습니다.');
        // 인증 완료 후 추가 작업 수행
      } else {
        alert('인증 번호가 올바르지 않습니다.');
      }
    };

    return {
      email,
      verificationCode,
      error,
      isCodeSent,
      validateEmail,
      sendVerificationEmail,
      verifyCode,
    };
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
