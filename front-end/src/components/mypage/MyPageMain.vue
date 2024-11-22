<template>
  <AppHeader :isMyPage="true" />
  <div>
    <!-- 프로필 섹션 -->
    <div class="profile-container">
      <img
          :src="profileImage || defaultImage"
          class="profile-img"
          alt="HealthTogether Logo"
          @click="isEditing ? selectImage() : null"
          :class="{ 'editable': isEditing }"
      />
      <input
          type="file"
          ref="fileInput"
          @change="uploadImage"
          style="display: none"
          accept="image/*"
      />

      <div class="profile-info">
        <!-- 닉네임 -->
        <div>
          <h3 v-if="!isEditing">{{ name }}</h3>
          <input
              v-else
              v-model="name"
              placeholder="이름을 입력하세요"
          />
        </div>
        <!-- 지역 -->
        <div>
          <p v-if="!isEditing">{{ location || '지역을 선택하세요' }}</p>
          <select v-else v-model="location" class="form-select w-auto">
            <option disabled value="">지역을 선택하세요</option>
            <option>역삼1동</option>
            <option>강남구</option>
            <option>서초구</option>
          </select>
        </div>

        <!-- 선호 종목 (단일 항목) -->
        <div>
          <p v-if="!isEditing">{{ sports || '선호 종목을 입력하세요' }}</p>
          <input
              v-else
              v-model="sports"
              placeholder="선호 종목을 입력하세요"
          />
        </div>
      </div>
    </div>
    <!-- 자기소개 -->
    <div class="introduce-container">
      <p v-if="!isEditing" class="introduce-text">{{ introduce || '자기 소개를 입력하세요' }}</p>
      <textarea
          v-else
          v-model="introduce"
          placeholder="자기소개를 입력하세요"
          class="introduce-input"
          rows="3"
      ></textarea>
    </div>


    <div class="profile-actions">
      <!-- 수정 버튼 -->
      <button @click="toggleEdit" class="edit-button">
        {{ isEditing ? '수정 완료' : '프로필 수정' }}
      </button>
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
import AppHeader from "@/components/common/AppHeader.vue";
import AppFooter from "@/components/common/AppFooter.vue";
import axios from "axios";

export default {
  components: { AppHeader, AppFooter },
  data() {
    return {
      userId: localStorage.getItem("userId") || null,
      profileImage: null,
      defaultImage: require('@/assets/Weblogo.png'),
      name: "",
      location: "",
      sports: "",
      introduce: "",
      isEditing: false, // 전체 수정 상태 관리
    };
  },
  methods: {
    async loadProfile() {
      if (!this.userId) {
        alert("로그인이 필요합니다.");
        this.$router.push("/login");
        return;
      }
      try {
        const response = await axios.get(`http://localhost:9090/api/profiles/${this.userId}`);
        const profile = response.data;
        this.name = profile.name;
        this.location = profile.location;
        this.sports = profile.sports;
        this.introduce = profile.introduce;
        this.profileImage = profile.profileImage || this.defaultImage;
      } catch (error) {
        console.error("Failed to load profile:", error);
      }
    },
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
          introduce: this.introduce,
          profileImage: this.profileImage,
        };
        await axios.post(`http://localhost:9090/api/profiles/create/${this.userId}`, profileData);
      } catch (error) {
        console.error("Failed to save profile:", error);
      }
    },
    toggleEdit() {
      if (this.isEditing) {
        // 수정 완료 -> 저장
        this.saveProfile();
      }
      // 상태 토글
      this.isEditing = !this.isEditing;
    },
    selectImage() {
      this.$refs.fileInput.click();
    },
    uploadImage(event) {
      const file = event.target.files[0];
      if (file) {
        const formData = new FormData();
        formData.append("file", file);
        axios.post("http://localhost:9090/s3/resource", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
            .then((response) => {
              this.profileImage = response.data.path;
              if (!this.isEditing) {
                this.saveProfile(); // 수정 모드가 아닐 때 즉시 저장
              }
            })
            .catch((error) => {
              console.error("Failed to upload image:", error);
            });
      }
    },
  },
  mounted() {
    if (this.userId) {
      this.loadProfile();
    } else {
      alert("로그인이 필요합니다.");
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
  margin-top: 5%;
  margin-bottom: 5%;
  gap: 5%;
}

.profile-img {
  width: 35%;
  max-width: 200px;
  margin-left: 10%;
  border-radius: 50%;
  cursor: default; /* 클릭할 수 있음을 시각적으로 표시 */
}

.profile-img.editable {
  cursor: pointer; /* 수정 모드일 때만 클릭 가능 */
  padding: 5px;
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

.profile-actions {
  display: flex;
  justify-content: space-evenly;
  gap: 10px;
  margin-left: 10%;
  margin-right: 10%;
}

.introduce-container{
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  margin-left: 5%;
  margin-right: 5%;
}

.edit-button {
  padding: 8px 15px;
  font-size: 0.9rem;
  border-radius: 4px;
  cursor: pointer;
  background-color: #ffffff;
  color: #0056b3;
  border: 1px solid #0056b3;
}

.edit-button:hover {
  background-color: #ffffff;
  color: #0056b3;
  border: 1px solid #0056b3;
}

input {
  width: 85%;
  margin: 10px 0;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
  color: #333;
  outline: none;
}

.introduce-input {
  width: 100%;
  margin: 10px 0;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
  color: #333;
  outline: none;
}
.introduce-text {
  white-space: pre-wrap; /* 줄바꿈과 공백 유지 */
  word-wrap: break-word; /* 긴 단어를 다음 줄로 넘김 */
  text-align: center;
}



input:focus {
  border-color: #0056b3;
  box-shadow: 0 0 5px rgba(0, 86, 179, 0.5); /* 포커스 시 그림자 효과 */
}


</style>
