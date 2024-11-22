<template>
  <div v-if="isVisible" class="modal-container">
    <div class="modal-content">
      <!-- 왼쪽: 검색 필드 및 결과 리스트 -->
      <div class="search-panel">
        <input
            v-model="keyword"
            type="text"
            class="form-control mb-3"
            placeholder="검색어 입력 (예: 서울 강남구 농구장)"
        />
        <ul class="list-group search-results">
          <li
              v-for="(result, index) in results"
              :key="index"
              class="list-group-item"
              @click="selectPlace(result)"
          >
            {{ result.place_name }} ({{ result.address_name }})
          </li>
        </ul>
      </div>

      <!-- 오른쪽: 지도 -->
      <div class="map-panel">
        <div id="mapContainer" style="width: 100%; height: 400px;"></div>
      </div>
    </div>

    <!-- 모달 닫기 버튼 -->
    <button class="btn btn-secondary mt-3" @click="closeModal">닫기</button>
  </div>
</template>

<script>
import { ref, watch } from "vue";
import { loadKakaoMaps } from "@/utils/kakao";

export default {
  props: {
    isVisible: Boolean, // 모달 표시 여부
  },
  emits: ["update:isVisible", "placeSelected"],
  setup(props, { emit }) {
    const keyword = ref(""); // 검색어
    const results = ref([]); // 검색 결과 리스트
    const map = ref(null); // Kakao Map 객체
    const markers = ref([]); // 마커 리스트

    const initializeMap = async () => {
      const kakao = await loadKakaoMaps();
      const container = document.getElementById("mapContainer");

      if (!container) {
        console.error("Map container not found!");
        return;
      }

      map.value = new kakao.maps.Map(container, {
        center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 중심
        level: 3,
      });
    };

    const searchPlaces = async () => {
      if (!keyword.value) {
        results.value = [];
        return;
      }

      const kakao = await loadKakaoMaps();
      const ps = new kakao.maps.services.Places();

      ps.keywordSearch(keyword.value, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
          results.value = data;
          displayMarkers(data); // 검색 결과를 지도에 마커로 표시
        } else {
          results.value = [];
        }
      });
    };

    const displayMarkers = (places) => {
      const kakao = window.kakao;

      if (!map.value) {
        console.error("Map is not initialized!");
        return;
      }

      markers.value.forEach((marker) => marker.setMap(null));
      markers.value = [];

      places.forEach((place) => {
        const coords = new kakao.maps.LatLng(place.y, place.x);
        const marker = new kakao.maps.Marker({
          position: coords,
          map: map.value,
        });

        markers.value.push(marker);

        kakao.maps.event.addListener(marker, "click", () => {
          selectPlace(place);
        });
      });

      if (places.length > 0) {
        const bounds = new kakao.maps.LatLngBounds();
        places.forEach((place) => bounds.extend(new kakao.maps.LatLng(place.y, place.x)));
        map.value.setBounds(bounds);
      }
    };

    const selectPlace = (place) => {
      emit("placeSelected", {
        name: place.place_name,
        latitude: parseFloat(place.y),
        longitude: parseFloat(place.x),
      });
      closeModal();
    };

    const closeModal = () => {
      emit("update:isVisible", false);
    };

    watch(keyword, searchPlaces);

    watch(
        () => props.isVisible,
        async (newValue) => {
          if (newValue) {
            await initializeMap();
          }
        }
    );

    return {
      keyword,
      results,
      selectPlace,
      closeModal,
    };
  },
};
</script>
