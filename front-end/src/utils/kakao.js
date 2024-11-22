export function loadKakaoMaps() {
    return new Promise((resolve, reject) => {
        // 이미 로드된 경우, 바로 resolve
        if (window.kakao && window.kakao.maps) {
            resolve(window.kakao);
            return;
        }

        // Kakao Maps API 동적 로드
        const kakaoApiKey = process.env.VUE_APP_KAKAO_MAP_API_KEY;

        if (!kakaoApiKey) {
            reject("Kakao Maps API 키가 설정되지 않았습니다.");
            return;
        }

        const script = document.createElement("script");
        script.onload = () => {
            if (window.kakao && window.kakao.maps) {
                resolve(window.kakao);
            } else {
                reject("Kakao Maps API 로드 실패");
            }
        };
        script.onerror = () => reject("Kakao Maps API 로드 실패");
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}&libraries=services`;
        document.head.appendChild(script);
    });
}
