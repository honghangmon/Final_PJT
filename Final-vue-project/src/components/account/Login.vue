<template>
  <div class="login-page">
    <div class="rectangle">
      <!-- <div class="content"> -->
      <!-- 로고 -->
      <div class="logo-container">
        <img src="@/assets/123.png" alt="Dash Logo" class="logo" />
      </div>
      <!-- 로그인 박스 -->
      <div class="box">
        <div class="login-box">
          <div class= "titlebox">
          <h1 class="title">DASH</h1>
          <div class="ball">
          <img src="@/assets/Vector.svg"/>
        </div>
        </div>
          <form @submit.prevent="handleLogin">
            <div class="form-group">
              <label for="userName">Name <span class="required">*</span></label>
              <input type="text" id="userName" v-model="userName" placeholder="Enter your name" required />
            </div>
            <div class="form-group">
              <label for="password">Password <span class="required">*</span></label>
              <input type="password" id="password" v-model="password" placeholder="Enter your password" required />
            </div>
            <p v-if="errorMessage" class="error-message">아이디 또는 비밀번호가 잘못되었습니다.</p>
            <button type="submit" class="login-button">JOIN!</button>
          </form>
          <p class="signup-link">
            If you do not have an ID, please click <RouterLink :to="{ name: 'signUp' }">sign up</RouterLink> to register.
          </p>
        </div>
        <p class="footer-message">
          We would like to convey this to those who use our app. First of all, thank
          you for using our app. We will solve your inconveniences and provide you
          with the best convenience.
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>

import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/user'; // Pinia store import

// 로그인 폼 데이터
const userName = ref('');
const password = ref('');

// Pinia store 사용
const store = useUserStore();

// errorMessage를 computed로 관리
const errorMessage = computed(() => store.message);

// 에러 메시지 초기화
onMounted(() => {
  store.message = ''; // 에러 메시지 초기화
});

// 로그인 함수 (try-catch를 써서 두 번 error를 catch할 때 이렇게 씀 -> store에서 error를 throw해줘야 정상 작동)
// const handleLogin = async () => {
//   try {
//     // Pinia store의 login 호출
//     await store.login(userName.value, password.value);

//     // 로그인 성공 시 메시지 초기화 (스토어 상태가 이미 관리되므로 별도로 초기화하지 않아도 됩니다)
//   } catch (error) {
//     // 에러 처리는 store.message로 자동 동기화
//     console.error(store.message);
//   }
// };

// 로그인 함수 (따로 try-catch 하지 않음)
const handleLogin = async () => {
  await store.login(userName.value, password.value);
  if (!store.isAuthenticated) {
    console.error(store.message);
  }
};

</script>

<style scoped>
/* 전체 페이지 배경 스타일 */
.login-page {
  background-image: url('@/assets/back.jpg');
  background-size: cover;
  background-position: center;
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: rgb(0, 0, 0);
  font-family: Arial, sans-serif;
}

.signup-link{
  text-align: center;
}

.rectangle {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.59);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

/* .content {
  z-index: 1; /* 검은색 사각형 위에 표시 */
/* } */



.box {
  z-index: 1;
  transform: translate3d(0, -230px, 0);
  margin: 0 38%;
}

.titlebox{
  display: flex;
  align-items: center;
  justify-content: center;
}

.ball{
  display:flex; 
  align-items: center;
  margin-left: 10px;
}

/* 로고 스타일 */
.logo-container {
  margin-bottom: 10px;
}

.logo {
  width: 950px;
  /* 로고 크기 */
  transform: translate3d(0, 100px, 0);
  height: auto;
}

.required {
  color: rgb(0, 166, 255);
  /* 원하는 색상 */
  font-weight: bold;
  /* 선택 사항: 강조 */
}

/* 로그인 박스 스타일 */
.login-box {
  background-color: rgba(0, 0, 0, 0.8);
  /* 반투명 검은 배경 */
  font-family: 'Inter', sans-serif;
  padding: 20px 30px;
  border-radius: 0px;
  text-align: left;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5);
  z-index: 2;
}

.title {
  font-family: 'Bangers', sans-serif;
  font-size: 50px;
  font-weight: normal;
  text-align: center;
  color: white;
}

/* 폼 그룹 스타일 */
.form-group {
  margin-bottom: 15px;
}


label {
  display: block;
  font-size: 14px;
  margin-bottom: 5px;
  color: white;
}

input {
  color:rgb(251, 251, 251);
  width: 100%;
  padding: 10px;
  font-size: 14px;
  border: 2px solid #ffffff;
  border-radius: 5px;
  background-color: #201e1e;
}

/* 버튼 스타일 */
.login-button {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  background-color: #56AAF7;
  color: white;
  border: none;
  border-radius: 0px;
  cursor: pointer;
  margin-top: 10px;
}

.login-button:hover {
  background-color: #56AAF7;
}

/* 회원가입 링크 스타일 */
.signup-link {
  margin-top: 15px;
  font-size: 14px;
  color: white;
}

.signup-link a {
  color: #007bff;
  text-decoration: underline;
}

/* 푸터 메시지 스타일 */
.footer-message {
  margin-top: 20px;
  text-align: center;
  font-size: 12px;
  color: white;
  max-width: 800px;
}

.error-message {
  color: red;
  margin-top: 10px;
}
</style>
