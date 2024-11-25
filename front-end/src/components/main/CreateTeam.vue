<template>
  <CreateHeader />
  <div>
    <form @submit.prevent="submitForm">
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
          <option value="배드민턴">배드민턴</option>
          <option value="축구">축구</option>
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
      <div v-if="team.location" class="mb-3">
        <p>선택된 시설: {{ team.location }}</p>
        <p>위도: {{ team.latitude }}, 경도: {{ team.longitude }}</p>
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
      <button type="submit" class="btn btn-primary">모임 작성</button>
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
import { reactive, ref } from "vue";
import axios from "axios";
import MapSearchModal from "@/components/map/MapSearchModal.vue";
import CreateHeader from "@/components/common/header/CreateHeader.vue";

export default {
  components: { CreateHeader, MapSearchModal },
  setup() {
    const team = reactive({
      teamName: "",
      location: "",
      latitude: null,
      longitude: null,
      sportType: "",
      description: "",
      fee: "무료",
      maxMembers: 10,
      preparing: "",
      rule: "",
      invitations: "",
    });

    const isMapModalVisible = ref(false);
    const message = ref("");
    const success = ref(false);

    // 지도 검색 모달 열기
    const openMapModal = () => {
      isMapModalVisible.value = true;
    };

    // 시설 선택 처리
    const handlePlaceSelected = (place) => {
      team.location = place.name;
      team.latitude = place.latitude;
      team.longitude = place.longitude;
    };

    // 팀 생성 요청
    const submitForm = async () => {
      try {
        const payload = {
          teamName: team.teamName,
          location: team.location,
          latitude: team.latitude,
          longitude: team.longitude,
          sportType: team.sportType,
          description: team.description,
          fee: team.fee,
          maxMembers: team.maxMembers,
          preparing: team.preparing,
          rule: team.rule,
          invitations: team.invitations,
        };

        // 서버로 데이터 전송
        const response = await axios.post("/api/teams", payload);

        // 성공 처리
        if (response.status === 201) {
          message.value = "모임이 성공적으로 생성되었습니다!";
          success.value = true;
        }
      } catch (error) {
        // 실패 처리
        message.value = error.response?.data?.message || "모임 생성에 실패했습니다.";
        success.value = false;
      }
    };

    return {
      team,
      isMapModalVisible,
      openMapModal,
      handlePlaceSelected,
      submitForm,
      message,
      success,
    };
  },
};
</script>

<style>
.alert {
  margin-top: 20px;
  padding: 15px;
  border-radius: 5px;
}

.alert-success {
  background-color: #d4edda;
  color: #155724;
}

.alert-danger {
  background-color: #f8d7da;
  color: #721c24;
}
</style>
