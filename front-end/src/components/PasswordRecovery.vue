<template>
  <form @submit.prevent="recoverPassword" class="recovery-form">
    <h1>HealthTogether</h1>
    <img src="@/assets/Weblogo.png" class="img-size" alt="HealthTogether Logo" />
    <h2>비밀번호 찾기</h2>

    <!-- 이메일 입력 -->
    <label>Email</label>
    <input
        type="email"
        v-model="email"
        @blur="validateEmail"
        placeholder="이메일을 입력하세요"
        :class="{ 'input-error': error }"
    />
    <span v-if="error" class="error-message">{{ error }}</span>

    <!-- 인증 코드 입력란 (isCodeSent가 true일 때만 표시) -->
    <div v-if="isCodeSent && !isVerified">
      <label>인증 번호</label>
      <input
          type="text"
          v-model="verificationCode"
          placeholder="인증 번호를 입력하세요"
      />
    </div>

    <!-- 인증 메일 전송 / 인증 확인 버튼 -->
    <button type="button" @click="isCodeSent ? verifyCode() : sendVerificationEmail()">
      {{ isCodeSent ? '인증' : '인증 메일 전송' }}
    </button>

    <!-- 비밀번호 변경 입력란 (이메일 인증이 완료되면 표시) -->
    <div v-if="isVerified">
      <div class="form-group">
        <label>비밀번호 입력</label>
        <input
            type="password"
            v-model="newPassword"
            placeholder="새 비밀번호를 입력하세요"
            required
        />
      </div>
      <div class="form-group">
        <label>비밀번호 재입력</label>
        <input
            type="password"
            v-model="confirmPassword"
            placeholder="새 비밀번호를 다시 입력하세요"
            required
        />
      </div>
      <button type="button" @click="changePassword" class="submit-button">비밀번호 변경</button>
    </div>

    <router-link to="/login" class="link-to-login">로그인 화면</router-link>
  </form>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const email = ref('');
    const verificationCode = ref('');
    const newPassword = ref('');
    const confirmPassword = ref('');
    const error = ref(null);
    const isCodeSent = ref(false);
    const isVerified = ref(false);

    // 이메일 유효성 검사
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

    // 서버에서 이메일 존재 여부 확인
    const checkEmailExists = async () => {
      try {
        const response = await axios.post('/user/api/check-email', { email: email.value });
        return response.data.exists; // 서버에서 이메일 존재 여부를 반환한다고 가정
      } catch (e) {
        error.value = '서버와의 통신에 실패했습니다.';
        return false;
      }
    };

    // 인증 메일 전송 함수
    const sendVerificationEmail = async () => {
      validateEmail();
      if (!error.value) {
        const exists = await checkEmailExists();
        if (exists) {
          alert('인증 메일이 전송되었습니다.');
          isCodeSent.value = true;
          // 실제 이메일 인증 메일 전송 로직 추가 필요
          await axios.post('http://localhost:9090/user/api/email/send', { email: email.value });
        } else {
          error.value = '입력하신 이메일이 존재하지 않습니다.';
        }
      }
    };

    // 인증 코드 검증 함수
    const verifyCode = async () => {
      try {
        const response = await axios.post('http://localhost:9090/user/api/email/verify', {
          email: email.value,
          verificationCode: verificationCode.value,
        });
        if (response.data.success) {
          alert('인증이 완료되었습니다.');
          isVerified.value = true; // 인증 완료 상태 설정
        } else {
          alert('인증 번호가 올바르지 않습니다.');
        }
      } catch (error) {
        alert('서버와의 통신에 실패했습니다.');
      }
    };

    // 비밀번호 변경 함수
    const changePassword = async () => {
      if (newPassword.value !== confirmPassword.value) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
      }
      try {
        await axios.post('/user/api/change-password', {
          email: email.value,
          newPassword: newPassword.value,
        });
        alert('비밀번호가 변경되었습니다.');
        // 비밀번호 변경 후 로그인 페이지로 이동
        window.location.href = '/login';
      } catch (error) {
        alert('비밀번호 변경에 실패했습니다.');
      }
    };

    return {
      email,
      verificationCode,
      newPassword,
      confirmPassword,
      error,
      isCodeSent,
      isVerified,
      validateEmail,
      sendVerificationEmail,
      verifyCode,
      changePassword,
    };
  },
};
</script>

<style scoped>
h1 {
  color: #3d5afe;
}

.img-size {
  width: 300px;
  height: auto;
  display: block;
  margin: 0 auto; /* 수평 중앙 정렬 */
}

.recovery-form {
  max-width: 400px;
  margin: 2em auto;
  padding: 2em;
  background-color: #fff;
  display: flex;
  flex-direction: column;
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
  margin-bottom: 1rem;
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
</style>
