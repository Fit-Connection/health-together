<template>
  <AppHeader :isMyPage="false" :isChatPage="true" />
  <div class="chat-container">
    <h3>{{ teamName }}</h3>
    <div class="messages" ref="messagesContainer">
      <div
          v-for="(message, index) in messages"
          :key="message.id"
          :class="['message-wrapper', message.sender === cachedProfileName ? 'mine' : 'theirs']"
      >
        <!-- 이름 표시 -->
        <div v-if="shouldShowName(index, message)" class="message-sender">
          {{ message.sender }}
        </div>

        <!-- 메시지와 시간 -->
        <div class="message-container">
          <!-- 내 메시지의 경우 왼쪽에 시간 -->
          <small v-if="message.sender === cachedProfileName" class="timestamp left-timestamp">
            {{ formatTimestamp(message.timestamp) }}
          </small>

          <!-- 메시지 내용 -->
          <div class="message">
            <span class="message-content">{{ message.content }}</span>
          </div>

          <!-- 상대 메시지의 경우 오른쪽에 시간 -->
          <small v-if="message.sender !== cachedProfileName" class="timestamp right-timestamp">
            {{ formatTimestamp(message.timestamp) }}
          </small>
        </div>
      </div>
    </div>
    <div class="message-input">
      <input
          v-model="messageContent"
          @keyup.enter="sendMessage"
          placeholder="메세지를 입력하세요"
      />
      <button @click="sendMessage">전송</button>
    </div>
  </div>
  <AppFooter />
</template>


<script>
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import AppHeader from "@/components/common/AppHeader.vue";
import AppFooter from "@/components/common/AppFooter.vue";
import axios from "axios";

export default {
  components: { AppHeader, AppFooter },
  data() {
    return {
      messages: [], // 메시지 배열
      messageContent: '', // 입력 메시지
      roomId: null, // Room ID
      sender: '', // 사용자 이름
      stompClient: null, // STOMP 클라이언트
      cachedProfileName: null, // 사용자 이름 캐시
    };
  },
  methods: {
    async connect() {
      const socket = new SockJS('http://localhost:9090/ws-chat');
      this.stompClient = Stomp.over(socket);

      await new Promise((resolve, reject) => {
        this.stompClient.connect(
            {},
            async () => {
              console.log(`Connected to room: ${this.roomId}`);

              // 기존 메시지 요청
              this.stompClient.send('/app/chat.history', {}, this.roomId);

              // 메시지 수신 처리
              this.stompClient.subscribe(`/topic/${this.roomId}`, (message) => {
                this.messages.push(JSON.parse(message.body)); // 새 메시지 추가
                this.scrollToBottom();
              });

              // 기존 메시지 기록 수신
              this.stompClient.subscribe(`/topic/${this.roomId}/history`, (historyMessage) => {
                const history = JSON.parse(historyMessage.body);
                this.messages = history;
                this.scrollToBottom();
              });

              resolve();
            },
            (error) => {
              console.error("WebSocket connection failed:", error);
              reject(error);
            }
        );
      });
    },
    async sendMessage() {
      if (!this.messageContent.trim()) return;
      console.log(this.cachedProfileName)

      if (!this.cachedProfileName) {
        const response = await axios.get(`http://localhost:9090/api/profiles/${this.userId}`);
        this.cachedProfileName = response.data.name;
      }
      console.log(this.cachedProfileName)
      const message = {
        sender: this.cachedProfileName,
        content: this.messageContent,
        roomId: this.roomId,
      };

      this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));
      this.messageContent = '';
      this.scrollToBottom();
    },
    async fetchTeamName() {
      try {
        const response = await axios.get(`http://localhost:9090/api/teams/${this.roomId}`);
        this.teamName = response.data.teamName;
      } catch (error) {
        console.error("Failed to fetch team name:", error);
        this.teamName = "Unknown Team";
      }
    },
    formatTimestamp(timestamp) {
      const date = new Date(timestamp);
      return date.toLocaleString('ko-KR', {
        hour: '2-digit',
        minute: '2-digit',
      });
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messagesContainer;
        if (container) {
          container.scrollTop = container.scrollHeight;
        }
      });
    },
    shouldShowName(index, message) {
      // 첫 번째 메시지거나, 이전 메시지와 보낸 사람이 다르면 이름 표시
      return index === 0 || this.messages[index - 1].sender !== message.sender;
    },

  },
  async mounted() {
    this.roomId = this.$route.params.id;
    await this.fetchTeamName();
    this.userId = localStorage.getItem('userId');

    const response = await axios.get(`http://localhost:9090/api/profiles/${this.userId}`);
    console.log(response.data)
    this.cachedProfileName = response.data.name;

    await this.connect();
  },
};
</script>

<style scoped>
/* 전체 채팅 컨테이너 */
.chat-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 120px); /* 상단, 하단 헤더를 제외한 높이 */
  padding: 10px;
  box-sizing: border-box;
}

/* 제목 */
h3 {
  text-align: center;
  margin-bottom: 10px;
}

/* 메시지 리스트 */
.messages {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

/* 각 메시지 래퍼 */
.message-wrapper {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
}

/* 내 메시지 (오른쪽 정렬) */
.message-wrapper.mine {
  align-items: flex-end;
}

/* 상대방 메시지 (왼쪽 정렬) */
.message-wrapper.theirs {
  align-items: flex-start;
}

/* 메시지 보낸 사람 이름 */
.message-sender {
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 0.9em;
  color: #555;
}

/* 메시지 컨테이너 (메시지 내용 + 시간) */
.message-container {
  display: flex;
  align-items: center;
  gap: 10px; /* 메시지와 시간 사이 간격 */
}

/* 내 메시지 시간 (왼쪽) */
.left-timestamp {
  font-size: 0.8em;
  color: #999;
  white-space: nowrap; /* 시간 줄바꿈 방지 */
  margin-right: 10px; /* 메시지와 시간 간격 */
}

/* 상대 메시지 시간 (오른쪽) */
.right-timestamp {
  font-size: 0.8em;
  color: #999;
  white-space: nowrap; /* 시간 줄바꿈 방지 */
  margin-left: 10px; /* 메시지와 시간 간격 */
}

/* 메시지 박스 */
.message {
  max-width: 70%; /* 메시지 최대 너비 */
  padding: 10px;
  border-radius: 10px;
  word-wrap: break-word; /* 긴 단어를 줄바꿈 */
}

/* 내 메시지 스타일 */
.message-wrapper.mine .message {
  background-color: #dcf8c6;
  text-align: left;
}

/* 상대방 메시지 스타일 */
.message-wrapper.theirs .message {
  background-color: #ffffff;
  border: 1px solid #ccc;
  text-align: left;
}

/* 메시지 입력창 컨테이너 */
.message-input {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border-top: 1px solid #ccc;
  margin-bottom: 20px;
}

/* 메시지 입력 필드 */
.message-input input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

/* 메시지 전송 버튼 */
.message-input button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #4caf50;
  color: white;
  cursor: pointer;
}

.message-input button:hover {
  background-color: #45a049;
}

</style>