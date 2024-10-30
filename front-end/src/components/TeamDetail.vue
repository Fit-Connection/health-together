<template>
  <div>
    <h2>{{ team.name }}</h2>
    <p>운동 종류: {{ team.sport }}</p>
    <ul>
      <li v-for="member in team.members" :key="member.id">{{ member.name }}</li>
    </ul>
    <button @click="joinTeam">팀 참여하기</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      team: {
        id: 1,
        name: "풋살팀",
        sport: "풋살",
        members: [{ id: 1, name: "유저1" }, { id: 2, name: "유저2" }]
      }
    };
  },
  methods: {
    joinTeam() {
      this.$router.push({ name: 'JoinTeam', params: { id: this.team.id } });
    }
  }
};
</script>
