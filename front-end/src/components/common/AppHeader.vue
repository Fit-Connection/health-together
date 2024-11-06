<!-- src/components/common/AppHeader.vue -->
<template>
  <header class="header d-flex align-items-center justify-content-between mb-4">
    <select v-model="selectedLocation" class="form-select w-auto">
      <option>역삼1동</option>
      <option>강남구</option>
      <option>서초구</option>
    </select>
    <div class="icons d-flex gap-3">
      <button v-for="icon in icons" :key="icon.name" class="btn btn-light d-flex flex-column align-items-center">
        <img :src="icon.img" :alt="icon.name" class="icon-img" />
        <span class="small">{{ icon.name }}</span>
      </button>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      selectedLocation: "역삼1동",
      icons: [
        { name: "야구", img: "https://img.icons8.com/ios/250/000000/baseball.png" },
        { name: "축구", img: "https://img.icons8.com/ios/250/000000/football2.png" },
        { name: "농구", img: "https://img.icons8.com/ios/250/000000/basketball.png" },
        { name: "골프", img: "https://img.icons8.com/ios/250/000000/golf-ball.png" },
        { name: "러닝", img: "https://img.icons8.com/ios/250/000000/sports-mode.png" },
      ],
    };
  },
};
</script>

<style scoped>
.header {
  background-color: #f8f9fa;
  padding: 10px;
  border-radius: 5px;
}

.icon-img {
  width: 24px;
  height: 24px;
}
</style>
