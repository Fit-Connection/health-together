<!-- src/views/EmailView.vue -->
<template>
  <div class="email-view">
    <h1>이메일 인증 페이지</h1>
    <!-- 이메일 인증 컴포넌트를 포함 -->
    <EmailVerification />
  </div>
</template>

<script>
import EmailVerification from "@/components/EmailVerification.vue"; // 컴포넌트 경로에 따라 수정

export default {
  components: {
    EmailVerification, // 컴포넌트를 뷰에 등록
  },
};
</script>
