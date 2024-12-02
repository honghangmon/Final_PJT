import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/api/matches`

export const useMatchStore = defineStore('match', () => {

  const match = ref({});
  const matchList = ref([]);

  const getAllMatches = async function () {
    await axios({
      url: REST_API_URL,
      method: 'GET',
    })
      .then((response) => {
        matchList.value = response.data;
        // console.log(matchList.value);
      })
  }

  const createMatch = function (match) {
    const matchForCreate = ref({
      team1Id: match.teamId,
      sportsType: match.sportsType,
      location: match.location,
      scheduledTime: match.scheduledTime,
    });
    // console.log(matchForCreate);
    axios({
      url: REST_API_URL,
      method: 'POST',
      data: matchForCreate.value,
    })
      .then(() => {
        console.log('매치 생성 성공!')
      })
  }

  const updateMatch = async (matchId, team2Id) => {
    try {
      const response = await axios.put(`${REST_API_URL}/${matchId}`, {
        team2Id: team2Id, // RequestBody에 포함될 데이터
      });

      if (response.status === 200) {
        console.log('Match updated successfully:', response.data.message);
        return true;
      } else {
        console.error('Failed to update match:', response.data.message);
        return false;
      }
    } catch (error) {
      console.error('Error updating match:', error.response?.data || error.message);
      return false;
    }
  };

  const deleteMatch = async (matchId) => {
    try {
      await axios.delete(`${REST_API_URL}/${matchId}`);
      console.log('매치 삭제 성공!');
      // matchList를 다시 불러오거나, matchList에서 삭제된 matchId를 제거
      // await getAllMatches(); // matchList 업데이트 함수
    } catch (error) {
      console.error('매치 삭제 실패:', error.response?.data || error.message);
    }
  };

  return { match, matchList, getAllMatches, createMatch, updateMatch, deleteMatch }
})
