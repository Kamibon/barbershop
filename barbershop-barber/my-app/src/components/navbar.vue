<template>
    <div className=" bg-linear-to-b from-[#0F0F0F] to-[#C9A24D] w-full h-30 flex items-center justify-around">
        <i style="font-size: x-large;" v-on:click="() => router.push('/')"
            class="lg:hidden cursor-pointer hover:text-amber-500 pi pi-shop" ></i>
        <RouterLink to="/" class="text-xl hidden lg:block text-white cursor-pointer hover:text-amber-500">
            I nostri servizi
        </RouterLink>
        <i style="font-size: x-large;" v-on:click="() => router.push('/bookings')"
            class="lg:hidden cursor-pointer hover:text-amber-500 pi pi-calendar" ></i>
        <RouterLink to="/bookings" class="text-xl hidden lg:block text-white cursor-pointer hover:text-amber-500">
            I tuoi appuntamenti
        </RouterLink>
        <i style="font-size: x-large;" v-on:click="() => router.replace('/')"
            class="cursor-pointer hover:text-amber-500 pi pi-sign-out" ></i>
    </div>
</template>

<script setup lang="ts">
import { RouterLink, useRouter } from 'vue-router';

const router = useRouter()
</script>

<style scoped></style>