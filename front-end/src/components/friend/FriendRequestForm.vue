
<template>
  <div>
    <h2>Send Friend Request</h2>
    <input v-model="friendId" placeholder="Friend ID" />
    <button @click="sendFriendRequest">Send</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userId: localStorage.getItem("userId") || null,
      friendId: ""
    };
  },
  methods: {
    async sendFriendRequest() {
      const userId = localStorage.getItem("userId");
      try {
        await axios.post("/api/friends/request", null, {
          params: { userId: userId, friendId: this.friendId }
        });
        alert("Friend request sent.");
        this.friendId = ""; // 입력 초기화
      } catch (error) {
        console.error("Error sending friend request:", error);
      }
    }
  }
};
</script>
