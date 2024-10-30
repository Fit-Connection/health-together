<template>
  <div>
    <h2>친구 목록</h2>
    <ul>
      <li v-for="friend in friends" :key="friend.id">
        {{ friend.name }}
        <button @click="startChat(friend.id)">채팅하기</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      friends: [
        { id: 1, name: "친구1" },
        { id: 2, name: "친구2" }
      ]
    };
  },
  methods: {
    startChat(friendId) {
      this.$router.push({ name: 'Chat', params: { id: friendId } });
    }
  }
};
</script>
