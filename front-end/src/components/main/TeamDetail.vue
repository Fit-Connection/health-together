<template>
  <div class="team-detail">
    <h2>{{ team.teamName }}</h2>
    <p>운동 종류: {{ team.sportType }}</p>
    <button @click="showCreatePostForm">팀 모집 글 작성</button>

    <!-- 게시글 목록 -->
    <h3>게시판</h3>
    <ul class="post-list">
      <li v-for="post in posts" :key="post.postId" class="post-item">
        <h4>{{ post.title }}</h4>
        <p>{{ post.content }}</p>
        <span class="post-date">{{ post.postedAt }}</span>
      </li>
    </ul>

    <!-- 게시글 작성 폼 -->
    <div v-if="showPostForm" class="post-form">
      <input v-model="newPost.title" placeholder="제목" />
      <textarea v-model="newPost.content" placeholder="내용"></textarea>
      <button @click="createPost">게시글 작성</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      team: {},
      posts: [],
      showPostForm: false,
      newPost: {
        title: "",
        content: "",
      },
    };
  },
  methods: {
    async fetchTeam() {
      try {
        const response = await axios.get(`http://localhost:9090/api/teams/${this.$route.params.id}`);
        this.team = response.data;
      } catch (error) {
        console.error("팀 정보를 불러오는 중 오류 발생:", error);
      }
    },
    async fetchPosts() {
      try {
        const response = await axios.get(`http://localhost:9090/api/teams/${this.team.teamId}/posts`);
        this.posts = response.data;
      } catch (error) {
        console.error("게시글을 불러오는 중 오류 발생:", error);
      }
    },
    showCreatePostForm() {
      this.showPostForm = true;
    },
    async createPost() {
      try {
        await axios.post(`http://localhost:9090/api/teams/${this.team.teamId}/posts`, this.newPost);
        this.fetchPosts(); // 게시글 목록 갱신
        this.newPost.title = "";
        this.newPost.content = "";
        this.showPostForm = false;
      } catch (error) {
        console.error("게시글 작성 중 오류 발생:", error);
      }
    },
  },
  created() {
    this.fetchTeam();
    this.fetchPosts(); // 페이지 로드 시 게시글 목록 불러오기
  },
};
</script>

<style scoped>
.team-detail {
  padding: 20px;
}

.post-list {
  list-style: none;
  padding: 0;
}

.post-item {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-bottom: 10px;
}

.post-form {
  margin-top: 15px;
}

input, textarea {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  padding: 5px 10px;
  cursor: pointer;
}
</style>
