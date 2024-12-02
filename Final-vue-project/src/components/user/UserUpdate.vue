<template>
	<div class="box">
		<div class="container">
			<RouterLink :to="{ name: 'main' }">
				<img src="@/assets/123.png" alt="Dash Logo" class="logo" />
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
	<div>
		<fieldset>
			<legend>사용자 정보 수정</legend>
			<form @submit.prevent="updateUser">
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
					<input type="text" id="nickname" v-model="user.nickname" required />
				</div>

				<!-- 이메일 입력 -->
				<div class="form-group">
					<label for="email">Email</label>
					<br>
					<input type="email" id="email" v-model="user.email" required />
				</div>

				<!-- 핸드폰 번호 입력 -->
				<div class="form-group">
					<label for="phoneNumber">PhoneNumber</label>
					<div style="display: flex; align-items: center;">
						<input type="text" id="phoneNumber1" v-model="phoneNumber1" maxlength="3" required /> -
						<input type="text" id="phoneNumber2" v-model="phoneNumber2" maxlength="4" required /> -
						<input type="text" id="phoneNumber3" v-model="phoneNumber3" maxlength="4" required />
					</div>
				</div>

				<!-- 나이 입력 -->
				<div class="form-group">
					<label for="age">Age</label>
					<br>
					<input type="number" id="age" v-model="user.age" required />
				</div>
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

				<!-- 수정 버튼 -->
				<div>
					<button type="submit">수정</button>
				</div>
			</form>
			<button @click="goBack">취소</button>
		</fieldset>
	</div>
</template>

<script setup>
import Logout from '../account/Logout.vue';
import TheHeaderNav from '../common/TheHeaderNav.vue';
import { ref, onMounted, watch } from 'vue';
import { useSessionStore } from '@/stores/session';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const sessionStore = useSessionStore();
const userStore = useUserStore();
const router = useRouter();

const user = ref({
	password: sessionStore.currentUser.password, // 현재 update 컴포넌트에선 비밀번호도 그냥 바꿀 수 있음...
	nickname: sessionStore.currentUser.nickname,
	email: sessionStore.currentUser.email,
	phoneNumber: sessionStore.currentUser.phoneNumber,
	age: sessionStore.currentUser.age,
	gender: sessionStore.currentUser.gender,
});

const phoneNumber1 = ref('');
const phoneNumber2 = ref('');
const phoneNumber3 = ref('');

onMounted(async () => {
  const phoneNumber = sessionStore.currentUser.phoneNumber; // 저장된 phoneNumber 가져오기
  if (phoneNumber) {
    const [part1, part2, part3] = phoneNumber.split('-'); // '-'로 나누기
    phoneNumber1.value = part1 || ''; // 첫 번째 부분
    phoneNumber2.value = part2 || ''; // 두 번째 부분
    phoneNumber3.value = part3 || ''; // 세 번째 부분
  }
});

watch([phoneNumber1, phoneNumber2, phoneNumber3], ([new1, new2, new3]) => {
  if (new1 && new2 && new3) {
    user.value.phoneNumber = `${new1}-${new2}-${new3}`;
  } else {
    user.value.phoneNumber = '';
  }
});

const updateUser = async () => {
  try {
    const userId = sessionStore.currentUser.userId; // 현재 유저 ID
    await userStore.updateUser(userId, user.value);
		await sessionStore.checkSession();
  } catch (error) {
    console.error('사용자 정보 수정 실패:', error);
  }
};

const goBack = () => {
  router.push({ name: 'myPage' });
};

</script>

<style scoped>
.logout {
	position: absolute;
	/* 부모인 .header를 기준으로 배치 */
	right: 20px;
	/* 오른쪽 끝에서 20px 떨어짐 */
	top: 4%;
	/* 수직 가운데 정렬 */
	transform: translateY(-50%);
	/* 가운데 정렬 보정 */
}

.box {
	width: 100%;
	/* 화면 전체 너비 */
	height: 70px;
	/* 높이 */
	background-color: #242E73;
	/* 박스 배경색 */
	color: #ffffff;
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
	background-color: #242E73;
	/* 선의 색상 */
	margin: 20px 0;
	/* 위아래 간격 */
}

.container {
	display: flex;
	justify-content: center;
	/* 수평 가운데 */
	align-items: center;
	/* 수직 가운데 */
	height: 100px;
	/* 전체 화면 높이 */
	width: 100px;
	/* 전체 화면 너비 */
}

.logo {
	width: 100px;
	/* 로고 크기 */
}
</style>