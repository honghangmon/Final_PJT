<template>
  <div>
    <div class="member-container">
      <h4>팀원</h4>
      <ul class="member-list">
        <li v-for="(member, index) in store.members" :key="index">

          <span class="member-nickname">{{ member.nickname }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useTeamStore } from '@/stores/team';
import { useRoute } from 'vue-router';

const store = useTeamStore();
const route = useRoute();

onMounted(() => {
  // 현재 팀 ID를 사용하여 멤버 목록 가져오기
  store.getMemberList(route.params.id);
});
</script>

<style scoped>
/* 컨테이너 스타일 */
.member-container {
  max-width: 800px;
  margin: 30px auto;

  padding: 20px;
  text-align: center;
}

/* 헤더 스타일 */
.member-container h4 {
  background: #8AAAE5; /* 헤더의 그라데이션 */
  color: #fff; /* 텍스트 색상 */
  font-weight: bold;
  font-size: 1.1rem;
  padding: 12px 8px; /* 적당한 여백 */
  text-transform: uppercase; /* 대문자 스타일 */
  text-align: center; /* 가운데 정렬 */
}

/* 리스트 스타일 */
.member-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

/* 리스트 항목 스타일 */
.member-list li {
  padding: 12px 8px;
  color: #8AAAE5; /* 텍스트 색상 */
  font-size: 0.95rem;
  font-weight: 500;
  display: flex;
  justify-content: center;
  text-align: center; /* 데이터도 가운데 정렬 */
  border-bottom: 1px solid rgba(0, 0, 0, 0.1); /* 각 행 구분 */
  background: rgba(255, 255, 255, 0.8); /* 반투명 배경 */
}

/* Hover 효과 */
.member-list li:hover {
  background: linear-gradient(120deg, #bacef2, #c3d0e8);
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
}

/* 인덱스 스타일 */
.member-index {
  font-weight: bold;
  color: #8AAAE5;
  font-size: 1rem;

}

/* 닉네임 스타일 */
.member-nickname {
  font-size: 1rem;
  font-weight: 500;
  font-family: 'Inter', sans-serif;
  color: #444;
  text-align: right;
  margin-left: 10px;
}
</style>
