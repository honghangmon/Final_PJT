import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

axios.defaults.withCredentials = true;

const REST_API_URL = `http://localhost:8080/api`

export const useSessionStore = defineStore('session', ()=> {

  const isAuthenticated = ref(false);
  const currentUser = ref(null);
  
  const checkSession = async () => {
    try {
      const response = await axios.get(`${REST_API_URL}/session`, {
        withCredentials: true, // 세션 쿠키를 포함하여 요청
      });
      isAuthenticated.value = response.data.authenticated;
      currentUser.value = response.data.user;
    } catch (error) {
      isAuthenticated.value = false;
      currentUser.value = null;
    }
  };
  
  return { isAuthenticated, currentUser, checkSession }
})

