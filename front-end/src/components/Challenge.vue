<template>
  <div>
    <h2>챌린지</h2>
    <ul>
      <li v-for="challenge in challenges" :key="challenge.id">
        {{ challenge.title }} - 목표: {{ challenge.goal }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      challenges: [
        { id: 1, title: "주 1회 운동", goal: "주 1회 달성" },
        { id: 2, title: "다양한 운동 시도", goal: "다양한 운동 경험" }
      ]
    };
  }
};
</script>
