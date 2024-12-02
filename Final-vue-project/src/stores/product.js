import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useProductStore = defineStore('product', () => {
  let id = 0;
  const productList = ref([
    {
      id: id++,
      name: "스타스포츠 스매싱 축구공 SB8785-18",
      price: "14,730원",
      imagePath: "src/assets/products/soccer_ball.png",
    },
    {
      id: id++,
      name: "아디다스 축구화 천연잔디용 맨땅용 FXG",
      price: "65,500원",
      imagePath: "src/assets/products/soccer_shoes.jpg",
    },
    {
      id: id++,
      name: "파리 생제르망 유니폼 PSG",
      price: "86,400원",
      imagePath: "src/assets/products/soccer_uniform.png",
    },
    {
      id: id++,
      name: "스포츠트라이브 ST 풋살공 STFS-4Y",
      price: "40,000원",
      imagePath: "src/assets/products/futsal_ball.png",
    },
    {
      id: id++,
      name: "아디다스 남성 인조잔디 전용 TF 축구화",
      price: "66,500원",
      imagePath: "src/assets/products/futsal_shoes.jpg",
    },
    {
      id: id++,
      name: "이레스포츠 철제 축구 골대",
      price: "105,000원",
      imagePath: "src/assets/products/futsal_goal.png",
    },
    {
      id: id++,
      name: "JB235TB-31 태극 족구공",
      price: "45,000원",
      imagePath: "src/assets/products/footvolley_ball.png",
    },
    {
      id: id++,
      name: "낫소 피더테크 족구화",
      price: "38,710원",
      imagePath: "src/assets/products/footvolley_shoes.png",
    },
    {
      id: id++,
      name: "휴대용 접이식 족구 네트",
      price: "36,230원",
      imagePath: "src/assets/products/footvolley_net.png",
    },
  ])

  return { productList }
})
