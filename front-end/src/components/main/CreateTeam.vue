<template>
  <CreateHeader/>
  <div>
    <form @submit.prevent="submitForm">
      <!-- 팀 이름 -->
      <div class="mb-3">
        <label for="teamName" class="form-label">팀 이름</label>
        <input v-model="team.teamName" type="text" class="form-control" id="teamName" required />
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
      </div>

      <button type="submit" class="btn btn-primary">모임 작성</button>
    </form>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import MapSearchModal from "@/components/map/MapSearchModal.vue";
import CreateHeader from "@/components/common/CreateHeader.vue";

export default {
  components: {CreateHeader, MapSearchModal },
  setup() {
    const team = reactive({
      teamName: "",
      location: "",
      latitude: null,
      longitude: null,
    });
    const isMapModalVisible = ref(false);

    const openMapModal = () => {
      isMapModalVisible.value = true;
    };

    const handlePlaceSelected = (place) => {
      team.location = place.name;
      team.latitude = place.latitude;
      team.longitude = place.longitude;
    };

    return {
      team,
      isMapModalVisible,
      openMapModal,
      handlePlaceSelected,
    };
  },
};
</script>
