<template>
  <div class="page-background">
  <nav>
    <div class="box">
    <div class="container">
      <RouterLink :to="{name: 'main'}">
      <img src="@/assets/color19.png" alt="Dash Logo" class="logo" />
      </RouterLink> 
      <div class="logout">
        <Logout />
      </div>
    </div>
  </div>
  
  <nav>
    <TheHeaderNav />
  </nav>

  <div class="divider"></div> 
  </nav>
  <div class="Map">
    <h3>Map</h3>
    <div id="map" style="width: 50%; height: 500px;"></div>
  </div>
</div>
</template>

<script setup>
import Logout from '@/components/account/Logout.vue';
import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
import { onMounted } from 'vue';

const grounds = [
  { name: '노량진 축구장', lat: 37.515255, lng: 126.941258 },
  { name: '반포종합운동장 축구장', lat: 37.500590, lng: 126.994179 },
  { name: '고척스카이돔 축구장', lat: 37.496084, lng: 126.866891 },
  { name: '응봉풋살장', lat: 37.551240, lng: 127.036011 },
  { name: '고척스카이돔 풋살구장', lat: 37.496054, lng: 126.867443 },
  { name: '망원유수지 풋살구장', lat: 37.558417, lng: 126.896559 },
  { name: '응봉족구장', lat: 37.552466, lng: 127.039364 },
  { name: '아차산배수지체육공원 족구장', lat: 37.549196, lng: 127.096019 },
  { name: '중랑물재생센터 족구장', lat: 37.557902, lng: 127.063331 },
];

onMounted(() => {
  const apiKey = import.meta.env.VITE_KAKAO_API_KEY; // .env에서 API 키 가져오기

  const script = document.createElement('script');
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${apiKey}`;
  script.async = true;
  script.defer = true;

  script.onload = () => {
    kakao.maps.load(() => {
      const mapContainer = document.getElementById('map'); // 지도 표시할 div
      const mapOption = {
        center: new kakao.maps.LatLng(37.566535, 126.9779692), // 서울 중심 좌표
        level: 8, // 초기 확대 레벨
      };
      const map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성

      const overlays = []; // 커스텀 오버레이 저장용

      // Markers 및 CustomOverlay 추가
      grounds.forEach(ground => {
        const markerPosition = new kakao.maps.LatLng(ground.lat, ground.lng);

        // 마커 생성
        const marker = new kakao.maps.Marker({
          position: markerPosition,
          map: map, // 지도 객체
        });

        // 커스텀 오버레이 생성 (경기장 이름)
        const customOverlay = new kakao.maps.CustomOverlay({
          map: null, // 처음에는 표시하지 않음
          position: markerPosition,
          content: `<div style="padding: 2px 5px; background: white; border: 1px solid #ccc; border-radius: 3px; font-size: 12px;">${ground.name}</div>`,
          yAnchor: 1.5, // 마커 기준으로 텍스트 위치 조정
        });

        overlays.push(customOverlay); // 배열에 추가
      });

      // 지도 확대 레벨 변경에 따라 오버레이 표시/숨기기
      kakao.maps.event.addListener(map, 'idle', () => {
        const level = map.getLevel(); // 현재 확대 레벨
        overlays.forEach(overlay => {
          if (level <= 6) {
            overlay.setMap(map); // 확대 레벨이 6 이하일 때만 표시
          } else {
            overlay.setMap(null); // 숨기기
          }
        });
      });
    });
  };

  document.head.appendChild(script);
});

</script>

<style scoped>
.page-background{
  background-color: #807D6E; /* 원하는 배경색 */
  min-height: 100vh; /* 화면 전체 높이 */
  padding: 0;
  margin: 0;
}
.logout {
  position: absolute; /* 부모인 .header를 기준으로 배치 */
  right: 20px; /* 오른쪽 끝에서 20px 떨어짐 */
  top: 4%; /* 수직 가운데 정렬 */
  transform: translateY(-50%); /* 가운데 정렬 보정 */
}
.box {
  width: 100%;
  /* 화면 전체 너비 */
  height: 70px;
  /* 높이 */
  background-color: #FED700;
  /* 박스 배경색 */
  color: #ffffff;
  /* 텍스트 색상 */
  display: flex;
  /* 수평 및 수직 정렬을 위한 플렉스 박스 */
  align-items: center;
  /* 수직 가운데 정렬 */
  justify-content: center;
  /* 수평 가운데 정렬 */
  margin-bottom: 20px;
}

.divider {
  width: 100%;
  /* 선의 너비 */
  height: 2px;
  /* 선의 높이 */
  background-color: #FED700;
  /* 선의 색상 */
  margin: 20px 0;
  /* 위아래 간격 */
}

.container {
  display: flex;
  justify-content: center; /* 수평 가운데 */
  align-items: center;    /* 수직 가운데 */
  height: 100px;          /* 전체 화면 높이 */
  width: 100px;            /* 전체 화면 너비 */
}

.logo {
  width: 100px; /* 로고 크기 */
  
}

::v-deep(nav a) {
  color: #FED700;
  /* 네비게이션 텍스트 색상 */
}

::v-deep(nav a:hover) {
  color: #ffffff;
  /* 호버 시 색상 */
}

/* ------------------------------------------------------------- */

.Map{
  display: flex;
  font-family: 'Bangers', sans-serif;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}

h3{
  font-size: 50px;
  color: #FED700;
}
</style>