<template>
  <div class="modal-overlay" v-if="isVisible">
    <div class="modal-container">
      <h3>비밀번호 확인</h3>
      <form @submit.prevent="verifyPassword">
        <!-- <label for="password">비밀번호:</label> -->
        <input type="password" id="password" v-model="inputPassword" placeholder="Enter your password" required />
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
        <div class="buttons">
          <button type="submit">확인</button>
          <button type="button" @click="closeModal">취소</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useSessionStore } from '@/stores/session';

const props = defineProps({
  isVisible: Boolean, // 모달 표시 여부를 부모에서 전달
  action: String, // update or delete 액션
});

const emit = defineEmits(['close', 'verified']); // 부모로 이벤트 전송

const inputPassword = ref('');
const errorMessage = ref('');
const sessionStore = useSessionStore();

const verifyPassword = async () => {
  const storedPassword = sessionStore.currentUser.password; // 현재 유저 비밀번호 가져오기
  if (storedPassword === inputPassword.value) {
    if (props.action === 'update') {
      emit('verified', 'update'); // 'update' 액션 전달
    } else if (props.action === 'delete') {
      emit('verified', 'delete'); // 'delete' 액션 전달
    }
  } else {
    errorMessage.value = '비밀번호가 일치하지 않습니다.';
  }
};

const closeModal = () => {
  emit('close'); // 모달 닫기
};
</script>

<style scoped>
/* 모달 오버레이 스타일 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6); /* 반투명 배경 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* 모달 컨테이너 스타일 */
.modal-container {
  background-color: #5738AF; /* 배경색 */
  color: white; /* 글자색 */
  padding: 20px;
  border-radius: 10px;
  width: 300px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2); /* 그림자 */
  text-align: center;
}

/* 인풋 필드 스타일 */
input[type="password"] {
  width: 100%; /* 전체 너비 */
  padding: 10px; /* 내부 여백 */
  margin-top: 10px;
  margin-bottom: 10px; /* 아래 여백 */
  border: 1px solid #ffffff; /* 테두리 */
  border-radius: 5px; /* 모서리 둥글게 */
  background-color: #ffffff; /* 흰 배경 */
  color: #242E73; /* 텍스트 색상 */
}

/* 버튼 공통 스타일 */
button {
  padding: 10px 20px; /* 내부 여백 */
  border: none; /* 테두리 제거 */
  border-radius: 5px; /* 모서리 둥글게 */
  font-size: 1rem; /* 글자 크기 */
  font-weight: bold; /* 글자 두께 */
  cursor: pointer; /* 클릭 커서 */
  transition: all 0.3s ease; /* 전환 효과 */
}

/* 확인 버튼 스타일 */
button[type="submit"] {
  background-color: white; /* 흰 배경 */
  color: #5738AF; /* 파란 글자 */
}

button[type="submit"]:hover {
  background-color: #d3d3d3; /* 회색 배경 */
  color: #5738AF; /* 검은 글자 */
}

/* 취소 버튼 스타일 */
button:not([type="submit"]) {
  background-color: transparent; /* 투명 배경 */
  color: #ffffff; /* 흰 글자 */
  border: 1px solid #ffffff; /* 흰 테두리 */
}

button:not([type="submit"]):hover {
  background-color: #ff6347; /* 빨간 배경 */
  color: #ffffff; /* 흰 글자 */
}

/* 버튼 컨테이너 */
.buttons {
  display: flex; /* 플렉스 박스 */
  justify-content: center; /* 버튼 중앙 배치 */
  gap: 10px; /* 버튼 간격 */
}

.error {
  color: #ff6347; /* 빨간색 */
  font-size: 0.9rem; /* 글자 크기 설정 */
  text-align: center; /* 가운데 정렬 */
  margin-top: 10px; /* 위쪽 여백 추가 */
}
</style>
