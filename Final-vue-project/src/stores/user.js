import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/api/users`

export const useUserStore = defineStore('user', () => {

  const user = ref(null);
  const isAuthenticated = ref(false);
  const message = ref('');
  const userTeams = ref([]);

  // 로그인
  const login = async (userName, password) => {
    try {
      const response = await axios.post(`${REST_API_URL}/login`, {
        userName,
        password,
      });
      user.value = response.data.user; // 사용자 정보 저장
      isAuthenticated.value = true;
      // message.value = response.data.message; // 성공 메시지 -> 얘 있으면 main으로 옮겨지지 않을 때 로그인이 성공하여도 error메시지가 나올 수 있음 (Login컴포넌트에서 message가 있고 없고로 에러 판단하기 때문)
      await router.push({ name: 'main' }); // 로그인 후 메인 페이지로 이동
      window.location.reload()
    } catch (error) {
      if (error.response) {
        message.value = error.response.data.message;
      } else {
        message.value = 'An error occurred while logging in.';
      }
      isAuthenticated.value = false;
      // throw error; // 이거 안해주면 상위 컴포넌트인 user.js에서 try-catch 블록이 정상적으로 작동하지 않음. 근데 이제 왜 안 되는지 알았으니까 굳이 error를 throw하지 않고 상위 컴포넌트에선 try-catch 안 쓰고 여기서만 try-catch 쓸 예정
    }
  };

  // 로그아웃
  const logout = function () {
    axios({
      url: `${REST_API_URL}/logout`,
      method: 'POST',
    })
      .then(() => {
        console.log("로그아웃 완료")
        // isAuthenticated.value = false; // 로그아웃 성공 후 인증 상태 업데이트 -> 였는데 session.js에서 인증 상태 관리할 거라 의미 없을 듯?
        user.value = null; // 사용자 정보 초기화 (필요 시)
        router.push({ name: 'home' })
      })
      .catch(() => {
        console.log("로그아웃 실패")
      })
  }
  
  // 회원가입
  const signUp = function (user) {
    axios({
      url: `${REST_API_URL}/signup`,
      method: 'POST',
      data: user,
    })
    .then(() => {
      console.log("회원가입 완료")
      alert('회원가입에 성공했습니다!')
      router.push({ name: 'home' })
    })
    .catch(() => {
      console.log("회원가입 실패")
      alert('회원가입에 실패하였습니다.')
    })
  }

  // 유저 업데이트
  const updateUser = (async(userId, userData) => {
    await axios.put(`${REST_API_URL}/${userId}`, userData)
    .then(() => {
      console.log("유저 업데이트 완료")
      alert('사용자 정보가 성공적으로 수정되었습니다.')
      router.push({ name: 'myPage' })
    })
    .catch(() => {
      console.log("유저 업데이트 실패")
      alert('입력한 닉네임 또는 전화번호가 이미 사용 중입니다.');
    })
  })

  // 회원 탈퇴
  const deleteUser = async(userId) => {
    try {
      await axios.delete(`${REST_API_URL}/${userId}`);
      console.log("회원 탈퇴 완료")
    } catch (error) {
      console.error(error);
    }
  }
  
  // 유저가 속한 팀 리스트 조회
  const fetchUserTeams = async function(userId) {
    await axios.get(`${REST_API_URL}/${userId}/teams`)
    .then((response)=>{
      userTeams.value=response.data
    })
    .catch(()=>{
      console.log('불러오기 실패')
    })
  }

  return { user, isAuthenticated, message, login, signUp, logout, updateUser, deleteUser, userTeams, fetchUserTeams }
})
