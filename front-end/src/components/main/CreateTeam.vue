<!-- src/views/CreateTeam.vue -->
<template>
  <div class="create-team container my-4">
    <h2>모임 작성하기</h2>
    <form @submit.prevent="submitForm">
      <div class="mb-3">
        <label for="teamName" class="form-label">팀 이름</label>
        <input v-model="team.teamName" type="text" class="form-control" id="teamName" required />
      </div>
      <div class="mb-3">
        <label for="description" class="form-label">팀 설명</label>
        <textarea v-model="team.description" class="form-control" id="description" rows="3" required></textarea>
      </div>
      <div class="mb-3">
        <label for="sportType" class="form-label">운동 종류</label>
        <input v-model="team.sportType" type="text" class="form-control" id="sportType" required />
      </div>
      <div class="mb-3">
        <label for="maxMembers" class="form-label">최대 멤버 수</label>
        <input v-model.number="team.maxMembers" type="number" class="form-control" id="maxMembers" min="1" required />
      </div>
      <div class="mb-3">
        <label for="ageLimit" class="form-label">연령 제한</label>
        <input v-model.number="team.ageLimit" type="number" class="form-control" id="ageLimit" min="0" />
      </div>
      <div class="mb-3">
        <label for="location" class="form-label">위치</label>
        <input v-model="team.location" type="text" class="form-control" id="location" required />
      </div>
      <div class="mb-3">
        <label for="meetingDate" class="form-label">모임 날짜</label>
        <input v-model="team.meetingDate" type="date" class="form-control" id="meetingDate" required />
      </div>
      <div class="mb-3">
        <label for="mapImageUrl" class="form-label">지도 이미지 URL</label>
        <input v-model="team.mapImageUrl" type="url" class="form-control" id="mapImageUrl" />
      </div>
      <button type="submit" class="btn btn-primary">모임 작성</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      team: {
        teamName: "",
        description: "",
        sportType: "",
        maxMembers: null,
        ageLimit: null,
        location: "",
        meetingDate: "",
        mapImageUrl: "",
      },
    };
  },
  methods: {
    submitForm() {
      // 작성된 팀 정보 제출
      console.log("모임 작성 내용:", this.team);
      alert("모임이 작성되었습니다!");
      // 폼 초기화
      this.team = {
        teamName: "",
        description: "",
        sportType: "",
        maxMembers: null,
        ageLimit: null,
        location: "",
        meetingDate: "",
        mapImageUrl: "",
      };
      this.$router.push({ name: "TeamDetail", params: { id: "new-team-id" } }); // 새로 생성된 모임 상세 페이지로 이동 (임시 id 사용)
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 600px;
}
</style>
