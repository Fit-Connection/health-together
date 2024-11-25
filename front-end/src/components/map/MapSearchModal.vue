<template>
  <div v-if="isVisible" class="modal-container">
    <div class="modal-content">
      <!-- 검색 창 -->
      <div v-if="viewMode === 'search'" class="search-panel">
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
              @click="selectSearchResult(result)"
          >
            {{ result.place_name }} ({{ result.address_name }})
          </li>
        </ul>
      </div>

      <!-- 지도 보기 -->
      <div v-if="viewMode === 'map'" class="map-panel">
        <button class="btn btn-secondary mb-3" @click="switchToSearch">
          ← 검색으로 돌아가기
        </button>
        <div id="mapContainer" style="width: 100%; height: 400px;"></div>
      </div>

      <!-- 닫기 버튼 (X) -->
      <div class="close-button" @click="closeModal">✖</div>
    </div>
  </div>
</template>

<script>
import { ref, watch, nextTick } from "vue";
import { loadKakaoMaps } from "@/utils/kakao";

export default {
  props: {
    isVisible: Boolean,
  },
  emits: ["update:isVisible", "placeSelected"],
  setup(props, { emit }) {
    const keyword = ref("");
    const results = ref([]);
    const map = ref(null);
    const markers = ref([]);
    const selectedPlace = ref(null);
    const viewMode = ref("search");

    let infoWindow = null;
    let activeMarker = null;

    const initializeMap = async () => {
      await nextTick();
      const container = document.getElementById("mapContainer");

      if (!container) {
        console.error("Map container not found!");
        return;
      }

      const kakao = await loadKakaoMaps();
      map.value = new kakao.maps.Map(container, {
        center: new kakao.maps.LatLng(37.5665, 126.9780),
        level: 3,
      });
    };

    const resetMarkers = () => {
      markers.value.forEach((marker) => marker.setMap(null));
      markers.value = [];
      if (infoWindow) {
        infoWindow.close();
        infoWindow = null;
      }
      activeMarker = null;
    };

    const searchPlaces = async () => {
      if (!keyword.value) {
        results.value = [];
        resetMarkers();
        return;
      }

      const kakao = await loadKakaoMaps();
      const ps = new kakao.maps.services.Places();

      ps.keywordSearch(keyword.value, (data, status) => {
        results.value = status === kakao.maps.services.Status.OK ? data : [];
        resetMarkers();
      });
    };

    const createInfoWindowContent = (place) => `
      <div style="text-align:center;">
        <div style="
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          padding: 10px;
          font-weight:bold;
          text-align:center;
          cursor:pointer;
          gap: 5px;
        ">
          <a style="
            font-weight: bold;
            text-decoration: underline;
            cursor: pointer;
          "
          onclick="window.open('${place.place_url}', '_blank')">${place.place_name}</a>
          <div style="font-size:12px; color:gray;">${place.address_name}</div>
          <button style="
            margin-top:5px;
            padding:5px 10px;
            background-color:#4CAF50;
            color:white;
            border:none;
            border-radius:4px;
            cursor:pointer;"
            onclick="window.confirmSelection()">
            확인
          </button>
        </div>
      </div>`;

    const displayMarkers = (places) => {
      if (!map.value) {
        console.error("Map is not initialized!");
        return;
      }

      const kakao = window.kakao;

      resetMarkers();

      const normalImage = new kakao.maps.MarkerImage(
          "https://img.icons8.com/color/48/blue/marker.png",
          new kakao.maps.Size(30, 35)
      );
      const selectedImage = new kakao.maps.MarkerImage(
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
          new kakao.maps.Size(40, 50)
      );

      places.forEach((place) => {
        const coords = new kakao.maps.LatLng(place.y, place.x);

        const marker = new kakao.maps.Marker({
          position: coords,
          map: map.value,
          image: normalImage,
        });

        kakao.maps.event.addListener(marker, "click", () => {
          if (activeMarker === marker) {
            infoWindow?.close();
            marker.setImage(normalImage);
            activeMarker = null;
            selectedPlace.value = null;
          } else {
            infoWindow?.close();
            activeMarker?.setImage(normalImage);

            const newInfoWindow = new kakao.maps.InfoWindow({
              content: createInfoWindowContent(place),
              zIndex: 1,
            });

            selectedPlace.value = {
              id: place.id,
              name: place.place_name,
              address: place.address_name,
              latitude: parseFloat(place.y),
              longitude: parseFloat(place.x),
            };

            marker.setImage(selectedImage);
            activeMarker = marker;
            newInfoWindow.open(map.value, marker);
            infoWindow = newInfoWindow;
          }
        });

        markers.value.push(marker);
      });

      if (places.length > 0) {
        const bounds = new kakao.maps.LatLngBounds();
        places.forEach((place) => bounds.extend(new kakao.maps.LatLng(place.y, place.x)));
        map.value.setBounds(bounds);
      }

      window.confirmSelection = confirmSelection;
    };

    const selectSearchResult = async (place) => {
      viewMode.value = "map";
      await initializeMap();
      selectedPlace.value = {
        id: place.id,
        name: place.place_name,
        address: place.address_name,
        latitude: parseFloat(place.y),
        longitude: parseFloat(place.x),
      };
      displayMarkers(results.value);
    };

    const confirmSelection = () => {
      if (selectedPlace.value) {
        emit("placeSelected", selectedPlace.value);
        closeModal();
      }
    };

    const switchToSearch = () => {
      viewMode.value = "search";
      selectedPlace.value = null;
    };

    const closeModal = () => {
      emit("update:isVisible", false);
      resetMarkers();
    };

    watch(keyword, searchPlaces);

    watch(
        () => props.isVisible,
        async (newValue) => {
          if (newValue) {
            viewMode.value = "search";
            selectedPlace.value = null;
          }
        }
    );

    return {
      keyword,
      results,
      selectedPlace,
      closeModal,
      selectSearchResult,
      switchToSearch,
      confirmSelection,
      viewMode,
    };
  },
};
</script>

<style>
.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 24px;
  color: #333;
  cursor: pointer;
}

.close-button:hover {
  color: #ff0000;
}
</style>
