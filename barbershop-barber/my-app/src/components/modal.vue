<template>
    <div v-if="isOpen" className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div className="bg-white rounded-lg p-6 max-w-md w-full text-center shadow-lg">
            <h2 className="text-xl text-black font-semibold mb-4">{{ title }}</h2>
            <slot />
            <div className="flex justify-around">
                <Button
                    class="bg-green-500 hover:bg-green-600 cursor-pointer text-white font-semibold px-6 py-2 rounded transition"
                    v-on:click="() => onConfirm()">
                    Procedi
                </Button>
                <Button color="#fb2c36"
                    class="bg-red-500 hover:bg-red-600 cursor-pointer text-white font-semibold px-6 py-2 rounded transition"
                    v-on:click="() => onCancel()">
                    Annulla
                </Button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import Button from 'primevue/button';
const props = defineProps(
    {
        isOpen: Boolean,
        title: String,
        onConfirm: {
            type: Function,
            required: true
        },
        onCancel: {
            type: Function,
            required: true
        },
    }
)
</script>

<style scoped></style>