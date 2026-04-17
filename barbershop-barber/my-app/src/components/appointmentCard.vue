<template>
    <div class="flex items-center justify-center flex-wrap gap-10 w-full">
        <div
            class="bg-indigo-500/5 border border-gray-500/20 text-sm text-gray-500 flex flex-col items-center w-80 rounded-lg">
            <div class="flex items-center justify-between w-full px-4 py-2">
                <div class="flex items-center justify-between gap-3">
                    <div class="bg-white p-1.5 rounded border border-gray-500/30">
                        <img class="h-9"
                            src="https://raw.githubusercontent.com/prebuiltui/prebuiltui/main/assets/dummyLogo/dummyFavicon.svg"
                            alt="dummyFavicon" />
                    </div>
                    <p class="text-lg text-gray-800">{{ name }}</p>
                </div>
                <button @click="emit('delete')" type="button" aria-label="Elimina prenotazione">
                    <svg width="22" height="22" viewBox="0 0 22 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M11 11.917a.917.917 0 1 0 0-1.833.917.917 0 0 0 0 1.833M11 5.5a.917.917 0 1 0 0-1.833.917.917 0 0 0 0 1.833m0 12.834a.917.917 0 1 0 0-1.834.917.917 0 0 0 0 1.834"
                            stroke="#6B7280" strokeOpacity=".8" strokeWidth="1.5" strokeLinecap="round"
                            strokeLinejoin="round" />
                    </svg>
                </button>
            </div>
            <div
                class="flex flex-col items-center gap-2 w-full p-4 pb-2 rounded-b-lg bg-white border-t border-gray-500/20">
                <div class="flex items-center w-full justify-between">
                    <p>Per</p>
                    <p>{{ barber }}</p>
                </div>
                <div class="w-full h-px bg-gray-300/60"></div>
                <div class="flex items-center w-full justify-between">
                    <p>Data</p>
                    <p>{{ formattedDate }}</p>
                </div>
                <div class="w-full h-px bg-gray-300/60"></div>
                <div class="flex items-center w-full justify-between">
                    <p>Servizio</p>
                    <div class="flex items-center gap-2">
                        <p>{{ service }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';


const formattedDate = computed(() => {
    return new Date(props.date).toLocaleDateString('it-IT', {
        day: '2-digit',
        month: 'short',
        year: 'numeric'
    })
})

const emit = defineEmits<{
    (e: 'delete'): void
}>()
const props = defineProps({
    name: {
        required: true,
        type: String
    },
    barber: {
        required: true,
        type: String,
    },
    date: {
        required: true,
        type: String,
    },
    service: {
        required: true,
        type: String
    },
})
</script>

<style scoped></style>