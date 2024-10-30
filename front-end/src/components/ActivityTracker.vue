<template>
  <div>
    <h2>활동 지수</h2>
    <p>현재 지수: {{ activityScore }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activityScore: 75 // 예시 지수
    };
  }
};
</script>
