<template>
  <div>
    <!-- 모달을 여는 버튼 -->
    <div class="container">
      <button type="button" class="btn btn-primary2" data-bs-toggle="modal" data-bs-target="#exampleModal">
        <img src="@/assets/매치버튼.png" class="logo" />
      </button>
    </div>

    <!-- 모달 -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">FORM</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <!-- 모달 내용 -->
          <div class="modal-body">
            <!-- 경고 메시지 -->
            <p v-if="userTeams.length === 0" class="alert alert-warning">
              You are not a member of any team. Please join a team to create a match.
            </p>
            <form v-else @submit.prevent="handleMatch">
              <!-- 전체 그리드 레이아웃 -->
              <div class="form-grid">
                <!-- 왼쪽 그룹 -->
                <div class="form-group-left">
                  <div class="form-group">
                    <label for="team">Select Your Team</label>
                    <select id="team" v-model="match.teamId" class="form-select" required>
                      <option v-for="team in userTeams" :key="team.teamId" :value="team.teamId">
                        {{ team.teamName }}
                      </option>
                    </select>
                  </div>
                  <div class="form-group">
                    
                    <label for="sportsType">Sports Type</label>
                    <div class="ta1">
                    <select id="sportsType" v-model="match.sportsType" class="form-select" required>
                      <option v-for="sport in sportsList" :key="sport" :value="sport">
                        {{ sport.name }}
                      </option>
                    </select>
                    </div>
                  </div>

                  <div class="hight" v-if="match.sportsType">
                    <img class="sports-image" :src="getSportImage(match.sportsType)" :alt="match.sportsType.name" />
                  </div>

                </div>

                <!-- 오른쪽 그룹 -->
                <div class="form-group-right">
                  <div class="form-group">
                    <label for="date">Select Date</label>
                    <input type="date" id="date" class="form-control" v-model="selectedDate" required />
                  </div>
                  <div class="form-group">
                    <label for="time">Select Time</label>
                    <select id="time" v-model="selectedTime" class="form-select" required>
                      <option v-for="time in availableTimes" :key="time" :value="time">
                        {{ time }}
                      </option>
                    </select>
                  </div>
                </div>

                <!-- 가운데 Location -->
                <div class="form-group-center">
                  <label for="location">Location</label>
                  <div class="ta2">
                  <select id="location" v-model="match.location" class="form-select" required>
                    <option v-if="!match.sportsType" disabled>
                      종목을 먼저 선택해주세요.
                    </option>
                    <option v-for="location in locationList" :key="location" :value="location">
                      {{ location }}
                    </option>
                  </select>
                </div>
                </div>
              </div>

              <!-- 제출 버튼 -->
              <div class="form-submit">
                <button type="submit" class="btn btn-primary">Match Start!</button>
              </div>
            </form>
            <div v-if="matchResultMessage" class="alert alert-info mt-3" role="alert">
              {{ matchResultMessage }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useMatchStore } from '@/stores/match';
import { useUserStore } from '@/stores/user';
import { useSessionStore } from '@/stores/session';
import axios from 'axios';

// Match 데이터
const match = ref({
  teamId: '',
  sportsType: '',
  location: '',
  scheduledTime: '',
});

//스포츠 타입에 관한 이미지 

const sportsList = ref([
  { name: "축구", image: "src/assets/soccer.png" },
  { name: "풋살", image: "src/assets/futsal.png" },
  { name: "족구", image: "src/assets/volley.png" },
]);

const getSportImage = (sportsType) => {
  const sport = sportsList.value.find((sport) => sport.name === sportsType.name);
  return sport.image;
};

// 유저 관련 Store 사용
const userStore = useUserStore();
const sessionStore = useSessionStore();
const matchStore = useMatchStore();

const userTeams = ref([]); // 유저가 속한 팀 리스트

// 유저 팀 리스트 로드
const loadUserTeams = async () => {
  await sessionStore.checkSession(); // 현재 유저 인증 상태 확인
  if (sessionStore.isAuthenticated) {
    const userId = sessionStore.currentUser.userId;
    await userStore.fetchUserTeams(userId); // Store에서 팀 리스트 가져오기
    userTeams.value = userStore.userTeams;
  }
};

onMounted(() => {
  loadUserTeams();
});

// Sports List 정의
// const sportsList = ['축구', '풋살', '족구'];

// Location 목록 정의
const locations = {
  축구: ['노량진 축구장', '반포종합운동장 축구장', '고척스카이돔 축구장'],
  풋살: ['응봉풋살장', '고척스카이돔 풋살구장', '망원유수지 풋살구장'],
  족구: ['응봉족구장', '아차산배수지체육공원 족구장', '중랑물재생센터 족구장'],
};

// 동적 Location List
const locationList = computed(() => {
  return locations[match.value.sportsType.name] || [];
});

// 날짜 및 시간 상태 관리
const selectedDate = ref('');
const selectedTime = ref('');

// `watch`로 selectedDate와 selectedTime을 감지하여 scheduledTime 업데이트
watch([selectedDate, selectedTime], ([newDate, newTime]) => {
  if (newDate && newTime) {
    match.value.scheduledTime = `${newDate}T${newTime}`;
  } else {
    match.value.scheduledTime = '';
  }
});

// 30분 단위로 시간 목록 생성
const generateTimeSlots = () => {
  const times = [];
  const start = new Date();
  start.setHours(0, 0, 0, 0); // 자정으로 초기화
  for (let i = 0; i < 48; i++) { // 하루를 30분씩 나눔
    const hours = start.getHours().toString().padStart(2, '0');
    const minutes = start.getMinutes().toString().padStart(2, '0');
    times.push(`${hours}:${minutes}`);
    start.setMinutes(start.getMinutes() + 30); // 30분 추가
  }
  return times;
};

const availableTimes = ref(generateTimeSlots());

const matchResultMessage = ref(''); // 결과 메시지를 저장하는 상태 변수

// Match 판단 및 생성/업데이트
// 메서드 작동 방법
// 1. 같은 조건의 매칭을 찾은 후 매칭이 존재할 경우
// 1-1. 존재하는 매칭을 모두 탐색하며 매치가 성사되지 않은 경우를 찾았을 때
// 1-1-1. 본인 팀이 신청한 매치이면 메시지 출력 후 메서드 끝
// 1-1-2. 다른 팀이 신청한 매치이면 매치를 업데이트하여 매칭 성공
// 1-2. 이미 모든 매치가 성사되어 있으면 새로운 매치 생성
// 2. 같은 조건의 매칭이 없는 경우 새로운 매치 생성
const handleMatch = async function () {
  try {
    const response = await axios.get('http://localhost:8080/api/matches/check', {
      params: {
        sportsType: match.value.sportsType.name,
        location: match.value.location,
        scheduledTime: match.value.scheduledTime,
      },
    });

    const matches = response.data;

    if (matches && matches.length > 0) {
      let team2IsEmpty = false;

      for (const existingMatch of matches) {
        if (existingMatch.team2Id == 0) { // null이 아닌 0으로 두는 것은 dto에서 team2Id를 int로 설정하여 db에 값이 존재하지 않으면(null이면) vue로 0을 보내기 때문.
          // console.log('여기1')
          team2IsEmpty = true;
          if (existingMatch.team1Id == match.value.teamId) { // 동일 팀이 이미 요청한 경우 -> 메시지만 띄움
            // matchResultMessage.value = 'Your team has already requested a match with the same conditions.';
            matchResultMessage.value = '팀이 이미 같은 조건으로 매칭을 요청 중입니다.';
            // console.log('여기2')
            break;
          } else {  // 기존 매치의 상대가 없는 경우 -> 매치를 업데이트하여 매칭 성공
            matchStore.updateMatch(existingMatch.matchId, match.value.teamId);
            // matchResultMessage.value = 'Match has been successfully completed! You are now matched with another team.';
            window.location.reload();
            alert('같은 조건의 상대 팀과 매칭이 성사되었습니다.');
            // console.log('여기3')
            break;
          }
        }
      }
      if (!team2IsEmpty) { // 조건에 맞는 매치들을 모두 탐색했지만 team2가 비어있지 않은 경우 (조건에 맞는 매치들이 모두 성사된 매치인 경우) -> 새로운 매치 생성 (구장의 예약여부는 이 사이트에서 관여하지 않음)
        const newMatch = ref({
          teamId: match.value.teamId,
          sportsType: match.value.sportsType.name,
          location: match.value.location,
          scheduledTime: match.value.scheduledTime,
        });
        matchStore.createMatch(newMatch.value);
        // matchResultMessage.value = 'Match successfully created! Waiting for an opponent.';
        window.location.reload();
        alert('새로운 매치가 생성되었습니다. 같은 조건의 상대를 기다립니다.');
        // console.log('여기4')
      }
    } else {  // 매칭이 존재하지 않음 -> 생성
      const newMatch = ref({
          teamId: match.value.teamId,
          sportsType: match.value.sportsType.name,
          location: match.value.location,
          scheduledTime: match.value.scheduledTime,
        });
      matchStore.createMatch(newMatch.value);
      // matchResultMessage.value = 'Match successfully created! Waiting for an opponent.';
      window.location.reload();
      alert('새로운 매치가 생성되었습니다. 같은 조건의 상대를 기다립니다.');
      // console.log('여기5')
    }
  } catch (error) {
    console.error('Error checking or creating/updating match:', error);
  }
};
</script>

<style scoped>
.sports-image {
  width: 200px;
}
/* ------------------셀렉트 ----------------------*/
.sports-select {
  font-family: 'Tenada', sans-serif; /* 원하는 한글 전용 폰트 */
  font-size: 1rem; /* 폰트 크기 */
  color: #ffd700; /* 텍스트 색상 */
  background-color: rgba(0, 0, 0, 0.422); /* 배경색 */
  border: 0px solid #ffd700; /* 테두리 색상 */
  border-radius: 0px; /* 모서리 둥글게 설정 */
  appearance: none; /* 기본 브라우저 스타일 제거 */
}

.form-select {
  width: 100%;
  /* select의 너비를 부모 컨테이너에 맞춤 */
  padding: 10px;
  /* 내부 여백 */
  font-size: 1rem;
  /* 글꼴 크기 */
  font-family: 'Bangers', sans-serif;
  /* 글꼴 */
  color: #ffd700;
  /* 텍스트 색상 */
  background-color: rgba(0, 0, 0, 0.422);
  /* 배경색 (어두운 녹색) */
  border: 0px solid #ffd700;
  /* 테두리 색상 */
  border-radius: 0px;
  /* 둥근 모서리 */
  appearance: none;
  /* 기본 브라우저 스타일 제거 */
  cursor: pointer;
  /* 클릭 가능한 커서 */
  outline: none;
  /* 포커스 테두리 제거 */
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
  /* 애니메이션 효과 */
}


/* hover 상태 스타일 */
.form-select:hover {
  border-color: #ffff00;
  /* 테두리 색상 변경 */
}

/* 포커스 상태 스타일 */
.form-select:focus {
  box-shadow: 0 0 5px #adadad;
  /* 포커스 시 그림자 효과 */
  outline: none;
  /* 포커스 아웃라인 제거 */
}

/* 옵션 드롭다운 스타일 */
.form-select option {
  background-color: #006937;
  /* 옵션 배경색 */
  color: #ffd700;
  /* 옵션 텍스트 색상 */
  padding: 10px;
  /* 내부 여백 */
}

/* 비활성 옵션 */
.form-select option[disabled] {
  color: #999999;
  /* 비활성화된 옵션 색상 */
}

/* 데이트 인풋 */

input[type="date"] {
  -webkit-appearance: none;
  /* 웹킷 기반 브라우저 (Chrome, Safari 등) 기본 스타일 제거 */
  -moz-appearance: none;
  /* Firefox 기본 스타일 제거 */
  appearance: none;
  /* 공통 기본 스타일 제거 */
  border-radius: 0px;
  /* 모서리 둥글게 설정 */
  padding: 10px;
  /* 내부 여백 */

  font-family: "Do Hyeon", sans-serif;
  
  font-size: 1rem;
  /* 글꼴 크기 */
  background-color: rgba(0, 0, 0, 0.422);
  /* 배경색 */
  color: #ffd700;
  /* 텍스트 색상 */
  border: 0px solid #ffd700;
  /* 테두리 색상 */
  outline: none;
  /* 기본 포커스 스타일 제거 */
  width: 100%;
  /* 부모 너비에 맞춤 */
  box-sizing: border-box;
  /* 패딩 포함 크기 계산 */
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
  /* 부드러운 전환 효과 */
}

/* hover 상태 스타일 */
input[type="date"]:hover {
  border-color: #ffff00;
  /* 테두리 색상 변경 */
}

/* 포커스 상태 스타일 */
input[type="date"]:focus {
  border-color: #ffff00;
  /* 포커스 시 테두리 색상 변경 */
  box-shadow: 0 0 5px #ffd700;
  /* 포커스 시 그림자 효과 */
  outline: none;
  /* 기본 아웃라인 제거 */
}

.form-grid {
  display: grid;
  grid-template-areas:
    "left right"
    "center center";
  /* 아래 중앙 Location */
  gap: 20px;
  /* 필드 간 간격 */
  margin: 20px 0;
}

/* 왼쪽 필드 */
.form-group-left {
  grid-area: left;
  /* 그리드 왼쪽 */
  display: flex;
  flex-direction: column;
  /* 위아래 정렬 */
  gap: 60px;
  /* 필드 간 간격 */
}

/* 오른쪽 필드 */
.form-group-right {
  grid-area: right;
  /* 그리드 오른쪽 */
  display: flex;
  flex-direction: column;
  /* 위아래 정렬 */
  gap: 60px;
  /* 필드 간 간격 */
}

/* 가운데 Location 필드 */
.form-group-center {
  grid-area: center;
  /* 그리드 중앙 */
  text-align: center;
  margin-top: 20px;
}

.ta2{
  display: flex;
  justify-content: center;
}

/*-------------------- 셀렉트---------------------------- */


.btn-primary2 {
  background-color: transparent !important;
  /* 버튼 배경을 투명으로 설정 */
  border: none;
  /* 테두리를 제거 (선택 사항) */
  box-shadow: none;
  /* 그림자 제거 (선택 사항) */
}

.btn-primary2:hover {
  background-color: rgba(0, 0, 0, 0.1) !important;
  /* 호버 시 약간의 투명 배경 추가 (선택 사항) */
}

.container {
  display: flex;
  /* Flexbox 활성화 */
  justify-content: center;
  /* 수평 중앙 정렬 */
  align-items: center;
  /* 수직 중앙 정렬 */
  height: 100vh;
  /* 화면 전체 높이 */
  width: 100%;
  /* 화면 전체 너비 */
}

.custom-btn {
  padding: 5px 10px;
}

.logo {
  width: 600px;
  height: auto;
}

/* 모달 */

h5 {
  font-family: 'Bangers', sans-serif;
  font-size: 30px;
  margin: auto;

}

/* 전체 모달 배경색 */
.modal-content {
  background-color: #006937;
  /* 녹색 */
  color: #FFDD00;
  /* 노란색 */
  font-family: 'Bangers', sans-serif;
  /* 폰트 */
  border-radius: 0px;
  /* 둥근 모서리 */
}
.modal-header {
  display: flex;
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
  text-align: center;
}

.modal-header .btn-close {
z-index: 1;
position:absolute;
margin-right: -85%;

}
/* 제목 스타일 */
.modal-title {
  font-size: 2rem;
  text-align: center;

}

/* 그리드 레이아웃 */
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  /* 좌우 2열 */
  gap: 20px;
  /* 좌우 간격 */
  margin-top: 20px;
}

/* 공통 입력 필드 스타일 */
.form-group {
  display: flex;
  flex-direction: column;
  /* 위아래로 정렬 */
  gap: 10px;
  /* 레이블과 입력 필드 간격 */
}

.hight{
  height: 78px;
}
label {
  font-size: 1.2rem;
}

input,
textarea {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  color: #ffd700;
  /* 텍스트 색상 */
  background-color: rgba(0, 0, 0, 0.422);
  /* 투명 배경 */
  border: 0px solid #ffd700;
  /* 노란색 테두리 */
  border-radius: 5px;
  /* 둥근 모서리 */
}

/* 입력 필드 포커스 스타일 */
input:focus,
textarea:focus {
  outline: none;
  /* 기본 포커스 제거 */
  border-color: #ffff00;
  /* 포커스 시 밝은 노란색 */
  box-shadow: 0 0 5px #ffd700;
  /* 포커스 시 그림자 */
}

/* 제출 버튼 */
.form-submit {
  text-align: center;
  /* 버튼 중앙 정렬 */
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  font-size: 1.2rem;
  font-weight: bold;
  background-color: #ffd700;
  /* 노란색 */
  color: #006400;
  /* 녹색 */
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
}

button:hover {
  background-color: #ffbb00;
  /* 밝은 노란색 */
  color: #003300;
  /* 어두운 녹색 */
}

#location {
  width: 220px; /* 원하는 너비 설정 */
  padding: 11px; /* 내부 여백 조정 */
  font-family: "Do Hyeon", sans-serif;
  font-weight: 100;
  font-size: 0.95rem; /* 글자 크기 조정 */
}
#sportsType{
  width: 220px; /* 원하는 너비 설정 */
  padding: 11px; /* 내부 여백 조정 */
  font-family: "Do Hyeon", sans-serif;
  font-weight: 100;
  font-size: 0.95rem; /* 글자 크기 조정 */

}


</style>
