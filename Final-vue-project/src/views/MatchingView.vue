<template>
  <div class="page-background">
    <div class="box">
      <div class="container">
        <RouterLink :to="{ name: 'main' }">
          <img src="@/assets/color5.png" alt="Dash Logo" class="logo" />
        </RouterLink>
        <div class="logout">
          <Logout />
        </div>
      </div>
    </div>

    <nav>
      <TheHeaderNav class="custom-nav" />
    </nav>

    <div class="divider"></div>


    <div>

      <MatchCreate />

      <div class="match-status-container">
        <div class="wait">
          <h4>Waiting</h4>
          <div class="waitbox">
            <p v-for="pendingMatch in pendingMatches" :key="pendingMatch.matchId">
              <span class="match-label">팀:</span> <strong class="match-value1">{{ getTeamName(pendingMatch.team1Id)
                }}</strong><br>
              <span class="match-label">시간:</span> <strong class="match-value">{{ pendingMatch.scheduledTime
                }}</strong><br>
              <span class="match-label">장소:</span> <strong class="match-value">{{ pendingMatch.location }}</strong><br>
              <span class="match-label">종목:</span> <strong class="match-value">{{ pendingMatch.sportsType }}</strong>
              <button @click="deleteMatch(pendingMatch.matchId, pendingMatch.team1Id)">매칭 취소</button>
            <p>---------------------------------------------------------------</p>
            </p>
          </div>
        </div>

        <div class="comple">
          <h4>completed!</h4>
          <div class="comlpebox">
            <p v-for="confirmedMatch in confirmedMatches" :key="confirmedMatch.matchId" class="match-complete">
            <div class="match-info">
              <div class="match-teams">
                <span class="team-name">{{ getTeamName(confirmedMatch.team1Id) }}</span>
                <span class="vs-text">vs</span>
                <span class="team-name">{{ getTeamName(confirmedMatch.team2Id) }}</span>
              </div>
              <div class="match-details">
                <div class="detail-item">{{ confirmedMatch.location }}</div>
                <div class="detail-item">{{ confirmedMatch.scheduledTime }}</div>
                <div class="detail-item">{{ confirmedMatch.sportsType }}</div>
              </div>
            </div>
            <p class="line">-------------------------------------------------------</p>
            </p>

          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import Logout from '@/components/account/Logout.vue';
import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
import MatchCreate from '@/components/match/MatchCreate.vue';
import { useMatchStore } from '@/stores/match';
import { useSessionStore } from '@/stores/session';
import { useTeamStore } from '@/stores/team';
import { useUserStore } from '@/stores/user';
import { ref, onMounted, computed } from 'vue';

const matchStore = useMatchStore();
const userStore = useUserStore();
const sessionStore = useSessionStore();
const teamStore = useTeamStore();

onMounted(async () => {
  await matchStore.getAllMatches();
  await userStore.fetchUserTeams(sessionStore.currentUser.userId);
  await teamStore.getTeamList();
})

const userTeamIds = computed(() =>
  userStore.userTeams.map(team => team.teamId)
);

const pendingMatches = computed(() =>
  matchStore.matchList.filter(
    match =>
      match.status === 'pending' &&
      (userTeamIds.value.includes(match.team1Id) || userTeamIds.value.includes(match.team2Id))
  )
);

const confirmedMatches = computed(() =>
  matchStore.matchList.filter(
    match =>
      match.status === 'confirmed' &&
      (userTeamIds.value.includes(match.team1Id) || userTeamIds.value.includes(match.team2Id))
  )
);

// 팀 ID와 팀 이름 매핑 함수
const getTeamName = (teamId) => {
  const team = teamStore.teamList.find(team => team.teamId === teamId);
  return team ? team.teamName : 'Unknown Team';
};

const deleteMatch = async (matchId, teamId) => {
  // match의 team1의 리더 아이디가 currentUser와 같을 때만
  await teamStore.getTeam(teamId)
  // console.log('teamStore.team.leaderId : ', teamStore.team.leaderId, 'sessionStore.currentUser.userId : ', sessionStore.currentUser.userId)
  if (teamStore.team.leaderId == sessionStore.currentUser.userId) {
    matchStore.deleteMatch(matchId);
    window.location.reload();
    alert("요청 중인 매치를 취소하였습니다.");
  } else {
    alert("팀 리더만 매치 요청을 취소할 수 있습니다.")
  }
}



</script>

<style scoped>
.page-background {
  background-color: #FFDD00;
  /* 원하는 배경색 */
  min-height: 100vh;
  /* 화면 전체 높이 */
  padding: 0;
  margin: 0;
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
  background-color: #006937;
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
  background-color: #006937;
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
  color: #006937;
  /* 네비게이션 텍스트 색상 */
}

::v-deep(nav a:hover) {
  color: #ffffff;
  /* 호버 시 색상 */
}

/* 여기 까지가 디자인 */

/* 웨이팅, 매치컴플 박스  */

.match-label {
  font-family: "Do Hyeon", sans-serif;
  font-size: 27px;
}

.match-status-container {
  display: flex;
  /* Flexbox로 좌우 배치 */
  /* 좌우 끝으로 정렬 */
  gap: 20px;
  /* 두 박스 간 간격 */
  position: relative;
  /* 화면의 특정 위치에 고정 */
  justify-content: center;
  bottom: 100px;
  /* 화면 아래에서 20px 떨어진 위치 */
  left: 0;
  right: 0;
  padding: 0 20px;
  /* 좌우 여백 */
}

.wait,
.comple {
  width: 30%;
  padding: 20px;
  /* 내부 여백 */
  border: 2px solid #006937;
  /* 테두리 스타일 */
  border-radius: 10px;
  /* 둥근 모서리 */
  background-color: #00552c;
  /* 배경색 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  /* 그림자 */
}

h4 {
  font-family: 'Bangers', sans-serif;
  font-size: 150px;

}

.wait h4,
.comple h4 {
  font-family: 'Bangers', sans-serif;
  font-size: 90px !important;
  ;

  text-align: center;
  margin-bottom: 15px;
  /* 제목과 내용 간 간격 */
  font-size: 1.5rem;
  /* 제목 크기 */
  color: #FFDD00;
  /* 제목 색상 */
}

.wait p,
.comple p {
  font-size: 1rem;
  /* 텍스트 크기 */
  color: #beff0a;
  /* 텍스트 색상 */
}

strong {
  font-size: 20px;
}

/* 웨이팅, 매치컴플 박스  */

.waitbox {
  width: 100%;
  padding: 20px;
  /* 내부 여백 */
  border: 0px solid #006937;
  /* 테두리 스타일 */
  border-radius: 0px;
  /* 둥근 모서리 */
  background-color: #0000008c;
  /* 배경색 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  /* 그림자 */

}

.match-value {
  font-family: "Do Hyeon", sans-serif;
  font-size: 22px;
  font-weight: 300;
}

.match-value2 {
  font-family: 'Bangers', sans-serif;
  font-size: 1.7rem;
  font-weight: 100;

}

.vs-text {
  font-family: 'Bangers', sans-serif;
  font-size: 1.8rem;
  font-weight: 500;
  color: #ffa113;
  margin: 0 15px;
  /* 양쪽 여백 설정 */

}


.comlpebox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 20px;
  border: 0px solid #006937;
  border-radius: 0px;
  background-color: #0000008c;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.match-complete {
  display: flex;
  flex-direction: column;
  align-items: center; /* 세로축 기준 중앙 정렬 */
  text-align: center; /* 텍스트 중앙 정렬 */
  margin-bottom: 20px; /* 매치 간 간격 */
}

.match-info {
  display: flex;
  flex-direction: column;
  align-items: center; /* 모든 요소를 세로 중앙 정렬 */
  gap: 10px; /* 각 행 간 간격 */
}

.match-teams {
  display: flex;
  justify-content: center;
  align-items: center; /* 세로축 정렬 */
  gap: 15px; /* 팀명과 vs 사이 간격 */
}

.team-name {
  font-family: 'Bangers', sans-serif;
  font-size: 1.7rem;
  font-weight: 400;
  color: #beff0a;
}

.vs-text {
  font-family: 'Bangers', sans-serif;
  font-size: 1.5rem;
  font-weight: 500;
  color: #ffa113;
}

.match-details {
  display: flex;
  flex-direction: column;
  align-items: center; /* 세로축 중앙 정렬 */
  font-family: 'Do Hyeon', sans-serif;
  font-size: 30px;
  gap: 5px; /* 세부 사항 간 간격 */
}

.detail-item {
  font-family: 'Do Hyeon', sans-serif;
  font-size: 1.5rem;
  font-weight: 300;
  color: #beff0a;
}




/* 버튼 */

.logout button {
  background-color: #FFDD00;
  /* 기본 배경색 */
  color: #006937;
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
}

.logout button:hover {
  background-color: #fdfdff;
  /* 호버 시 배경색 변경 */
  transform: translateY(-2px);
  /* 마우스 오버 시 살짝 위로 이동 */
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.2);
  /* 그림자 더 강하게 */
}

.logout button:active {
  transform: translateY(2px);
  /* 클릭 시 버튼 살짝 눌림 효과 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /* 그림자 줄이기 */
}

/* 버튼 내부 애니메이션 */
.logout button::after {
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

.logout button:hover::after {
  left: 100%;
  /* 마우스 오버 시 흰색 레이어가 지나가는 애니메이션 */
}

button {
  background-color: #FFDD00;
  /* 버튼 배경색 */
  color: #006937;
  /* 텍스트 색상 */
  border: none;
  /* 테두리 제거 */
  border-radius: 3px;
  /* 둥근 모서리 */
  padding: 7px 10px;
  /* 내부 여백 */
  font-size: 1rem;
  /* 글꼴 크기 */
  font-weight: bold;
  /* 글자 강조 */
  cursor: pointer;
  /* 클릭 가능한 커서 */
  transition: all 0.3s ease;
  /* 부드러운 전환 효과 */
  margin-left: 235px;
}

button:hover {
  background-color: #ffe400;
  /* 호버 시 배경색 */
  color: #004d1a;
  /* 호버 시 텍스트 색상 */
  transform: translateY(-2px);
  /* 살짝 위로 이동 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  /* 호버 시 그림자 효과 */
}

button:active {
  background-color: #ccbb00;
  /* 클릭 시 배경색 */
  transform: translateY(0);
  /* 클릭 시 원래 위치로 */
  box-shadow: none;
  /* 클릭 시 그림자 제거 */
}

/* 버튼 */

.match-label {
  color: #ffa113
}

.match-value1 {
  font-family: 'Bangers', sans-serif;
  font-size: 1.7rem;
  font-weight: 100;
}

</style>