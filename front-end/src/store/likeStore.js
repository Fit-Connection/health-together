import { defineStore } from 'pinia';
import api from "@/api"; // 좋아요 데이터를 서버와 통신

export const useLikeStore = defineStore('likeStore', {
    state: () => ({
        likes: [], // 좋아요 데이터
        isLoading: false, // 로딩 상태
        error: null, // 에러 상태
    }),
    actions: {
        // 좋아요 데이터 로드
        async fetchLikes() {
            if (this.likes.length > 0) return; // 이미 데이터가 있으면 요청하지 않음
            this.isLoading = true;
            this.error = null;
            try {
                const response = await api.get('/likes'); // 서버에서 좋아요 데이터 가져오기
                this.likes = response.data;
            } catch (error) {
                this.error = error.message;
            } finally {
                this.isLoading = false;
            }
        },
        // 좋아요 추가/삭제 토글
        async toggleLike(likeRequest) {
            try {
                const index = this.likes.findIndex(
                    (like) =>
                        like.userId === likeRequest.userId &&
                        like.teamId === likeRequest.teamId
                );

                if (index !== -1) {
                    // 좋아요 삭제
                    console.log("좋아요 삭제")
                    await api.post('/likes', likeRequest);
                    this.likes.splice(index, 1); // 로컬에서 삭제
                } else {
                    // 좋아요 추가
                    console.log("좋아요 추가")
                    await api.post('/likes', likeRequest);
                    this.likes.push(likeRequest); // 로컬에서 추가
                }
            } catch (error) {
                console.error('좋아요 처리 중 오류:', error.message);
            }
        },
    },
    getters: {
        // 특정 사용자가 특정 팀에 좋아요를 눌렀는지 확인
        hasUserLiked: (state) => (userId, teamId) =>
            state.likes.some(
                (like) => like.userId === userId && like.teamId === teamId
            ),
    },
});
