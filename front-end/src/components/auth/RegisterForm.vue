<template>
  <form @submit.prevent="register" class="register-form">
    <h2>회원가입</h2>

    <!-- Step 1: 이름 입력 -->
    <div v-if="currentStep === 1" class="form-group">
      <label>성</label>
      <input type="text" v-model="lastName" required />

      <label>이름</label>
      <input type="text" v-model="firstName" required />

      <button type="button" @click="nextStep" class="btn-primary">다음</button>
    </div>

    <!-- Step 2: 전화번호 입력 -->
    <div v-if="currentStep === 2" class="form-group">
      <label>전화번호</label>
      <input type="text" v-model="phone" required />

      <div class="button-group">
        <button type="button" @click="prevStep" class="btn-secondary">이전</button>
        <button type="button" @click="nextStep" class="btn-primary">다음</button>
      </div>
    </div>

    <!-- Step 3: 이메일과 비밀번호 입력 -->
    <div v-if="currentStep === 3" class="form-group">
      <label>이메일</label>
      <input type="email" v-model="email" required />
      <button type="button" @click="sendEmailVerification" class="btn-primary">인증</button>

      <div v-if="emailVerificationSent">
        <label>인증 코드</label>
        <input type="text" v-model="verificationCode" required />
        <button type="button" @click="verifyEmailCode" class="btn-secondary">확인</button>
      </div>

      <label>비밀번호</label>
      <input type="password" v-model="password" required />

      <div class="button-group">
        <button type="button" @click="prevStep" class="btn-secondary">이전</button>
        <button type="submit" class="btn-primary">회원가입</button>
      </div>
    </div>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      currentStep: 1,
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      phone: '',
      status: 'ACTIVE',
      createdAt: new Date(),
      updatedAt: new Date(),
      verificationCode: '',
      emailVerificationSent: false,
      emailVerified: false,
    };
  },
  methods: {
    nextStep() {
      if (this.currentStep === 1 && (!this.firstName || !this.lastName)) {
        alert('성명은 필수 입력 항목입니다.');
        return;
      }
      if (this.currentStep === 2 && !this.phone) {
        alert('전화번호를 입력해 주세요.');
        return;
      }
      if (this.currentStep === 3 && (!this.email || !this.password)) {
        alert('이메일과 비밀번호를 모두 입력해 주세요.');
        return;
      }
      this.currentStep++;
    },
    prevStep() {
      if (this.currentStep > 1) this.currentStep--;
    },
    async sendEmailVerification() {
      try {
        await axios.post('http://localhost:9090/user/api/email/send', {
          mail: this.email,
        });
        this.emailVerificationSent = true;
        alert('인증 코드가 이메일로 발송되었습니다.');
      } catch (error) {
        alert('인증 코드 발송 실패: 서버 오류');
        console.error(error);
      }
    },
    async verifyEmailCode() {
      try {
        const response = await axios.post('http://localhost:9090/user/api/email/verify', {
          mail: this.email,
          verifyCode: this.verificationCode,
        });
        if (response.data.status === "success") {  // 서버가 성공 시 "success" 상태를 반환하는지 확인
          this.emailVerified = true;
          alert('이메일 인증 성공');
        } else {
          alert('이메일 인증 실패: 인증 코드를 확인하세요.');
        }
      } catch (error) {
        alert('이메일 인증 실패: 서버 오류');
        console.error(error);
      }
    },

    async register() {
      if (!this.emailVerified) {
        alert('이메일 인증을 완료해 주세요.');
        return;
      }
      try {
        await axios.post('http://localhost:9090/user/api/register', {
          username: `${this.lastName} ${this.firstName}`,
          email: this.email,
          password: this.password,
          phone: this.phone,
          status: this.status,
          createdAt: this.createdAt,
          updatedAt: this.updatedAt,
        });
        alert('회원가입 성공');
        this.$router.push('/login');
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

.form-group {
  display: flex;
  flex-direction: column;
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

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
}

button {
  padding: 0.8rem 1.2rem;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
}

.btn-secondary:hover {
  background-color: #565e64;
}
</style>
