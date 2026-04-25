<template>
    <div v-if="isOpen" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white rounded-lg p-6 max-w-md w-full text-center shadow-lg" role="dialog"
            aria-labelledby="modal-title" aria-describedby="modal-description">
            <h2 id="modal-title" class="text-xl text-black font-semibold mb-4">{{ title }}</h2>
            <slot />
            <div class="flex justify-around mt-4">
                <Button @click="emit('confirm')">
                    Procedi
                </Button>
                <Button @click="emit('cancel')">
                    Annulla
                </Button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

import Button from 'primevue/button';
import { onMounted, onUnmounted } from 'vue';

const props = defineProps({
    isOpen: {
        type: Boolean,
        required: true
    },
    title: {
        type: String,
        required: true
    },
});

const emit = defineEmits<{
    (e: 'confirm'): void,
    (e: 'cancel'): void
}>();

const handleEscape = (event: KeyboardEvent) => {
    if (event.key === 'Escape') {
        emit('cancel')
    }
};

onMounted(() => {
    window.addEventListener('keydown', handleEscape);
});

onUnmounted(() => {
    window.removeEventListener('keydown', handleEscape);
});

</script>

<style scoped>
.flex.justify-around {
    gap: 1rem;
}
</style>