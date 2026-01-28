<template>
    <div class="rounded-xl shadow-xl overflow-hidden transition-transform duration-200 ease-out cursor-pointer bg-white"
        @mousemove="handleMove" @mouseleave="resetTilt" :style="cardStyle">
        <!-- <img src="../../public/hair.jpg" alt="City skyline" class="w-full h-52 object-cover" /> -->
        <img :src="imageUrl !== null ? bUrl + imageUrl : '/hair.jpg'" alt="City skyline" class="w-full h-52 object-cover" />
        <h3 class="mt-3 px-4  mb-1 text-lg font-semibold text-gray-800">
            {{ name }}
        </h3>
        <div class="px-4 pb-2">
            <div class=" flex items-center gap-2 text-gray-600">
                <i class="pi pi-clock"></i>
                <span>{{ duration }} min</span>
            </div>
            <div class=" flex items-center gap-2 text-gray-600">
                <i class="pi pi-euro"></i>
                <span>{{ price }}</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { getServiceImage } from '../api/services';
import { bUrl } from '../main';

const tilt = ref({ x: 0, y: 0 });

const threshold = 12;



const handleMove = (e) => {
    const { left, top, width, height } = e.currentTarget.getBoundingClientRect();
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
        required: true
    },
    name: String,
    duration: Number,
    price: Number,
    imageUrl: String,
    update: {
        type: Function,
        required: true
    }
})

</script>

<style scoped></style>
