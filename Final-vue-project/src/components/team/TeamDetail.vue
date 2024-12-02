<template>
  <div class="page-background">
  <nav>
    <div class="box">
    <div class="container">
      <RouterLink :to="{name: 'main'}">
      <img src="@/assets/color22.png" alt="Dash Logo" class="logo" />
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

  <div class="page-container">
    <div class="detail">
    <h4>Team Information</h4>
    <p>팀 이름: {{ store.team.teamName }}</p>
    <p>팀장: {{ store.userMappings[store.team.leaderId] }}</p>
    <div class="button-container">
    <button class="btn" @click="deleteTeam">삭제</button>
    <button class="btn" @click="goToUpdatePage">수정</button>
  </div> 
  </div>
    
    <div class="member">
      <h5>team  Members</h5>
    <TeamMemberList />
    <div class="button-container2">
    <button class="btn" @click="joinTeam">팀 참가</button>
    <button class="btn" @click="leaveTeam">팀 탈퇴</button>
    </div>
  </div>
</div>
</div>
</template>

<script setup>
import { useTeamStore } from '@/stores/team';
import { onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import TeamMemberList from './TeamMemberList.vue';
import { useSessionStore } from '@/stores/session';
import TheHeaderNav from '../common/TheHeaderNav.vue';

const store = useTeamStore()

const route = useRoute();
const router = useRouter();


onMounted(() => {
  store.getTeam(route.params.id)
  store.fetchUserMappings()
})

const deleteTeam = () => {
  const sessionStore = useSessionStore(); // 세션 스토어 초기화
  const currentUser = sessionStore.currentUser;
  if (currentUser.userId == store.team.leaderId) {
    store.deleteTeam(route.params.id);
    alert("팀 삭제에 성공했습니다!");
  } else {
    alert("팀 리더만 삭제가 가능합니다.");
  }
};

// 수정 기능
const goToUpdatePage = () => {
  const sessionStore = useSessionStore(); // 세션 스토어 초기화
  const currentUser = sessionStore.currentUser;
  if (currentUser.userId == store.team.leaderId) {
    router.push(`/team-update/${route.params.id}`);
  } else {
    alert("팀 리더만 수정이 가능합니다.");
  }
};

const joinTeam = () => {
  const sessionStore = useSessionStore(); // 세션 스토어 사용
  const currentUser = sessionStore.currentUser;

  const user = { userId: currentUser.userId }; // 현재 사용자의 userId 추출

  store.joinTeam(route.params.id, user)
    .then(() => {
      store.getMemberList(route.params.id); // 멤버 목록 갱신
      alert("팀 참가에 성공했습니다!");
      console.log("팀 참가에 성공했습니다.");
    })
    .catch((error) => {
      alert("이미 팀에 속해있습니다.");
      console.error("팀 참가 실패:", error.response?.data || error.message);
    });
};

const leaveTeam = () => {
  const sessionStore = useSessionStore(); // 세션 스토어 초기화
  const currentUser = sessionStore.currentUser;

  if (currentUser.userId != store.team.leaderId) {
    store.leaveTeam(route.params.id, currentUser.userId)
      .then(() => {
        store.getMemberList(route.params.id); // 팀 멤버 목록 갱신
        console.log("팀 탈퇴에 성공했습니다.");
        alert("팀 탈퇴에 성공했습니다!");
        window.location.reload();
      })
      .catch((error) => {
        console.error("팀 탈퇴 실패:", error.response?.data || error.message);
        alert("팀 탈퇴에 실패했습니다.");
      });
  } else {
    alert("팀 리더는 탈퇴가 불가능합니다.");
  }


};

// const joinTeam = async () => {
//   const user = useSessionStore.currentUser; // 실제 사용자 ID로 교체
//   try {
//     await store.joinTeam(route.params.id, user.userId);
//     store.getMemberList(route.params.id); // 멤버 목록 갱신
//   } catch (error) {
//     console.error("팀 참가 중 오류:", error);
//   }
// };

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
  background-color: #8AAAE5;
  /* 박스 배경색 */
  color: #F1FBF7;
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
  background-color: #8AAAE5;
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
  
  color: #8AAAE5;
  /* 네비게이션 텍스트 색상 */
}

::v-deep(nav a:hover) {
  color: #96f1f3;
  /* 호버 시 색상 */
}


.page-container {
  display: flex; /* flexbox 활성화 */
  justify-content: space-between; /* 양쪽으로 배치 */
  align-items: flex-start; /* 세로 축 정렬: 위쪽 기준 */
  font-family: 'Inter', sans-serif;
  font-size: 1.5rem;
  font-weight: bold;
  color: #444;
  gap: 20px; /* 두 박스 사이 간격 */
  padding: 20px;
}

/* 왼쪽에 위치할 detail 클래스 */
.detail {
  flex: 1; /* 비율에 따라 너비 조정 */
  max-width: 45%; /* 최대 너비 제한 */
  background: #f9f9f9; /* 연한 배경 */
  border: 1px solid #ddd; /* 테두리 */
  border-radius: 8px; /* 둥근 모서리 */
  padding: 15px; /* 내부 여백 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 */
}

/* 오른쪽에 위치할 member 클래스 */
.member {
  flex: 1; /* 비율에 따라 너비 조정 */
  max-width: 45%; /* 최대 너비 제한 */
  background: #f9f9f9; /* 연한 배경 */
  border: 1px solid #ddd; /* 테두리 */
  border-radius: 8px; /* 둥근 모서리 */
  padding: 15px; /* 내부 여백 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 */
}

/* 공통 헤딩 스타일 */
.detail h3,
.member h3 {
  font-family: 'Bangers', sans-serif;
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #990012;
}

h4,h5{
  font-family: 'Bangers', sans-serif;
  font-size: 2rem;
  color: #8AAAE5;
  text-align: center;
}

/* 버튼 스타일 */
.btn {
  display: inline-block;
  padding: 10px 20px; /* 내부 여백 */
  font-size: 1rem; /* 글씨 크기 */
  font-weight: bold; /* 글씨 강조 */
  font-family: 'Inter', sans-serif; /* 글씨체 */
  color: #ffffff; /* 텍스트 색상 */
  background-color: #8AAAE5; /* 기본 배경색 */
  border: none; /* 테두리 제거 */
  border-radius: 8px; /* 둥근 모서리 */
  cursor: pointer; /* 클릭 가능한 커서 */
  transition: all 0.3s ease; /* 부드러운 애니메이션 효과 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  text-align: center;
  margin: 10px; /* 버튼 간 간격 */
}

/* Hover 효과 */
.btn:hover {
  background-color: #729fd1; /* Hover 시 배경색 */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* 더 강한 그림자 효과 */
  transform: translateY(-2px); /* 살짝 떠오르는 효과 */
}

/* Active 상태 (클릭 시 효과) */
.btn:active {
  background-color: #5e85b4; /* 클릭 시 배경색 */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15); /* 그림자 축소 */
  transform: translateY(2px); /* 눌리는 효과 */
}

/* 비활성화 상태 */
.btn:disabled {
  background-color: #cccccc; /* 비활성화된 배경색 */
  color: #666666; /* 비활성화된 텍스트 색상 */
  cursor: not-allowed; /* 클릭 불가 커서 */
  box-shadow: none; /* 그림자 제거 */
}

/* 버튼 컨테이너 스타일 */
.button-container {
  display: flex;
  flex-direction: row; /* 버튼을 가로로 배치 */
  justify-content: center; /* 버튼들을 가로로 중앙 정렬 */
  align-items: center; /* 세로 축으로 중앙 정렬 */
  flex-wrap: wrap; /* 버튼이 화면 크기에 따라 줄바꿈 가능 */
  margin-top: 20px; /* 컨테이너 상단 여백 */
}

.button-container2 {
  display: flex;
  flex-direction: row; /* 버튼을 가로로 배치 */
  justify-content: center; /* 버튼들을 가로로 중앙 정렬 */
  align-items: center; /* 세로 축으로 중앙 정렬 */
  flex-wrap: wrap; /* 버튼이 화면 크기에 따라 줄바꿈 가능 */
  margin-top: 20px; /* 컨테이너 상단 여백 */
}
</style>