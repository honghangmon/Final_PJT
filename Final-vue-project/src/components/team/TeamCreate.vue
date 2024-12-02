<template>
  <div class="page-background">
  <nav>
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
  </nav>
  <div class=".create-box-container">
    <div class="cre">
    <fieldset>
  <legend>등록</legend>
  <div style="display: flex; align-items: center;">
    <label for="teamName">팀 이름 :</label>
    <input type="text" id="teamName" v-model="team.teamName">
  </div>
  <div style="text-align: center;">
    <button class="btn" @click="createTeam">등록</button>
    <button class="btn" @click="goBack">취소</button>
  </div>
</fieldset>
</div>
  </div>
  </div>
</template>

<script setup>
import Logout from '@/components/account/Logout.vue';
import { useTeamStore } from '@/stores/team';
import { ref } from 'vue';
import { useSessionStore } from '@/stores/session';
import { useRouter } from 'vue-router';
import TheHeaderNav from '../common/TheHeaderNav.vue';

// 현재 사용자 정보 가져오기
const sessionStore = useSessionStore();
const user = sessionStore.currentUser; // sessionStore의 currentUser로부터 유저 정보 가져옴
const router = useRouter();
// 팀 정보 관리
const teamStore = useTeamStore();
const team = ref({
  teamName: '',
  leaderId: user.userId, // 팀장 ID를 현재 사용자 ID로 설정
});

// 팀 생성 함수
const createTeam = () => {
  if (!team.value.teamName.trim()) {
    alert('팀 이름을 입력하세요.');
    return;
  }
  teamStore.createTeam(team.value)
};

const goBack = () => {
  router.push({ name: 'teamList' });
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

/* 페이지 배경 */

/* 생성 상자를 위한 컨테이너 */
/* 생성 상자를 위한 컨테이너 */
.create-box-container {
  display: flex;
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
}
.cre{
  display: flex;
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
  margin-top: 10%;
}

/* 생성 상자 스타일 */
fieldset {
  width: 400px;
  padding: 20px;
  border: none;
  background: linear-gradient(120deg, #fff, #ffe0e0);
  border-radius: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  text-align: left; /* 텍스트 왼쪽 정렬 */
  display: flex;
  flex-direction: column;
  gap: 20px; /* 요소 간 간격 */
}

/* 필드셋 제목 */
legend {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 1.7rem;
  color: #990012;
  padding: 0px;
  margin-bottom: 10px;
  text-align: center; /* 제목 중앙 정렬 */
}

/* 입력 필드 스타일 */
label {
  font-size: 1rem;
  font-weight: bold;
  color: #990012;
  margin-right: 10px;
  display: inline-block; /* inline-block으로 input과 정렬 */
  width: 100px; /* 레이블 너비 고정 */
  text-align: right; /* 레이블 텍스트 오른쪽 정렬 */
}

input[type="text"] {
  width: calc(100% - 120px); /* 레이블 제외한 나머지 공간 사용 */
  padding: 10px;
  border: 1px solid #ddd;
  font-family: 'Do Hyeon', sans-serif;
  color: #990012;
  border-radius: 0px;
  font-size: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  display: inline-block; /* 레이블과 같은 행 */
}



/* 버튼 스타일 */
.btn {
  padding: 10px 20px;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 50px;
  background: #990012;
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
  margin: 0 10px;
}

.btn:hover {
  background: linear-gradient(45deg, #ff6a7c, #990012);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.btn:active {
  transform: scale(0.98);
}


</style>
