<template>
  <div class="box">
    <div class="container">
      <img src="@/assets/123.png" alt="Dash Logo" class="logo" />
    </div>
  </div>

  <div class="divider"></div> <!-- 선을 위한 추가 태그 -->

  <h2>create account<img src="@/assets/blue.png" class="ball"></h2>


  <div class="signup-form">
    <form @submit.prevent="signUp">
      <!-- 사용자 이름 입력 -->
      <div class="form-group">
        <label for="userName">Name</label>
        <br>
        <input type="text" id="userName" v-model="user.userName" placeholder="Enter your name" required />
      </div>

      <!-- 비밀번호 입력 -->
      <div class="form-group">
        <label for="password">Password</label>
        <br>
        <input type="password" id="password" v-model="user.password" placeholder="Enter your password" required />
      </div>

      <!-- 닉네임 입력 -->
      <div class="form-group">
        <label for="nickname">Nickname</label>
        <br>
        <input type="text" id="nickname" v-model="user.nickname" placeholder="Enter your nickname" required />
      </div>

      <!-- 이메일 입력 -->
      <div class="form-group">
        <label for="email">Email</label>
        <br>
        <input type="email" id="email" v-model="user.email" placeholder="Enter your email" required />
      </div>

      <!-- 핸드폰 번호 입력 -->
      <div class="form-group">
        <label for="phoneNumber">PhoneNumber</label>
        <div style="display: flex; align-items: center;">
          <input type="text" id="phoneNumber1" v-model="phoneNumber1" placeholder="010" maxlength="3" required /> - 
          <input type="text" id="phoneNumber2" v-model="phoneNumber2" placeholder="1234" maxlength="4" required /> - 
          <input type="text" id="phoneNumber3" v-model="phoneNumber3" placeholder="5678" maxlength="4" required />
        </div>
      </div>

      <!-- 나이 입력 -->
      <div class="form-group">
        <label for="age">Age</label>
        <br>
        <input type="number" id="age" v-model="user.age" placeholder="Enter your age" required />
      </div>

      <!-- 성별 선택 -->
      <!-- <div class="form-group">
        <label for="gender">Gender</label>
        <br>
        <select id="gender" v-model="user.gender" required>
          <option value="male">Male</option>
          <option value="female">Female</option>
        </select>
      </div> -->

      <!-- 성별 선택 -->
      <div class="form-group2">
        <label>Choose your gender</label>
        <br>
        <!-- Male 선택 -->
        <label>
          <input type="radio" name="gender" value="male" v-model="user.gender" />
          Male
        </label>
        <!-- Female 선택 -->
        <label>
          <input type="radio" name="gender" value="female" v-model="user.gender" />
          Female
        </label>
      </div>

      <!-- 회원가입 버튼 -->
      <button type="submit">CREATE</button>
    </form>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore();

const user = ref({
  userName: '',
  password: '',
  nickname: '',
  email: '',
  phoneNumber: '',
  age: '',
  gender: '',
})

const phoneNumber1 = ref('');
const phoneNumber2 = ref('');
const phoneNumber3 = ref('');

watch([phoneNumber1, phoneNumber2, phoneNumber3], ([new1, new2, new3]) => {
  if (new1 && new2 && new3) {
    user.value.phoneNumber = `${new1}-${new2}-${new3}`;
  } else {
    user.value.phoneNumber = '';
  }
});

const signUp = function () {
  store.signUp(user.value);
}
</script>

<style scoped>

.box {
  width: 100%; /* 화면 전체 너비 */
  height: 70px; /* 높이 */
  background-color: #242E73; /* 박스 배경색 */
  color: #ffffff; /* 텍스트 색상 */
  display: flex; /* 수평 및 수직 정렬을 위한 플렉스 박스 */
  align-items: center; /* 수직 가운데 정렬 */
  justify-content: center; /* 수평 가운데 정렬 */
  margin-bottom: 100px;
}

.divider {
  width: 100%; /* 선의 너비 */
  height: 2px; /* 선의 높이 */
  background-color: #001f7d; /* 선의 색상 */
  margin: 20px 0; /* 위아래 간격 */
}

.ball {
  width: 130px;
  margin-left: 20px;

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
.signup-page {
  font-family: 'Arial', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #ffffff;
  color: #000000;
}

h2 {
  font-family: 'Bangers', sans-serif;
  font-size: 100px;
  font-weight: normal;
  margin: auto;
  margin-top:60px;
  margin-bottom: 40px;
  text-align: center;
  color: #242E73;
  transform: rotate(-9deg);
  /* 텍스트를 45도 회전 */
  /* display: inline-block; */
  /* 회전을 위해 inline-block으로 설정 */
}

/* 헤더 스타일 */
.header {
  width: 100%;
  background-color: #242E73;
  color: #ffffff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
}

.logo {
  font-size: 24px;
  font-weight: bold;
}

.match {
  color: #66ccff;
}

.social-icons i {
  font-size: 20px;
  margin: 0 10px;
  cursor: pointer;
}

/* 회원가입 폼 스타일 */
.signup-form {
  width: 100%;
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 10px;
}

.form-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #242E73;
}

.form-group {
  margin-bottom: 15px;
}

.form-group2 {
  font-family: 'Inter', sans-serif;
  margin-top: 30px;
  display: flex;
  margin-bottom: 30px;
}

[type="radio"] {
  vertical-align: middle;
  appearance: none;
  border: max(2px, 0.1em) solid #242E73;
  border-radius: 0%;
  margin: auto;
  width: 1em;
  height: 1em;
  cursor: pointer;
  transition: background-color 0.2s, border-color 0.2s;
}

[type="radio"]:checked {
  background-color: #242E73;
  /* 선택된 상태의 배경색 */
  border-color: #ffffff;
  /* 선택된 상태의 테두리 색 */
}

label {
  font-family: 'Inter', sans-serif;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 0px;
  display: contents;
}

input,
select {
  font-family: 'Inter', sans-serif;
  width: 100%;
  padding: 10px;
  font-size: 14px;
  font-weight: bold;
  border: 3px solid #242E73;
  border-radius: 0px;
  margin-top: 5px;
}

.gender-group {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.gender-group label {
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 5px;
}

button {
  font-family: 'Inter', sans-serif;
  width: 100%;
  padding: 10px;
  background-color: #242E73;
  color: #ffffff;
  border: none;
  border-radius: 0px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
}

button:hover {
  background-color: #4770eb;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .signup-form {
    padding: 15px;
  }

  .form-title {
    font-size: 20px;
  }
}
</style>