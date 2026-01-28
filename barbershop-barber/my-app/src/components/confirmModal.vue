<template>
    <div v-if="isOpen" class="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-50"
        @click.self="() => onDeny">
        <div
            class="flex flex-col items-center bg-white shadow-md rounded-xl py-6 px-5 md:w-115 w-92.5 border border-gray-200">
            <div class="flex items-center justify-center p-4 bg-red-100 rounded-full">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                        d="M2.875 5.75h1.917m0 0h15.333m-15.333 0v13.417a1.917 1.917 0 0 0 1.916 1.916h9.584a1.917 1.917 0 0 0 1.916-1.916V5.75m-10.541 0V3.833a1.917 1.917 0 0 1 1.916-1.916h3.834a1.917 1.917 0 0 1 1.916 1.916V5.75m-5.75 4.792v5.75m3.834-5.75v5.75"
                        stroke="#DC2626" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" />
                </svg>
            </div>
            <h2 class="text-gray-900 font-semibold mt-4 text-xl">Sei sicuro?</h2>
            <p class="text-sm text-gray-600 mt-2 text-center">
                Vuoi davvero continuare? Questa azione<br>non può essere annullata.
            </p>
            <div class="flex items-center justify-center gap-4 mt-5 w-full">
                <button v-on:click="() => onDeny()" type="button"
                    class="w-full md:w-36 h-10 rounded-md border border-gray-300 bg-white text-gray-600 font-medium text-sm hover:bg-gray-100 active:scale-95 transition">
                    Annulla
                </button>
                <button v-on:click="() => onConfirm()" type="button"
                    class="w-full md:w-36 h-10 rounded-md text-white bg-red-600 font-medium text-sm hover:bg-red-700 active:scale-95 transition">
                    Conferma
                </button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue';

const props = defineProps({
    isOpen: Boolean,
    onDeny: {
        type: Function,
        required: true
    },
    onConfirm: {
        type: Function,
        required: true
    },
})

const handleEsc = (e: KeyboardEvent) => {
    if (e.key === 'Escape') {
        props.onDeny();
    }
};

onMounted(() => {
    window.addEventListener('keydown', handleEsc);
});

onUnmounted(() => {
    window.removeEventListener('keydown', handleEsc);
});
</script>

<style scoped>
/* Animazione di entrata e uscita */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s;
}

.fade-enter,
.fade-leave-to

/* .fade-leave-active in <2.1.8 */
    {
    opacity: 0;
}
</style>
