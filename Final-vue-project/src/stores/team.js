import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/api/teams`

export const useTeamStore = defineStore('team', () => {

  const teamList = ref([])
  const getTeamList = async function () { // async, await 추가했는데 형이 구현한 곳에서 문제 생기면 바꿀 예정
    await axios.get(REST_API_URL)
      .then((response) => {
        teamList.value = response.data
      })
  }

  const userMappings = ref({});
  const fetchUserMappings = () => {
    axios.get(`http://localhost:8080/api/users`)
      .then((response) => {
        const mappings = {};
        response.data.forEach((user) => {
          mappings[user.userId] = user.nickname; // userId -> nickname 매핑
        });
        userMappings.value = mappings;
      })
      .catch((error) => {
        console.error('사용자 매핑 가져오기 실패:', error);
      });
  };

  const createTeam = function (team) {
    axios({
      url: REST_API_URL,
      method: 'POST',
      data: team
    })
      .then(() => {
        alert('팀이 성공적으로 생성되었습니다.');
        router.push({ name: 'teamList' })
      })
      .catch((error) => {
        console.error('팀 생성 중 오류:', error);
        alert('팀 생성에 실패했습니다.');
      })
  }

  const team = ref({})
  const getTeam = async function (id) {
    await axios.get(`${REST_API_URL}/${id}`)
      .then((response) => {
        team.value = response.data
      })
  }

  const members = ref({})
  const getMemberList = function (id) {
    axios.get(`${REST_API_URL}/${id}/members`)
      .then((response) => {
        members.value = response.data
      })
  }
  
  const deleteTeam = function (id) {
    axios.delete(`${REST_API_URL}/${id}`)
      .then(() => {
        router.push({ name: 'teamList' })
      })
  }

  const update = ref({})
  const updateTeamName = (id, updatedData) => {
    return axios.put(`${REST_API_URL}/${id}`, updatedData) // 전체 Team 객체 전송
      .then((response) => {
        console.log("팀 이름 업데이트 성공:", response.data);
        return response.data;
      })
      .catch((error) => {
        console.error("팀 이름 업데이트 실패:", error.response?.data || error.message);
        throw error;
      });
  };

  const joinTeam = function (teamId, user) {
    return axios.post(`${REST_API_URL}/${teamId}/members`, user)
      .then((response) => {
        console.log("팀 참가 성공:", response.data);
        return response.data;
      })
      .catch((error) => {
        console.error("팀 참가 실패:", error.response?.data || error.message);
        throw error;
      });
  };

  const leaveTeam = (teamId, userId) => {
    return axios.delete(`${REST_API_URL}/${teamId}/members/${userId}`)
      .then((response) => {
        console.log("팀 탈퇴 성공:", response.data);
        return response.data;
      })
      .catch((error) => {
        console.error("팀 탈퇴 실패:", error.response?.data || error.message);
        throw error;
      });
  };

  return {
    teamList, getTeamList, team,
    getTeam, deleteTeam,
    getMemberList, members, updateTeamName
    , createTeam, update, userMappings, fetchUserMappings, joinTeam, leaveTeam,
  }
})
