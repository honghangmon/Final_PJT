<template>
  
    <nav>
      <div class="c1">
        <RouterLink :to="{ name: 'myPage' }">MY PAGE</RouterLink>
      </div>
      <div class="c2">
        <RouterLink :to="{ name: 'teamList' }">TEAM LIST</RouterLink>
      </div>
      <div class="c3">
        <RouterLink :to="{ name: 'matching' }"> <span>TEAM</span><br /><span>MATCHING</span></RouterLink>
      </div>
      <div class="c4">
        <RouterLink :to="{ name: 'ground' }">GROUND</RouterLink>
      </div>
      <div class="c5">
        <RouterLink :to="{ name: 'shop' }">SHOP</RouterLink>
      </div>
    </nav>
</template>

<script setup>
import { RouterLink } from 'vue-router';


</script>

<style scoped>
nav {
  display: flex; /* Flexbox 활성화 */
  justify-content: space-around; /* 모든 항목을 균등하게 배치 */
  align-items: center; /* 수직 가운데 정렬 */
  font-family: 'Bangers', sans-serif;
  font-size: 40px;
  font-weight: normal;
  color: #242E73;
}

div {
  display: flex; /* 각 박스를 Flexbox로 처리 */
  justify-content: center; /* 텍스트를 가운데 정렬 */
  align-items: center;
  flex-direction: column; /* TEAM MATCHING처럼 줄바꿈을 처리하려면 세로 방향 정렬 */
  width: 150px; /* 각 박스의 너비 */
  height: 100px; /* 각 박스의 높이 */
  text-align: center; /* 텍스트 가운데 정렬 */
  border: 0px solid #ddd; /* 선택 사항: 박스 테두리 */
  border-radius: 8px; /* 선택 사항: 모서리를 둥글게 */
}

nav a {
  color: #242E73;
  text-decoration: none;
}

nav a:hover {
  color: #9a9cfd;
}
</style>