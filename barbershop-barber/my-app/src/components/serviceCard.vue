<template>
    <div class=" rounded-2xl shadow flex-col bg-white border border-[#C9A24D] py-1 px-4 w-full">
        <div class="flex items-center justify-between">
            <span class=" font-bold text-lg italic text-black">{{ name }}</span>
            <i v-on:click="() => { deleteService(id); update() }"
                class="pi pi-times cursor-pointer text-red-500 text-xl "></i>
        </div>
        <div class=" flex items-center gap-2 text-gray-600">
            <i class="pi pi-clock"></i>
            <span>{{ duration }} min</span>
        </div>
        <div class=" flex items-center gap-2 text-gray-600">
            <i class="pi pi-euro"></i>
            <span>{{ price }}</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import { deleteAppointment } from '../api/booking';
import { deleteService } from '../api/services';

const props = defineProps({
    id: {
        type: Number,
        required: true
    },
    name: String,
    duration: Number,
    price: Number,
    update: {
        type: Function,
        required: true
    }
})

</script>

<style scoped></style>