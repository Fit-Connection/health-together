import { createRouter, createWebHistory } from 'vue-router';
import TeamSearch from '@/components/TeamSearch.vue';
import TeamDetail from '@/components/TeamDetail.vue';
import TeamReservation from '@/components/TeamReservation.vue';
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";

const routes = [
    { path: '/', name: 'TeamSearch', component: TeamSearch },
    { path: '/login', name: 'LoginView', component: LoginView },
    { path: '/register', name: 'RegisterView', component: RegisterView },
    { path: '/team/:id', name: 'TeamDetail', component: TeamDetail },
    { path: '/reservation', name: 'TeamReservation', component: TeamReservation },
];

const router = createRouter({
    history: createWebHistory(), // Vue 3에서는 createWebHistory를 사용합니다.
    routes
});

export default router;
