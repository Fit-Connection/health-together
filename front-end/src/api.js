import axios from "axios";

// 환경변수에서 기본 API URL 가져오기
const api = axios.create({
    baseURL: 'http://localhost:9090/api',
});

// 필요에 따라 다른 설정 추가 가능
export default api;
