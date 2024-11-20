<template>
  <div>
    <h2>Find Friends</h2>
    <input v-model="searchQuery" @input="searchUsers" placeholder="Search users..." />
    <ul>
      <li v-for="user in searchResults" :key="user.id">
        {{ user.name }}
        <button @click="sendRequest(user.id)">Send Friend Request</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';
import friendService from "@/services/friendService";

export default {
  data() {
    return {
      searchQuery: '',
      searchResults: []
    };
  },
  methods: {
    searchUsers() {
      axios.get(`/api/users/search`, { params: { query: this.searchQuery } })
        .then(response => {
          this.searchResults = response.data;
        });
    },
    sendRequest(friendId) {
      friendService.sendFriendRequest(this.$route.params.userId, friendId)
        .then(() => {
          alert("친구 신청을 보냈습니다.");
        });
    }
  }
};
</script>
