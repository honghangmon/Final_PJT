<template>
  <div class="page-background">
  <div class="box">
    <div class="container">
      <RouterLink :to="{ name: 'main' }">
        <img src="@/assets/color30.png" alt="Dash Logo" class="logo" />
      </RouterLink>
      <div class="logout">
        <Logout />
      </div>
    </div>
  </div>

  <nav>
    <TheHeaderNav />
  </nav>

  <div class="divider"></div> <!-- 선을 위한 추가 태그 -->

  <div>
    <img src="@/assets/313131.png" />
  </div>
</div>
</template>

<script setup>
import Logout from '@/components/account/Logout.vue';
import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
import { useSessionStore } from '@/stores/session';
import { RouterLink } from 'vue-router';

const store = useSessionStore();

const user = store.currentUser; // MainView로 넘어오면서 index.js에서 checkSession 메서드를 한 번 실행하므로 그냥 currentUser를 가져와도 되는 것 같음!

// const check = async function() {
//   await store.checkSession();
//   console.log(store.isAuthenticated);
// }

</script>

<style scoped>
.page-background {
  background-color: #000000;
  min-height: 100vh; /* 화면 전체 높이 */
  display: flex; /* Flexbox 활성화 */
  flex-direction: column; /* 세로 정렬 */
  justify-content: center; /* 세로 가운데 정렬 */
  align-items: center; /* 가로 가운데 정렬 */
  padding: 0;
  margin: 0;
}

nav {
  top: 0; /* 화면 상단에 배치 */
  left: 0;
  width: 100%; /* 화면 전체 너비 */
  background-color: transparent; /* 배경색 */
  z-index: 10; /* 다른 요소 위에 표시 */
}


.logout {
  position: absolute;
  /* 부모인 .header를 기준으로 배치 */
  right: 20px;
  /* 오른쪽 끝에서 20px 떨어짐 */
  top: 4%;
  /* 수직 가운데 정렬 */
  transform: translateY(-50%);
  /* 가운데 정렬 보정 */
}

.box {
  width: 100%;
  /* 화면 전체 너비 */
  height: 70px;
  /* 높이 */
  background-color: #DCDCDC;
  /* 박스 배경색 */
  color: #DCDCDC;
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
  background-color: #DCDCDC;
  /* 선의 색상 */
  margin: 20px 0;
  /* 위아래 간격 */
}

.container {
  display: flex;
  justify-content: center;
  /* 수평 가운데 */
  align-items: center;
  /* 수직 가운데 */
  height: 100px;
  /* 전체 화면 높이 */
  width: 100px;
  /* 전체 화면 너비 */
}

.logo {
  width: 100px;
  /* 로고 크기 */

}
::v-deep(nav a) {
  color: #DCDCDC;
  /* 네비게이션 텍스트 색상 */
}

::v-deep(nav a:hover) {
  color: #565656;
  /* 호버 시 색상 */
}
</style>