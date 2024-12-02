<template>
  <div class="box">
    <div class="container">
      <RouterLink :to="{name: 'main'}">
      <img src="@/assets/color16.png" alt="Dash Logo" class="logo" />
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

  <div class="team">
    <h4>List</h4>
    <button @click="goToCreatePage" class="team-create-button">Create</button>
      <table class="team-table">
          <tr>
            <th>번호</th>
            <th>팀 이름</th>
            <th>팀장</th>
          </tr>
          <!-- 팀 리스트-->
          <tr v-for="(team, index) in store.teamList" :key="team.teamId">
            <td>{{ index + 1 }}</td>
            <td><router-link :to="`/team-detail/${team.teamId}`">
              {{ team.teamName }}
            </router-link></td>
            <td>{{ store.userMappings[team.leaderId] || `Unknown (${team.leaderId})` }}</td>
          </tr>
      </table>
  </div>
</template>

<script setup>
import { useTeamStore } from '@/stores/team';
import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import Logout from '@/components/account/Logout.vue';

const router = useRouter();
const store = useTeamStore(); // Pinia store 사용

// 컴포넌트가 마운트될 때 데이터 로드
onMounted(() => { 
  store.getTeamList(); // 팀 목록 가져오기
  store.fetchUserMappings()

});

const goToCreatePage = () => {
  router.push({ name: 'teamCreate' });
};

</script>

<style scoped>

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
  background-color: #990012;
  /* 박스 배경색 */
  color: #FCF7F4;
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
  background-color: #990012;
  /* 선의 색상 */
  margin: 20px 0;
  /* 위아래 간격 */
}

::v-deep(nav a) {
  color: #990012;
  /* 네비게이션 텍스트 색상 */
}

::v-deep(nav a:hover) {
  color: #ffa5a5;
  /* 호버 시 색상 */
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

.team-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  text-align: left;
  font-family: 'Inter', sans-serif;
  background: linear-gradient(120deg, #fff6f6, #ffe0e0); /* 부드러운 그라데이션 */
  border-radius: 0px; /* 둥근 테두리 */
  overflow: hidden; /* 테두리 넘침 방지 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
}

.team-table th {
  background: #990012; /* 헤더의 그라데이션 */
  color: #fff; /* 텍스트 색상 */
  font-weight: bold;
  font-size: 1.1rem;
  padding: 12px 8px; /* 적당한 여백 */
  text-transform: uppercase; /* 대문자 스타일 */
  text-align: center; /* 가운데 정렬 */
}

.team-table td {
  padding: 12px 8px;
  color: #990012; /* 텍스트 색상 */
  font-size: 0.95rem;
  font-weight: 500;
  text-align: center; /* 데이터도 가운데 정렬 */
  border-bottom: 1px solid rgba(0, 0, 0, 0.1); /* 각 행 구분 */
  background: rgba(255, 255, 255, 0.8); /* 반투명 배경 */
}

.team-table tr:last-child td {
  border-bottom: none; /* 마지막 행 아래 테두리 제거 */
}

/* Hover 애니메이션 */
.team-table tr:hover td {
  background: linear-gradient(120deg, #ffe0e0, #ffcccc); /* Hover 시 그라데이션 */
  color: #990012; /* 텍스트 강조 */
  transform: translateY(-2px); /* 약간 위로 올라오는 효과 */
  transition: all 0.3s ease; /* 부드러운 전환 */
}

/* 링크 스타일 */
.team-table td a {
  text-decoration: none;
  color: #990012;
  font-weight: bold;
  transition: color 0.3s ease; /* 부드러운 색상 변화 */
}

.team-table td a:hover {
  color: #ff6a7c; /* Hover 시 링크 색상 */
  text-decoration: underline; /* 강조 */
}

/* 리스트 외부 여백 */
.team {
  max-width: 800px;
  margin: 30px auto;
  background: linear-gradient(120deg, #ffffff, #ffffff); /* 박스 배경 그라데이션 */
  border-radius: 15px;
  padding: 20px;
  
}

h4 {
  font-family: 'Bangers', sans-serif;
  font-size: 2.5rem;
  color: #990012;
  text-align: center; /* 제목 가운데 정렬 */
  margin-bottom: 15px; /* 아래 간격 */
}


/* 버튼 */

.team-create-button {
  position: relative;
  display: flex;
  justify-content:space-between;
  margin-left: auto;
  padding: 10px 26px; /* 내부 여백 */
  font-size: 1rem; /* 글꼴 크기 */
  font-weight: bold; /* 글자 강조 */
  font-family: 'Inter', sans-serif; /* 글꼴 */
  color: #ffffff; /* 텍스트 색상 */
  background: linear-gradient(45deg, #990012, #ff6a7c); /* 버튼 배경 그라데이션 */
  border: none; /* 테두리 제거 */
  border-radius: 50px; /* 둥근 모서리 */
  cursor: pointer; /* 클릭 가능한 커서 */
  overflow: hidden; /* 내부 요소 초과 숨김 */
  z-index: 1; /* 요소 순서 */
  transition: all 0.3s ease; /* 애니메이션 전환 효과 */
}

.team-create-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.2); /* 반투명 흰색 */
  transition: all 0.4s ease;
  z-index: 2; /* 버튼 위에 위치 */
}

.team-create-button:hover::before {
  left: 100%; /* 호버 시 흰색 레이어 이동 */
}

.team-create-button:hover {
  background: linear-gradient(45deg, #6a4bdb, #5738AF); /* 호버 시 색상 반전 */
  color: #ffe400; /* 텍스트 색상 변경 */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
}

.team-create-button::after {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255, 253, 253, 0.2), transparent);
  opacity: 0;
  transition: all 0.5s ease;
  z-index: 1; /* 버튼 아래에서 효과 */
}

.team-create-button:active::after {
  opacity: 1;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.team-create-button:active {
  transform: scale(0.98); /* 클릭 시 살짝 눌리는 효과 */
}


</style>