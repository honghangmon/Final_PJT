import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import HomeView from '@/views/HomeView.vue'
import MyPageView from '@/views/MyPageView.vue'
import TeamListView from '@/views/TeamListView.vue'
import MatchingView from '@/views/MatchingView.vue'
import GroundView from '@/views/GroundView.vue'
import ShopView from '@/views/ShopView.vue'
import SignUpView from '@/views/SignUpView.vue'
import TeamDetail from '@/components/team/TeamDetail.vue'
import { useSessionStore } from '@/stores/session'
import TeamCreate from '@/components/team/TeamCreate.vue'
import TeamUpdate from '@/components/team/TeamUpdate.vue'
import UserUpdate from '@/components/user/UserUpdate.vue'
import PasswordVerify from '@/components/account/PasswordVerify.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/main',
      name: 'main',
      component: MainView,
    },
    {
      path: '/my-page',   // path는 kebab-case
      name: 'myPage',     // name은 camelCase
      component: MyPageView,  // component는 PascalCase
    },
    {
      path: '/password-verify',
      name: 'passwordVerify',
      component: PasswordVerify,
    },
    {
      path: '/user-update',
      name: 'userUpdate',
      component: UserUpdate,
    },
    {
      path: '/team-list',
      name: 'teamList',
      component: TeamListView,
    },
    {
      path: '/matching',
      name: 'matching',
      component: MatchingView,
    },
    {
      path: '/ground',
      name: 'ground',
      component: GroundView,
    },
    {
      path: '/shop',
      name: 'shop',
      component: ShopView,
    },
    {
      path: '/sign-up',
      name: 'signUp',
      component: SignUpView,
    },
    {
      path: '/team-detail/:id',
      name: 'teamDetail',
      component: TeamDetail,
    
    },
    {
      path: '/team-create',
      name: 'teamCreate',
      component: TeamCreate,
    },
    {
      path: '/team-update/:id',
      name: 'teamUpdate',
      component: TeamUpdate,
    },
  ],
})

router.beforeEach(async (to, from, next) => {
  const store = useSessionStore();

  // 세션 정보를 먼저 확인
  await store.checkSession();

  // 로그인하지 않은 상태에서 보호된 페이지에 접근하려고 하면 리다이렉트
  const requiresAuth = to.name !== 'signUp' && to.name !== 'home'; // 보호할 페이지 지정
  const isLoginOrSignUpPage = to.name === 'signUp' || to.name === 'home'; // 로그인 상태에서 접근하지 못하는 페이지

  if (requiresAuth && !store.isAuthenticated) {
    alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.')
    next({ name: 'home' }); // 로그인 페이지로 리다이렉트
  } else if (isLoginOrSignUpPage && store.isAuthenticated) {
    // 로그인한 상태에서 로그인/회원가입 페이지 접근 시 메인 페이지로 리다이렉트
    alert('이미 로그인된 상태입니다. 메인 페이지로 이동합니다.');
    next({ name: 'main' }); // 메인 페이지로 리다이렉트
  } else {
    next(); // 정상적으로 이동
  }

});

export default router
