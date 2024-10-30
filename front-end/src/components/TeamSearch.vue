<template>
  <div>
    <h2>팀 검색</h2>
    <input v-model="searchQuery" placeholder="운동 팀 검색" />
    <button @click="searchTeam">검색</button>
    <ul>
      <li v-for="team in teams" :key="team.id">
        {{ team.name }} - {{ team.sport }}
        <button @click="viewTeam(team.id)">상세 보기</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: "",
      teams: [
        { id: 1, name: "풋살팀", sport: "풋살" },
        { id: 2, name: "농구팀", sport: "농구" }
      ]
    };
  },
  methods: {
    searchTeam() {
      // 검색 기능 구현
      console.log("검색어:", this.searchQuery);
    },
    viewTeam(teamId) {
      this.$router.push({ name: 'TeamDetail', params: { id: teamId } });
    }
  }
};
</script>

<style scoped>
/* 스타일링 추가 */
</style>
