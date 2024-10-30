import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(router); // Vue 3에서 라우터 사용
app.mount('#app');
