<template>
  <div class="box">
    <div class="container">
      <RouterLink :to="{name: 'main'}">
      <img src="@/assets/color20.png" alt="Dash Logo" class="logo" />
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
  <h4>Store</h4>
  <div class="product-container">
    <ShopDetail v-for="product in store.productList" :key="product.id" :product="product"/>
  </div>
</template>

<script setup>
import Logout from '@/components/account/Logout.vue';
import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
import ShopDetail from '@/components/shop/ShopDetail.vue';
import { useProductStore } from '@/stores/product';
const store = useProductStore();


</script>

<style scoped>
.logout {
  position: absolute; /* 부모인 .header를 기준으로 배치 */
  right: 20px; /* 오른쪽 끝에서 20px 떨어짐 */
  top: 4%; /* 수직 가운데 정렬 */
  transform: translateY(-50%); /* 가운데 정렬 보정 */
}
.box {
  width: 100%;
  /* 화면 전체 너비 */
  height: 70px;
  /* 높이 */
  background-color: #000000;
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

h4{
  font-family: 'Bangers', sans-serif;
  font-size:50px;
}
.divider {
  width: 100%;
  /* 선의 너비 */
  height: 2px;
  /* 선의 높이 */
  background-color: #000000;
  /* 선의 색상 */
  margin: 20px 0;
  /* 위아래 간격 */
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


::v-deep(nav a) {
  
  color: #000000;
  /* 네비게이션 텍스트 색상 */
}

::v-deep(nav a:hover) {
  color: #ffffff;
  /* 호버 시 색상 */
}

h4{
  text-align: center;
}

.product-container {
    display: flex;
    flex-wrap: wrap;
    padding: 30px;
    position: relative;
    transition: all .3s cubic-bezier(0,0,.5,1);
  
  }
</style>