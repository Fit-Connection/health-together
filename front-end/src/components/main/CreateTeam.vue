<template>
  <CreateHeader/>
  <div class="create-team container my-4">
    <h2 v-if="isEditMode">모임 수정하기</h2>
    <h2 v-else>모임 작성하기</h2>

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
      <!-- 날짜 및 시간 입력 필드 추가 -->
      <div class="mb-3">
        <label for="meetingDate" class="form-label">모임 날짜</label>
        <input v-model="meetingDate" type="date" class="form-control" id="meetingDate" required />
      </div>
      <div class="mb-3">
        <label for="meetingTime" class="form-label">모임 시간</label>
        <input v-model="meetingTime" type="time" class="form-control" id="meetingTime" required />
      </div>
      <div class="mb-3">
        <label for="mapImageUrl" class="form-label">지도 이미지 URL</label>
        <input v-model="team.mapImageUrl" type="url" class="form-control" id="mapImageUrl" />
      </div>
      <button type="submit" class="btn btn-primary">{{ isEditMode ? "수정 완료" : "모임 작성" }}</button>
    </form>
  </div>
</template>

<script>
import { reactive, onMounted, computed, ref} from "vue";
import { useRouter, useRoute } from "vue-router";
import api from "@/api";
import CreateHeader from "@/components/common/CreateHeader.vue";

export default {
  components: {CreateHeader},
  setup() {
    const router = useRouter();
    const route = useRoute();

    // 팀 데이터 상태 설정
    const team = reactive({
      teamId: null,  // teamId 추가
      teamName: "",
      description: "",
      sportType: "",
      maxMembers: null,
      ageLimit: null,
      location: "",
      mapImageUrl: "",
    });

    // 날짜 및 시간 입력 값을 위한 ref 변수
    const meetingDate = ref(""); // YYYY-MM-DD 형식으로 입력받는 변수
    const meetingTime = ref(""); // HH:MM 형식으로 입력받는 변수

    // 수정 모드인지 여부 확인
    const isEditMode = computed(() => !!route.params.id);

    // 팀 데이터 가져오기 (수정 모드일 경우)
    const fetchTeamData = async () => {
      if (isEditMode.value) {
        try {
          const response = await api.get(`/teams/${route.params.id}`);
          Object.assign(team, response.data);
          if (team.meetingDate) {
            // 기존 날짜와 시간을 분리하여 각각 할당
            const [date, time] = team.meetingDate.split("T");
            meetingDate.value = date;
            meetingTime.value = time.slice(0, 5); // HH:MM 형식으로 변환
          }
        } catch (error) {
          console.error("팀 정보를 불러오는 중 오류 발생:", error);
          alert("팀 정보를 불러오지 못했습니다.");
        }
      }
    };

    // 폼 제출 핸들러
    const submitForm = async () => {
      try {
        // 날짜와 시간을 합쳐 LocalDateTime 형식으로 변환
        const formattedDateTime = meetingDate.value && meetingTime.value
            ? `${meetingDate.value}T${meetingTime.value}:00`
            : null;

        const teamData = { ...team, meetingDate: formattedDateTime };

        if (isEditMode.value) {
          await api.put(`/teams/${route.params.id}`, teamData);
          alert("모임이 수정되었습니다!");
        } else {
          localStorage.setItem("userId", 4);
          const userId = localStorage.getItem("userId");
          teamData.userId = parseInt(userId);
          const response = await api.post("/teams", teamData);
          alert("모임이 작성되었습니다!");
          router.push({ name: "TeamDetail", params: { id: response.data.teamId } });
          return;
        }
        router.push({ name: "TeamSearch" });
      } catch (error) {
        console.error(isEditMode.value ? "모임 수정 중 오류 발생:" : "모임 생성 중 오류 발생:", error);
        alert(isEditMode.value ? "모임 수정에 실패했습니다." : "모임 생성에 실패했습니다.");
      }
    };

    onMounted(fetchTeamData);

    return {
      team,
      meetingDate,
      meetingTime,
      submitForm,
      isEditMode,
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 600px;
}
</style>
