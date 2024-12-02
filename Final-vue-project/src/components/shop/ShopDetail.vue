<template>
  <div class="product-container-inner">
    <img :src="product.imagePath" alt="상품 사진">
    <p style="font-size: 18px; font-weight: bold; height: 53px;">{{ product.name }}</p>
    <p style="width: 100%; display: flex; justify-content: left;">₩{{ product.price }}</p>
  </div>
</template>

<script setup>
defineProps({
  product: Object,
})
</script>

<style scoped>
  img {
    width: 200px;
    margin: 120px auto 10px;
  }
  .product-container-inner {
    border: 1px solid gainsboro;
    border-radius: 18px;
    display: flex;
    flex-direction: column;
    align-items: start;
    margin: 10px;
    width: 300px;
    height: 500px;


    background-color: #fff;
    box-shadow: 2px 4px 12px #00000014;
    box-sizing: border-box;
    padding: 30px;
    position: relative;
    transition: all .3s cubic-bezier(0,0,.5,1);
  }
</style>