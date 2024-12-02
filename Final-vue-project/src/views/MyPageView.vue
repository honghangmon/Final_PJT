<template>
  <div class="page-background">
    <div class="box">
      <div class="container">
        <RouterLink :to="{ name: 'main' }">
          <img src="@/assets/color12.png" alt="Dash Logo" class="logo" />
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

    <h3>Player</h3>
    <div class="mom">
      <!-- 배경 이미지 -->
      <img class="img" src="@/assets/background.png" />
      <!-- 이미지 위의 콘텐츠 -->
      <div class="content">
        <div class="team">
          <h4>My Team</h4>
          <p v-for="team in userStore.userTeams" :key="team.teamName">
            {{ team.teamName }}
          </p>
        </div>
        <div class="profile">
          <h4 id="pro">Profile</h4>
          <p>프로필 사진을 입력해주세요</p>
        </div>
        <div class="My">
          <div id="in">
            <h4>Information</h4>
          </div>
          <p>닉네임: {{ sessionStore.currentUser.nickname }}</p>
          <p>이메일: {{ sessionStore.currentUser.email }}</p>
          <p>전화번호: {{ sessionStore.currentUser.phoneNumber }}</p>
          <p>나이: {{ sessionStore.currentUser.age }}</p>
          <p>성별: {{ sessionStore.currentUser.gender }}</p>
        </div>
      </div>
    </div>
    <div class="btn-container">
    <button class="btn update-btn" @click="showModal('update')">정보 수정</button>
    <button class="btn delete-btn" @click="showModal('delete')">회원 탈퇴</button>
  </div>


    <PasswordVerify v-if="isModalVisible" :isVisible="isModalVisible" :action="modalAction" @close="closeModal"
      @verified="handleVerification" />
  </div>
</template>

<script setup>
import Logout from "@/components/account/Logout.vue";
import PasswordVerify from "@/components/account/PasswordVerify.vue";
import TheHeaderNav from "@/components/common/TheHeaderNav.vue";
import { useSessionStore } from "@/stores/session";
import { useUserStore } from "@/stores/user";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const sessionStore = useSessionStore();
const router = useRouter();

onMounted(async () => {
  userStore.fetchUserTeams(sessionStore.currentUser.userId);
});

const isModalVisible = ref(false); // 모달 표시 여부
const modalAction = ref(""); // 현재 액션 (update or delete)

const showModal = (action) => {
  modalAction.value = action;
  isModalVisible.value = true;
};

const closeModal = () => {
  isModalVisible.value = false;
};

const handleVerification = async (action) => {
  if (action === "update") {
    router.push({ name: "userUpdate" });
    alert("비밀번호 확인 성공: 정보 수정으로 이동합니다.");
    closeModal();
  } else if (action === "delete") {
    const isConfirmed = window.confirm(
      "리더로 있는 팀도 함께 삭제됩니다. 정말로 탈퇴하시겠습니까?"
    );
    if (isConfirmed) {
      try {
        await userStore.deleteUser(sessionStore.currentUser.userId);
        alert("회원 탈퇴가 완료되었습니다.");
        router.push({ name: "home" });
      } catch (error) {
        alert("회원 탈퇴에 실패했습니다.");
      }
    } else {
      alert("탈퇴를 취소하였습니다.");
      router.push({ name: "myPage" });
    }
    closeModal();
  }
};
</script>

<style scoped>
.page-background {
  background-color: #d4d3f7;
  /* 원하는 배경색 */
  min-height: 100vh;
  /* 화면 전체 높이 */
  padding: 0;
  margin: 0;
}

.logout {
  position: absolute;
  right: 20px;
  top: 4%;
  transform: translateY(-50%);
}

.box {
  width: 100%;
  height: 70px;
  background-color: #5738af;
  color: #312cac;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.divider {
  width: 100%;
  height: 2px;
  background-color: #5738af;
  margin: 20px 0;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100px;
  width: 100px;
}

.logo {
  width: 100px;
}

::v-deep(nav a) {
  color: #5738af;
}

::v-deep(nav a:hover) {
  color: #ffffff;
}

h3 {
  font-family: "Bangers", sans-serif;
  font-size: 3rem;
  color: #5738af;
  margin-left: 15%;
}

.mom {
  position: relative;
  width: 70%;
  height: 400px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  background: #d4d3f7;
  border-radius: 0px;
  box-shadow: 20px 20px 20px 20px rgba(0, 0, 0, 0.1);
  margin: 0 auto;
}

.mom .img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.mom .content {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.content>div {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
  font-family: "Bangers", sans-serif;
  font-size: 1.2rem;
  text-align: center;
  background: rgba(0, 0, 0, 0.6);
  margin: 10px;
  padding: 10px;
  border-radius: 10px;

}

h4 {
  font-family: 'Bangers', sans-serif;
  font-size: 2.5rem;
  color: #5738af;
  text-align: center;
}

#pro {
  font-family: 'Bangers', sans-serif;
  font-size: 2.5rem;
  color: #ffffff;
}

#in {
  display: flex;
  justify-content: center;
  text-align: center;
}

.content {
  display: flex;
  justify-content: space-between;
  /* 가로로 균등 분배 */
  align-items: flex-start;
  /* 상단 정렬 */
  gap: 20px;
  /* 각 클래스 사이 간격 */
}

.mom .content .team {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: rgb(47, 66, 135);
  font-family: "Inter", sans-serif;
  font-weight: bold;
  font-size: 1.2rem;
  text-align: center;
  background: #d4d3f7;
  margin: 10px;
  padding: 10px;
  border-radius: 10px;
  margin-bottom: 10%;
}

.mom .content .profile {

  background-color: #60606093;
  /* 프로필 박스 배경 */
  width: 330px;
  /* 가로 크기 */
  height: 330px;
  /* 세로 크기 */
  border: 1px solid #ddd;
  /* 테두리 */
  border-radius: 50%;
  /* 원 모양 */
  display: flex;
  /* 내부 텍스트 정렬 */
  justify-content: center;
  /* 가로 중앙 정렬 */
  align-items: center;
  /* 세로 중앙 정렬 */
  text-align: center;
  font-family: "Bangers", sans-serif;
  font-size: 1.2rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* 그림자 효과 */
  margin: auto;
  /* 가운데 배치 */
}

.mom .content .My {
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: center;
  color: rgb(47, 66, 135);
  font-family: "Inter", sans-serif;
  font-weight: bold;
  font-size: 1.2rem;
  text-align: center;
  background: #ffffff;
  margin: 10px;
  padding: 10px;

  margin-top: 10%;
  position: relative;
  /* 상대적 위치 설정 */
  left: 6%;
  /* 오른쪽으로 이동 */
}


.btn {
  display: inline-block;
  padding: 10px 20px; /* 여백 */
  font-size: 1rem; /* 글자 크기 */
  font-weight: bold;
  text-transform: uppercase; /* 대문자 변환 */
  color: #ffffff; /* 글자 색 */
  border: none;
  border-radius: 0px; /* 둥근 버튼 */
  cursor: pointer;
  transition: all 0.3s ease; /* 부드러운 효과 */
}

.update-btn {
  background: linear-gradient(45deg, #5738af, #8aaae5); /* 그라데이션 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 그림자 */
}

.update-btn:hover {
  background: linear-gradient(45deg, #312cac, #5738af); /* 호버 시 색상 */
  transform: translateY(-3px); /* 살짝 올라감 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3); /* 호버 시 그림자 */
}

.delete-btn {
  background: linear-gradient(45deg, #d9534f, #c9302c); /* 그라데이션 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 그림자 */
}

.delete-btn:hover {
  background: linear-gradient(45deg, #c9302c, #ac2925); /* 호버 시 색상 */
  transform: translateY(-3px); /* 살짝 올라감 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3); /* 호버 시 그림자 */
}

.btn:active {
  transform: translateY(2px); /* 클릭 시 눌림 효과 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* 클릭 시 그림자 */
}

.btn-container {
  display: flex;
  margin-left: 72%;
  gap: 20px; /* 버튼 간 간격 */
  margin-top: 20px; /* 버튼 위 여백 */
}

</style>
