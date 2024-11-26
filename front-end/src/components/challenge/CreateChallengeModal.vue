<template>
  <div class="modal-overlay" v-if="isVisible">
    <div class="modal-content">
      <h2>챌린지 추가</h2>
      <form @submit.prevent="createChallenge">
        <div class="mb-3">
          <label for="goal" class="form-label">목표</label>
          <input
              v-model="form.goal"
              type="text"
              class="form-control"
              id="goal"
              placeholder="챌린지 목표를 입력하세요"
              required
          />
        </div>
        <div class="mb-3">
          <label for="reward" class="form-label">보상</label>
          <input
              v-model="form.reward"
              type="text"
              class="form-control"
              id="reward"
              placeholder="챌린지 보상을 입력하세요"
          />
        </div>
        <button type="submit" class="btn btn-primary">추가</button>
        <button type="button" class="btn btn-secondary" @click="closeModal">
          취소
        </button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      form: {
        goal: "",
        reward: "",
      },
    };
  },
  methods: {
    createChallenge() {
      // API 요청을 통해 챌린지 생성
      this.$emit("challengeCreated", { ...this.form });
      this.closeModal();
    },
    closeModal() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 300px;
}
</style>
