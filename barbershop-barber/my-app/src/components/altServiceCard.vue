<template>
  <div
    class="rounded-xl shadow-xl overflow-hidden transition-transform duration-200 ease-out cursor-pointer bg-white"
    @mouseenter="handleEnter"
    @mousemove="handleMove"
    @mouseleave="resetTilt"
    :style="cardStyle"
  >
    <!-- <img src="../../public/hair.jpg" alt="City skyline" class="w-full h-52 object-cover" /> -->
    <img :src="imageSrc" alt="City skyline" class="w-full h-52 object-cover" />
    <h3 class="mt-3 px-4 mb-1 text-base md:text-lg font-semibold text-gray-800">
      {{ name }}
    </h3>
    <div class="px-4 pb-2">
      <div class="flex items-center gap-2 text-gray-600">
        <i class="pi pi-clock"></i>
        <span class="text-sm md:text-base">{{ duration }} min</span>
      </div>
      <div class="flex items-center gap-2 text-gray-600">
        <i class="pi pi-euro"></i>
        <span class="text-sm md:text-base">{{ formattedPrice }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { bUrl } from "../main";

const imageSrc = computed(() =>
  props.imageUrl ? bUrl + props.imageUrl : "/hair.jpg",
);

const tilt = ref({ x: 0, y: 0 });

const threshold = 12;

const formattedPrice = computed(() =>
  new Intl.NumberFormat("it-IT", {
    style: "currency",
    currency: "EUR",
  }).format(props.price),
);

const rect = ref(null);

const handleEnter = (e) => {
  rect.value = e.currentTarget.getBoundingClientRect();
};

const handleMove = (e) => {
  if (!rect.value) return;

  const { left, top, width, height } = rect.value;
  const x = (e.clientX - left) / width - 0.5;
  const y = (e.clientY - top) / height - 0.5;

  tilt.value = { x: y * -threshold, y: x * threshold };
};

const resetTilt = () => {
  tilt.value = { x: 0, y: 0 };
};

const cardStyle = computed(() => ({
  transform: `perspective(1000px) rotateX(${tilt.value.x}deg) rotateY(${tilt.value.y}deg)`,
}));

const props = defineProps({
  id: {
    type: Number,
    required: true,
  },
  name: {
    type: String,
    default: "",
  },
  duration: {
    type: Number,
    default: 0,
  },
  price: {
    type: Number,
    default: 0,
  },
  imageUrl: {
    type: String,
    default: null,
  },
  update: {
    type: Function,
    required: true,
  },
});
</script>

<style scoped></style>
