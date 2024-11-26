<template>
  <CreateHeader />
  <div>
    <form @submit.prevent="submitForm">
      <!-- 팀 이미지 업로드 -->
      <div class="mb-4 text-center">
        <img
            :src="teamImage || defaultImage"
            class="team-img"
            alt="Team Image"
            @click="selectImage"
        />
        <input
            type="file"
            ref="fileInput"
            @change="uploadImage"
            style="display: none"
            accept="image/*"
        />
        <p class="text-muted">팀 이미지를 클릭하여 업로드하세요</p>
      </div>

      <!-- 팀 이름 -->
      <div class="mb-3">
        <label for="teamName" class="form-label">팀 이름</label>
        <input
            v-model="team.teamName"
            type="text"
            class="form-control"
            id="teamName"
            placeholder="팀 이름을 입력하세요"
            required
        />
      </div>

      <!-- 스포츠 유형 -->
      <div class="mb-3">
        <label for="sportType" class="form-label">스포츠 유형</label>
        <select v-model="team.sportType" class="form-select" id="sportType" required>
          <option disabled value="">스포츠 유형을 선택하세요</option>
          <option value="풋살">풋살</option>
          <option value="농구">농구</option>
          <option value="야구">야구</option>
          <option value="런닝">런닝</option>
        </select>
      </div>

      <!-- 설명 -->
      <div class="mb-3">
        <label for="description" class="form-label">설명</label>
        <textarea
            v-model="team.description"
            class="form-control"
            id="description"
            placeholder="모임에 대한 설명을 작성하세요"
        ></textarea>
      </div>

      <!-- 모임 날짜와 시간 -->
      <div class="mb-3">
        <label for="meetingDate" class="form-label">모임 날짜와 시간</label>
        <input
            v-model="team.meetingDate"
            type="datetime-local"
            class="form-control datetime-input"
            id="meetingDate"
            required
        />
      </div>

      <!-- 시설 검색 버튼 -->
      <button type="button" class="btn btn-secondary mb-3" @click="openMapModal">
        시설 검색
      </button>

      <!-- 지도 검색 모달 -->
      <MapSearchModal
          :isVisible="isMapModalVisible"
          @update:isVisible="isMapModalVisible = $event"
          @placeSelected="handlePlaceSelected"
      />

      <!-- 선택된 장소 -->
      <div v-if="team.facilityName" class="mb-3">
        <p>선택된 시설: {{ team.facilityName }}</p>
      </div>

      <!-- 최대 인원 -->
      <div class="mb-3">
        <label for="maxMembers" class="form-label">최대 인원</label>
        <input
            v-model="team.maxMembers"
            type="number"
            class="form-control"
            id="maxMembers"
            placeholder="최대 인원을 입력하세요"
            required
            min="1"
        />
      </div>

      <!-- 비용 -->
      <div class="mb-3">
        <label for="fee" class="form-label">비용</label>
        <input
            v-model="team.fee"
            type="text"
            class="form-control"
            id="fee"
            placeholder="무료 또는 금액 입력"
        />
      </div>

      <!-- 준비물 -->
      <div class="mb-3">
        <label for="preparing" class="form-label">준비물</label>
        <input
            v-model="team.preparing"
            type="text"
            class="form-control"
            id="preparing"
            placeholder="운동복, 장비 등"
        />
      </div>

      <!-- 팀 규칙 -->
      <div class="mb-3">
        <label for="rule" class="form-label">팀 규칙</label>
        <textarea
            v-model="team.rule"
            class="form-control"
            id="rule"
            placeholder="예의 준수, 시간 엄수 등"
        ></textarea>
      </div>

      <!-- 초대 메시지 -->
      <div class="mb-3">
        <label for="invitations" class="form-label">초대 메시지</label>
        <textarea
            v-model="team.invitations"
            class="form-control"
            id="invitations"
            placeholder="초대 메시지를 입력하세요"
        ></textarea>
      </div>

      <!-- 작성 버튼 -->
      <!-- 작성 버튼 -->
      <button type="submit" class="btn btn-primary">
        {{ isEditMode ? "수정하기" : "모집하기" }}
      </button>
    </form>

    <!-- 상태 메시지 -->
    <div
        v-if="message"
        class="alert"
        :class="{ 'alert-success': success, 'alert-danger': !success }"
    >
      {{ message }}
    </div>
  </div>
</template>

<script>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import MapSearchModal from "@/components/map/MapSearchModal.vue";
import CreateHeader from "@/components/common/header/CreateHeader.vue";
import api from "@/api";
import { useRouter } from "vue-router";

export default {
  components: { CreateHeader, MapSearchModal },
  setup() {
    const router = useRouter();

    const team = reactive({
      teamName: "",
      facilityLocation: "", // 시설 상세 주소
      facilityName: "", // 시설 이름
      latitude: null, // 위도
      longitude: null, // 경도
      sportType: "",
      description: "",
      meetingDate: null,
      fee: "무료",
      maxMembers: 10,
      preparing: "",
      rule: "",
      invitations: "",
    });

    const teamImage = ref(null);
    const defaultImage = require("@/assets/football.jpg");
    const isMapModalVisible = ref(false);

    // 이미지 선택 트리거
    const selectImage = () => {
      document.querySelector("input[type='file']").click();
    };

    // 지도 검색 모달 열기
    const openMapModal = () => {
      isMapModalVisible.value = true;
    };

    // 이미지 업로드
    const uploadImage = async (event) => {
      const file = event.target.files[0];
      if (!file) return;

      const formData = new FormData();
      formData.append("file", file);

      try {
        const response = await axios.post("http://localhost:9090/s3/resource", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        teamImage.value = response.data.path; // 이미지 URL
      } catch (error) {
        alert("이미지 업로드 실패");
      }
    };

    // 시설 선택 처리
    const handlePlaceSelected = (place) => {
      team.facilityName = place.name;
      team.facilityLocation = place.address;
      team.latitude = place.latitude; // 위도
      team.longitude = place.longitude; // 경도
    };

    // 생성 또는 수정
    const submitForm = async () => {
      try {
        const payload = {
          ...team,
          teamImageUrl: teamImage.value || null,
          writerId: localStorage.getItem("userId"),
        };

        let response;
        if (isEditMode.value) {
          const teamId = router.currentRoute.value.params.id;
          response = await api.put(`/teams/${teamId}`, payload, {
            headers: { "Content-Type": "application/json" },
          });
          console.log("수정 응답 상태:", response.status); // 디버깅
          if (response.status === 200) {
            alert("모임이 성공적으로 수정되었습니다!");
            router.push({ name: "TeamDetail", params: { id: teamId } });
          }
        } else {
          response = await api.post("/teams", payload, {
            headers: { "Content-Type": "application/json" },
          });
          console.log("생성 응답 상태:", response.status); // 디버깅
          if (response.status === 201) {
            alert("모임이 성공적으로 생성되었습니다!");
            router.push({ name: "TeamDetail", params: { id: response.data.teamId } });
          }
        }
      } catch (error) {
        console.error("에러 발생:", error); // 디버깅
        alert(error.response?.data?.message || "모임 저장 실패");
      }
    };



    const isEditMode = ref(false);

    onMounted(() => {
      const storedTeamData = localStorage.getItem("editTeamData");
      if (storedTeamData) {
        const teamData = JSON.parse(storedTeamData);
        Object.assign(team, teamData);
        teamImage.value = teamData.teamImageUrl;
        isEditMode.value = true; // 수정 모드 활성화
        localStorage.removeItem("editTeamData");
      }
    });


    return {
      team,
      teamImage,
      defaultImage,
      isMapModalVisible,
      selectImage,
      uploadImage,
      handlePlaceSelected,
      openMapModal,
      submitForm,
      isEditMode,
    };
  },
};
</script>


<style scoped>
/* 전체 폼 컨테이너 */
.form-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* 팀 이미지 */
.team-img {
  width: 100%;
  max-width: 200px;
  max-height: 300px;
  margin: 0 auto;
  border-radius: 8px;
  border: 2px dashed #ccc;
  transition: all 0.3s ease-in-out;
  cursor: pointer;
}

.team-img:hover {
  border-color: #007bff;
  transform: scale(1.05);
}

/* 날짜 및 시간 입력 필드 */
.datetime-input {
  border: 2px solid #ccc;
  border-radius: 8px;
  padding: 10px;
  font-size: 1rem;
  transition: border-color 0.3s ease-in-out;
}

.datetime-input:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

/* Alert 메시지 */
.alert {
  margin-top: 20px;
  padding: 15px;
  border-radius: 8px;
  font-size: 0.9rem;
}

.alert-success {
  background-color: #e9f7ef;
  color: #27ae60;
}

.alert-danger {
  background-color: #fdecea;
  color: #e74c3c;
}

/* 공통 스타일 */
.mb-3 {
  margin-bottom: 1.5rem;
}
</style>