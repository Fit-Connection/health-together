import { createRouter, createWebHistory } from 'vue-router';
import TeamSearch from '@/components/main/TeamSearch.vue';
import TeamDetail from '@/components/main/TeamDetail.vue';
import TeamReservation from '@/components/TeamReservation.vue';
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import CreateTeam from "@/components/main/CreateTeam.vue";

const routes = [
    // 팀 검색 메인 페이지
    { path: '/', name: 'TeamSearch', component: TeamSearch },

    // 로그인 페이지
    { path: '/login', name: 'LoginView', component: LoginView },

    // 회원가입 페이지
    { path: '/register', name: 'RegisterView', component: RegisterView },

    // 특정 팀 상세 페이지 (팀 ID로 조회)
    { path: '/team/:id', name: 'TeamDetail', component: TeamDetail },

    // 팀 만들기
    { path: "/create-team/:id?", name: "CreateTeam", component: CreateTeam },

    // 팀 예약 페이지
    { path: '/reservation', name: 'TeamReservation', component: TeamReservation },
];

const router = createRouter({
    history: createWebHistory(), // Vue 3에서는 createWebHistory를 사용합니다.
    routes
});

export default router;
