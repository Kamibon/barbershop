<template>
  <div
    class="rounded-xl shadow-xl overflow-hidden transition-transform duration-200 ease-out cursor-pointer bg-white relative"
    @mouseenter="handleEnter" @mousemove="handleMove" @mouseleave="resetTilt" :style="cardStyle">
    <button @click="() => emit('delete', id)"
      class="rounded-full size-6 bg-white absolute top-[2%] right-[2%] z-20 flex items-center justify-center">
      <i class="pi pi-times text-red-500" />
    </button>
    <img :src="imageSrc" alt="Card del servizio" class="w-full h-52 object-cover" />
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
import { deleteService, getAllServices } from "../api/services";

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

const emit = defineEmits(["delete"]);

</script>

<style scoped></style>
