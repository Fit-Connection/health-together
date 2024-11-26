<template>
  <AppHeader :isMyPage="false" />
  <div class="challenge-container">
    <h2 class="page-title">챌린지</h2>
    <hr>
    <div v-if="userId && challengeInfo" class="user-info">
      <p><strong>등급:</strong> {{ challengeInfo.medal }}</p>
      <button @click="openPopup" class="info-button">등급 안내</button>
    </div>
  </div>
  <div class="challenge-list-container">
    <div class="challenge-card" v-for="(completed, challenge) in challengeList" :key="challenge">
      <div class="challenge-card-content">
        <div class="challenge-info">
          <li>{{ challenge }}</li>
        </div>
        <img
            v-if="completed"
            :src="'data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20%3F%3E%3Csvg%20height%3D%2260%22%20viewBox%3D%220%200%2060%2060%22%20width%3D%2260%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%3E%3Cdefs%3E%3Cstyle%3E%0A%20%20%20%20%20%20%20.cls-1%20%7B%0A%20%20%20%20%20%20%20%20%20fill%3A%20%23699f4c%3B%0A%20%20%20%20%20%20%20%20%20fill-rule%3A%20evenodd%3B%0A%20%20%20%20%20%20%20%7D%0A%20%20%20%20%3C/style%3E%3C/defs%3E%3Cpath%20class%3D%22cls-1%22%20d%3D%22M800%2C510a30%2C30%2C0%2C1%2C1%2C30-30A30%2C30%2C0%2C0%2C1%2C800%2C510Zm-16.986-23.235a3.484%2C3.484%2C0%2C0%2C1%2C0-4.9l1.766-1.756a3.185%2C3.185%2C0%2C0%2C1%2C4.574.051l3.12%2C3.237a1.592%2C1.592%2C0%2C0%2C0%2C2.311%2C0l15.9-16.39a3.187%2C3.187%2C0%2C0%2C1%2C4.6-.027L817%2C468.714a3.482%2C3.482%2C0%2C0%2C1%2C0%2C4.846l-21.109%2C21.451a3.185%2C3.185%2C0%2C0%2C1-4.552.03Z%22%20id%3D%22check%22%20transform%3D%22translate(-770%20-450)%22/%3E%3C/svg%3E'"
            alt="완료"
            class="icon-completed"
        />
      </div>
    </div>
  </div>

  <div v-if="showPopup" class="medal-info">
    <div class="popup-content">
      <h3 class="popup-head">등급 안내<button class="close-button" @click="closePopup"><img src="https://super.so/icon/dark/x.svg" alt="닫기" /></button></h3>
      <hr>
      <p><strong>현재 점수: </strong> {{ challengeInfo.totalScore }}점</p>
      <hr>
      <p><strong>동메달1</strong><img src="data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20%3F%3E%3Csvg%20id%3D%22Layer_1%22%20style%3D%22enable-background%3Anew%200%200%20120%20120%3B%22%20version%3D%221.1%22%20viewBox%3D%220%200%20120%20120%22%20xml%3Aspace%3D%22preserve%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20xmlns%3Axlink%3D%22http%3A//www.w3.org/1999/xlink%22%3E%3Cstyle%20type%3D%22text/css%22%3E%0A%09.st0%7Bfill%3A%23C4C4C4%3B%7D%0A%09.st1%7Bfill%3A%23E5B97F%3B%7D%0A%09.st2%7Bfill%3A%23C19A6B%3B%7D%0A%09.st3%7Bfill%3A%23FFFFFF%3B%7D%0A%3C/style%3E%3Cg%3E%3Cpolygon%20class%3D%22st0%22%20points%3D%2275.7%2C107.4%2060%2C97.5%2044.3%2C107.4%2044.3%2C41.1%2075.7%2C41.1%20%20%22/%3E%3Ccircle%20class%3D%22st1%22%20cx%3D%2260%22%20cy%3D%2244.8%22%20r%3D%2232.2%22/%3E%3Ccircle%20class%3D%22st2%22%20cx%3D%2260%22%20cy%3D%2244.8%22%20r%3D%2225.3%22/%3E%3Cpath%20class%3D%22st3%22%20d%3D%22M61.2%2C29.7l4.2%2C8.4c0.2%2C0.4%2C0.6%2C0.7%2C1%2C0.8l9.3%2C1.4c1.1%2C0.2%2C1.6%2C1.5%2C0.8%2C2.3l-6.7%2C6.6c-0.3%2C0.3-0.5%2C0.8-0.4%2C1.2%20%20%20l1.6%2C9.3c0.2%2C1.1-1%2C2-2%2C1.4l-8.3-4.4c-0.4-0.2-0.9-0.2-1.3%2C0L51%2C61.1c-1%2C0.5-2.2-0.3-2-1.4l1.6-9.3c0.1-0.4-0.1-0.9-0.4-1.2%20%20%20l-6.7-6.6c-0.8-0.8-0.4-2.2%2C0.8-2.3l9.3-1.4c0.4-0.1%2C0.8-0.3%2C1-0.8l4.2-8.4C59.3%2C28.7%2C60.7%2C28.7%2C61.2%2C29.7z%22/%3E%3C/g%3E%3C/svg%3E
" alt="동1" class="icon" /> : 11 ~ 20점</p>
      <p><strong>동메달2</strong><img src="data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20%3F%3E%3Csvg%20id%3D%22Layer_1%22%20style%3D%22enable-background%3Anew%200%200%20120%20120%3B%22%20version%3D%221.1%22%20viewBox%3D%220%200%20120%20120%22%20xml%3Aspace%3D%22preserve%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20xmlns%3Axlink%3D%22http%3A//www.w3.org/1999/xlink%22%3E%3Cstyle%20type%3D%22text/css%22%3E%0A%09.st0%7Bfill%3A%23C4C4C4%3B%7D%0A%09.st1%7Bfill%3A%23E5B97F%3B%7D%0A%09.st2%7Bfill%3A%23C19A6B%3B%7D%0A%09.st3%7Bfill%3A%23FFFFFF%3B%7D%0A%3C/style%3E%3Cg%3E%3Cpolygon%20class%3D%22st0%22%20points%3D%2279.7%2C45.6%2060%2C55.5%2040.3%2C45.6%2015.9%2C94.3%2031.1%2C92.8%2038.9%2C105.9%2060%2C63.9%2081.1%2C105.9%2088.9%2C92.8%20104.1%2C94.3%20%20%22/%3E%3Ccircle%20class%3D%22st1%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2232.2%22/%3E%3Ccircle%20class%3D%22st2%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2225.3%22/%3E%3Cpath%20class%3D%22st3%22%20d%3D%22M61.2%2C31.2l4.2%2C8.4c0.2%2C0.4%2C0.6%2C0.7%2C1%2C0.8l9.3%2C1.4c1.1%2C0.2%2C1.6%2C1.5%2C0.8%2C2.3l-6.7%2C6.6c-0.3%2C0.3-0.5%2C0.8-0.4%2C1.2%20%20%20l1.6%2C9.3c0.2%2C1.1-1%2C2-2%2C1.4l-8.3-4.4c-0.4-0.2-0.9-0.2-1.3%2C0L51%2C62.6c-1%2C0.5-2.2-0.3-2-1.4l1.6-9.3c0.1-0.4-0.1-0.9-0.4-1.2%20%20%20l-6.7-6.6c-0.8-0.8-0.4-2.2%2C0.8-2.3l9.3-1.4c0.4-0.1%2C0.8-0.3%2C1-0.8l4.2-8.4C59.3%2C30.2%2C60.7%2C30.2%2C61.2%2C31.2z%22/%3E%3C/g%3E%3C/svg%3E
" alt="동2" class="icon" /> : 21 ~ 30점</p>
      <p><strong>은메달1</strong><img src="data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20%3F%3E%3Csvg%20id%3D%22Layer_1%22%20style%3D%22enable-background%3Anew%200%200%20120%20120%3B%22%20version%3D%221.1%22%20viewBox%3D%220%200%20120%20120%22%20xml%3Aspace%3D%22preserve%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20xmlns%3Axlink%3D%22http%3A//www.w3.org/1999/xlink%22%3E%3Cstyle%20type%3D%22text/css%22%3E%0A%09.st0%7Bfill%3A%23FFBF4B%3B%7D%0A%09.st1%7Bfill%3A%23EDEDED%3B%7D%0A%09.st2%7Bfill%3A%23BCBCBC%3B%7D%0A%09.st3%7Bfill%3A%23FFFFFF%3B%7D%0A%3C/style%3E%3Cg%3E%3Cpolygon%20class%3D%22st0%22%20points%3D%2275.7%2C107.4%2060%2C97.5%2044.3%2C107.4%2044.3%2C41.1%2075.7%2C41.1%20%20%22/%3E%3Ccircle%20class%3D%22st1%22%20cx%3D%2260%22%20cy%3D%2244.8%22%20r%3D%2232.2%22/%3E%3Ccircle%20class%3D%22st2%22%20cx%3D%2260%22%20cy%3D%2244.8%22%20r%3D%2225.3%22/%3E%3Cpath%20class%3D%22st3%22%20d%3D%22M61.2%2C29.7l4.2%2C8.4c0.2%2C0.4%2C0.6%2C0.7%2C1%2C0.8l9.3%2C1.4c1.1%2C0.2%2C1.6%2C1.5%2C0.8%2C2.3l-6.7%2C6.6c-0.3%2C0.3-0.5%2C0.8-0.4%2C1.2%20%20%20l1.6%2C9.3c0.2%2C1.1-1%2C2-2%2C1.4l-8.3-4.4c-0.4-0.2-0.9-0.2-1.3%2C0L51%2C61.1c-1%2C0.5-2.2-0.3-2-1.4l1.6-9.3c0.1-0.4-0.1-0.9-0.4-1.2%20%20%20l-6.7-6.6c-0.8-0.8-0.4-2.2%2C0.8-2.3l9.3-1.4c0.4-0.1%2C0.8-0.3%2C1-0.8l4.2-8.4C59.3%2C28.7%2C60.7%2C28.7%2C61.2%2C29.7z%22/%3E%3C/g%3E%3C/svg%3E
" alt="은1" class="icon" /> : 31 ~ 40점</p>
      <p><strong>은메달2</strong><img src="data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20%3F%3E%3Csvg%20id%3D%22Layer_1%22%20style%3D%22enable-background%3Anew%200%200%20120%20120%3B%22%20version%3D%221.1%22%20viewBox%3D%220%200%20120%20120%22%20xml%3Aspace%3D%22preserve%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20xmlns%3Axlink%3D%22http%3A//www.w3.org/1999/xlink%22%3E%3Cstyle%20type%3D%22text/css%22%3E%0A%09.st0%7Bfill%3A%23FFBF4B%3B%7D%0A%09.st1%7Bfill%3A%23EDEDED%3B%7D%0A%09.st2%7Bfill%3A%23BCBCBC%3B%7D%0A%09.st3%7Bfill%3A%23FFFFFF%3B%7D%0A%3C/style%3E%3Cg%3E%3Cpolygon%20class%3D%22st0%22%20points%3D%2279.7%2C45.6%2060%2C55.5%2040.3%2C45.6%2015.9%2C94.3%2031.1%2C92.8%2038.9%2C105.9%2060%2C63.9%2081.1%2C105.9%2088.9%2C92.8%20104.1%2C94.3%20%20%22/%3E%3Ccircle%20class%3D%22st1%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2232.2%22/%3E%3Ccircle%20class%3D%22st2%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2225.3%22/%3E%3Cpath%20class%3D%22st3%22%20d%3D%22M61.2%2C31.2l4.2%2C8.4c0.2%2C0.4%2C0.6%2C0.7%2C1%2C0.8l9.3%2C1.4c1.1%2C0.2%2C1.6%2C1.5%2C0.8%2C2.3l-6.7%2C6.6c-0.3%2C0.3-0.5%2C0.8-0.4%2C1.2%20%20%20l1.6%2C9.3c0.2%2C1.1-1%2C2-2%2C1.4l-8.3-4.4c-0.4-0.2-0.9-0.2-1.3%2C0L51%2C62.6c-1%2C0.5-2.2-0.3-2-1.4l1.6-9.3c0.1-0.4-0.1-0.9-0.4-1.2%20%20%20l-6.7-6.6c-0.8-0.8-0.4-2.2%2C0.8-2.3l9.3-1.4c0.4-0.1%2C0.8-0.3%2C1-0.8l4.2-8.4C59.3%2C30.2%2C60.7%2C30.2%2C61.2%2C31.2z%22/%3E%3C/g%3E%3C/svg%3E
" alt="은2" class="icon" /> : 41 ~ 50점</p>
      <p><strong>금메달1</strong><img src="data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20%3F%3E%3Csvg%20id%3D%22Layer_1%22%20style%3D%22enable-background%3Anew%200%200%20120%20120%3B%22%20version%3D%221.1%22%20viewBox%3D%220%200%20120%20120%22%20xml%3Aspace%3D%22preserve%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20xmlns%3Axlink%3D%22http%3A//www.w3.org/1999/xlink%22%3E%3Cstyle%20type%3D%22text/css%22%3E%0A%09.st0%7Bfill%3A%23E24255%3B%7D%0A%09.st1%7Bfill%3A%23FFC54D%3B%7D%0A%09.st2%7Bfill%3A%23E8B04B%3B%7D%0A%09.st3%7Bfill%3A%23FFFFFF%3B%7D%0A%3C/style%3E%3Cg%3E%3Cpolygon%20class%3D%22st0%22%20points%3D%2279.7%2C45.6%2060%2C55.5%2040.3%2C45.6%2015.9%2C94.3%2031.1%2C92.8%2038.9%2C105.9%2060%2C63.9%2081.1%2C105.9%2088.9%2C92.8%20104.1%2C94.3%20%20%22/%3E%3Ccircle%20class%3D%22st1%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2232.2%22/%3E%3Ccircle%20class%3D%22st2%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2225.3%22/%3E%3Cpath%20class%3D%22st3%22%20d%3D%22M61.2%2C31.2l4.2%2C8.4c0.2%2C0.4%2C0.6%2C0.7%2C1%2C0.8l9.3%2C1.4c1.1%2C0.2%2C1.6%2C1.5%2C0.8%2C2.3l-6.7%2C6.6c-0.3%2C0.3-0.5%2C0.8-0.4%2C1.2%20%20%20l1.6%2C9.3c0.2%2C1.1-1%2C2-2%2C1.4l-8.3-4.4c-0.4-0.2-0.9-0.2-1.3%2C0L51%2C62.6c-1%2C0.5-2.2-0.3-2-1.4l1.6-9.3c0.1-0.4-0.1-0.9-0.4-1.2%20%20%20l-6.7-6.6c-0.8-0.8-0.4-2.2%2C0.8-2.3l9.3-1.4c0.4-0.1%2C0.8-0.3%2C1-0.8l4.2-8.4C59.3%2C30.2%2C60.7%2C30.2%2C61.2%2C31.2z%22/%3E%3C/g%3E%3C/svg%3E
" alt="금1" class="icon" /> : 51 ~ 60점</p>
      <p><strong>금메달2</strong><img src="data:image/svg+xml,%3Csvg%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20viewBox%3D%220%200%20120%20120%22%3E%3Cstyle%3E.st0%7Bfill%3A%23E24255%7D.st1%7Bfill%3A%23FFC54D%7D.st2%7Bfill%3A%23E8B04B%7D.st3%7Bfill%3A%23FFFFFF%7D%3C/style%3E%3Cg%3E%3Cpolygon%20class%3D%22st0%22%20points%3D%2279.7%2C45.6%2060%2C55.5%2040.3%2C45.6%2015.9%2C94.3%2031.1%2C92.8%2038.9%2C105.9%2060%2C63.9%2081.1%2C105.9%2088.9%2C92.8%20104.1%2C94.3%22/%3E%3Ccircle%20class%3D%22st1%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2232.2%22/%3E%3Ccircle%20class%3D%22st2%22%20cx%3D%2260%22%20cy%3D%2246.4%22%20r%3D%2225.3%22/%3E%3Cpath%20class%3D%22st3%22%20d%3D%22M61.2%2C31.2l4.2%2C8.4c0.2%2C0.4%2C0.6%2C0.7%2C1%2C0.8l9.3%2C1.4c1.1%2C0.2%2C1.6%2C1.5%2C0.8%2C2.3l-6.7%2C6.6c-0.3%2C0.3-0.5%2C0.8-0.4%2C1.2%20l1.6%2C9.3c0.2%2C1.1-1%2C2-2%2C1.4l-8.3-4.4c-0.4-0.2-0.9-0.2-1.3%2C0L51%2C62.6c-1%2C0.5-2.2-0.3-2-1.4l1.6-9.3c0.1-0.4-0.1-0.9-0.4-1.2%20l-6.7-6.6c-0.8-0.8-0.4-2.2%2C0.8-2.3l9.3-1.4c0.4-0.1%2C0.8-0.3%2C1-0.8l4.2-8.4C59.3%2C30.2%2C60.7%2C30.2%2C61.2%2C31.2z%22/%3E%3C/g%3E%3C/svg%3E
" alt="금2" class="icon" /> : 61점 이상</p>
    </div>
  </div>
  <AppFooter />
</template>

<script>
import axios from "axios";
import AppHeader from "@/components/common/header/AppHeader.vue";
import AppFooter from "@/components/common//footer/AppFooter.vue";

export default {
  components: {AppFooter, AppHeader},
  data() {
    return {
      userId: localStorage.getItem("userId") || null,
      challengeInfo: null,
      challengeList: {},
      showPopup: false,
    };
  },
  methods: {
    async fetchChallengeInfo() {
      try {
        const response = await axios.get(`http://localhost:9090/api/challenges/${this.userId}`);
        this.challengeInfo = response.data;
      } catch (error) {
        console.error("Error fetching challenge data:", error);
      } finally {
        this.loading = false;
      }
    },
    async fetchChallengeList() {
      try {
        const response = await axios.get(`http://localhost:9090/api/challenges/list/${this.userId}`);
        this.challengeList = response.data;
        console.log(response.data)
      } catch (error) {
        console.error("Error fetching challenge data:", error);
      } finally {
        this.loading = false;
      }
    },
    openPopup() {
      this.showPopup = true;
    },
    // 팝업 닫기
    closePopup() {
      this.showPopup = false;
    },


  },
  mounted() {
    if (this.userId) {
      this.fetchChallengeInfo();
      this.fetchChallengeList();
    } else {
      alert("로그인이 필요합니다.");
      this.$router.push("/login");
    }
  },
};
</script>

<style scoped>
.challenge-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 8px;
}

.page-title {
  text-align: center;
  color: #333;
  margin-bottom: 10px;
}


.challenge-list-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.challenge-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.challenge-card-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 15px;
  flex: 1; /* 정보 영역 확장 */
}


.medal-info {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.popup-head {
  display: flex;
  font-size: 1.5em;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
}

.popup-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.popup-content h3 {
  margin-top: 0;
  color: #333;
}

.popup-content p {
  margin: 10px 0;
  font-size: 1rem;
  color: #555;
}

.challenge-info p {
  font-size: 1.2em;
  margin: 10px 0;
}

.close-button, .info-button {
  padding: 8px 15px;
  cursor: pointer;
  border: none;
  background-color: #ffffff;
}


.user-info {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-left: 5%;
  margin-right: 5%;
}

.user-info p {
  margin:10px 0;
}

.icon {
  width: 50px;
  height: auto;
}

.icon-completed {
  width: 30px;
  height: auto;
}

</style>