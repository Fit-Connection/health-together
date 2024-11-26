<template>
  <AppHeader :isMyPage="true" />
  <div>
    <!-- 프로필 섹션 -->
    <div class="profile-container">
      <img :src="profileImage || defaultImage" class="profile-img" alt="HealthTogether Logo" @click="selectImage" />
      <input type="file" ref="fileInput" @change="uploadImage" style="display: none" accept="image/*" />

      <div class="profile-info">
        <!-- 이름 -->
        <div @click="editField('name')">
          <h3 v-if="!editing.name">{{ name || '이름을 입력하세요' }}</h3>
          <input
              v-else
              v-model="name"
              placeholder="이름을 입력하세요"
              @blur="saveField('name')"
              @keyup.enter="saveField('name')"
          />
        </div>

        <!-- 지역 -->
        <div @click="editField('location')">
          <p v-if="!editing.location">{{ location || '지역을 선택하세요' }}</p>
          <select v-else v-model="location" class="form-select w-auto" @blur="saveField('location')">
            <option disabled value="">지역을 선택하세요</option>
            <option>역삼1동</option>
            <option>강남구</option>
            <option>서초구</option>
          </select>
        </div>

        <!-- 선호 종목 (단일 항목) -->
        <div @click="editField('sports')">
          <p v-if="!editing.sports">{{ sports || '선호 종목을 입력하세요' }}</p>
          <input
              v-else
              v-model="sports"
              placeholder="선호 종목을 입력하세요"
              @blur="saveField('sports')"
              @keyup.enter="saveField('sports')"
          />
        </div>
      </div>
    </div>
    <hr />
    <!-- 메뉴 링크 -->
    <div class="group-text-size">
      <RouterLink to="/myPage/wishGroup">찜 모임</RouterLink> |
      <RouterLink :to="`/myPage/myGroup/${userId}`">내 모임</RouterLink> |
      <RouterLink to="/myPage/pastGroup">과거 모임</RouterLink>
    </div>
    <hr />
  </div>
  <router-view />
  <AppFooter />
</template>

<script>
import AppHeader from "@/components/common/header/AppHeader.vue";
import AppFooter from "@/components/common/footer/AppFooter.vue";
import axios from "axios"; // Axios 설정 파일을 통해 API 호출

export default {
  components: { AppHeader, AppFooter },
  data() {
    return {
      // 수정된 부분: userId를 로컬 스토리지에서 가져오기
      userId: localStorage.getItem("userId") || null, // 로컬 스토리지에서 user_id를 가져옴
      profileImage: null, // 업로드된 프로필 이미지 저장
      defaultImage: require('@/assets/Weblogo.png'), // 기본 프로필 이미지

      name: "",
      location: "",
      sports: "",

      // 수정 모드 상태
      editing: {
        name: false,
        location: false,
        sports: false,
      },
    };
  },
  methods: {
    // 프로필 정보 로드
    async loadProfile() {
      if (!this.userId) {
        alert("로그인이 필요합니다."); // 수정된 부분: userId가 없으면 로그인 요구
        this.$router.push("/login");
        return;
      }

      try {
        const userId = localStorage.getItem("userId"); // 로컬 스토리지에서 user_id 가져오기
        if (userId) {
          console.log(`Logged-in user ID: ${userId}`); // user_id가 있으면 출력
        } else {
          console.log("No user ID found. User is not logged in."); // user_id가 없으면 메시지 출력
        }

        const response = await axios.get(`http://localhost:9090/api/profiles/${this.userId}`); // userId를 사용하여 프로필 로드
        const profile = response.data;

        this.name = profile.name;
        this.location = profile.location;
        this.sports = profile.sports;
        this.profileImage = profile.profileImage || this.defaultImage;
        console.log(profile.profileImage);
      } catch (error) {
        console.error("Failed to load profile:", error);
      }
    },

    // 프로필 저장
    async saveProfile() {
      if (!this.userId) {
        alert("로그인이 필요합니다.");
        this.$router.push("/login");
        return;
      }

      try {
        const profileData = {
          name: this.name,
          location: this.location,
          sports: this.sports,
          profileImage: this.profileImage, // AWS S3 URL
        };
        await axios.post(`http://localhost:9090/api/profiles/create/${this.userId}`, profileData);
        alert("프로필이 저장되었습니다.");
      } catch (error) {
        console.error("Failed to save profile:", error);
      }
    },


    // 이미지 선택 트리거
    selectImage() {
      this.$refs.fileInput.click();
    },

    // 이미지 업로드
    uploadImage(event) {
      const file = event.target.files[0];
      if (file) {
        const formData = new FormData();
        formData.append("file", file);

        axios.post("http://localhost:9090/s3/resource", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
            .then(response => {
              this.profileImage = response.data.path; // S3에서 반환된 URL
              this.saveProfile(); // 업로드 후 프로필 저장
            })
            .catch(error => {
              console.error("Failed to upload image:", error);
            });
      }
    },


    // 필드 수정 모드 시작
    editField(field) {
      this.editing[field] = true;
    },

    // 필드 저장 후 수정 모드 종료
    saveField(field) {
      this.editing[field] = false;
      this.saveProfile(); // 필드 저장 시 프로필 저장
    },
  },
  mounted() {
    // userId를 확인하고 프로필 로드
    if (this.userId) {
      this.loadProfile(); // 컴포넌트 마운트 시 프로필 로드
    } else {
      alert("로그인이 필요합니다."); // userId가 없으면 로그인 페이지로 리다이렉트
      this.$router.push("/login");
    }
  },
};
</script>

<style scoped>
.profile-info div {
  cursor: pointer;
}

.group-text-size {
  font-size: 1.3em;
}

.profile-container {
  display: flex;
  align-items: center;
  width: 100%;
  margin-top: 10%;
  margin-bottom: 10%;
}

.profile-img {
  width: 35%;
  max-width: 200px;
  margin-left: 10%;
  cursor: pointer; /* 클릭할 수 있음을 시각적으로 표시 */
}

.profile-info {
  width: 65%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 10%;
}

.profile-info h4,
.profile-info p {
  margin: 0;
  text-align: center;
}
</style>
