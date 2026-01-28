<template>
    <div class="flex flex-col w-64 text-sm relative pl-4 pt-2">
        <p class="font-medium text-gray-800 py-4 ">Filtra per:</p>
        <button type="button"  v-on:click="() => setIsOpen(!isOpen)"
            class="cursor-pointer group flex items-center justify-between w-full text-left px-2 py-2 border rounded bg-white text-gray-700 border-gray-300 shadow-sm hover:bg-gray-50 focus:outline-none">
            <div class="flex items-center gap-2">
                <img class="w-6 h-6 rounded-full" :src="selectedBarber?.image" :alt="selectedBarber?.name" />
                <span>{{ selectedBarber?.name }}</span>
            </div>
            <svg width="11" height="17" viewBox="0 0 11 17" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9.92546 6L5.68538 1L1.44531 6" stroke="#6B7280" strokeOpacity="0.7" strokeWidth="1.5"
                    strokeLinecap="round" strokeLinejoin="round" />
                <path d="M1.44564 11L5.68571 16L9.92578 11" stroke="#6B7280" strokeOpacity="0.7" strokeWidth="1.5"
                    strokeLinecap="round" strokeLinejoin="round" />
            </svg>
        </button>
        <ul v-if="isOpen" class="w-64 bg-white border border-gray-300 rounded shadow-md mt-1 py-2 right-0">
            <li v-for="value in users" :key="value.name" :class="[
                'px-2 py-2 flex items-center gap-2 cursor-pointer',
                value.name === selectedBarber?.name
                    ? 'bg-indigo-500 text-white'
                    : 'hover:bg-indigo-500 hover:text-white text-gray-600'
            ]" v-on:click="(e) => { e.stopPropagation(); setSelectedBarber(value); changeSelected(value.id) }">
                <img class="w-6 h-6 rounded-full" :src="value.image" :alt="value.name" />
                <span>{{ value.name }}</span>
            </li>
        </ul>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps({
    changeSelected: {
        type: Function,
        required: true
    }
})

const users = [
    {
        id: 1,
        name: "Mario",
        image: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?q=80&w=50",
    },
    {
        id: 2,
        name: "Luigi",
        image: "https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=50",
    },
    {
        id: 3,
        name: "Paolo",
        image: "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=50&h=50&auto=format&fit=crop",
    },
];

const isOpen = ref(false)
const selectedBarber = ref(users[0])

const setIsOpen = (value: boolean) => {
    isOpen.value = value
}

const setSelectedBarber = (barber: any) => {
    selectedBarber.value = barber
}
</script>

<style scoped></style>