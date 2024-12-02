<template>
  
    <button @click="logout">Logout</button>
  
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const store = useUserStore();
const router = useRouter();

const logout = function () {
  store.logout();
  router.push({ name: 'home'});
}
</script>

<style scoped>

button {
  background-color: #ffffff;
  /* 기본 배경색 */
  color: rgb(47, 33, 243);
  /* 텍스트 색상 */
  font-size: 14px;
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  padding: 3px 7px;
  /* 버튼 크기 */
  border: none;
  border-radius: 3px;
  /* 모서리 둥글게 */
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  /* 기본 그림자 */
  transition: all 0.3s ease;
  /* 애니메이션 전환 효과 */
  position: relative;
  overflow: hidden;
  /* 내부 애니메이션을 제한 */
}

button:hover {
  background-color: #566cfd;
  /* 호버 시 배경색 변경 */
  transform: translateY(-2px);
  /* 마우스 오버 시 살짝 위로 이동 */
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.2);
  /* 그림자 더 강하게 */
}

button:active {
  transform: translateY(2px);
  /* 클릭 시 버튼 살짝 눌림 효과 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /* 그림자 줄이기 */
}

/* 버튼 내부 애니메이션 */
button::after {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 200%;
  height: 100%;
  background: rgba(255, 253, 253, 0.2);
  /* 반투명 흰색 레이어 */
  transform: skewX(-45deg);
  transition: all 0.5s ease;
}

button:hover::after {
  left: 100%;
  /* 마우스 오버 시 흰색 레이어가 지나가는 애니메이션 */
}
</style>