<template>
  <div
    class="h-screen w-screen background flex flex-col justify-center items-center"
  >
    <span class="text-2xl font-bold text-[#d6a229]">Barbershop</span>
    <span class="text-[#C9A24D] text-lg text-shadow-2xs font-semibold"
      >Per i tuoi clienti, per te</span
    >
    <div
      class="border bg-white rounded-2xl flex flex-col gap-2 shadow-lg mt-4 px-4 py-6 w-[40%]"
    >
      <IftaLabel class="w-full">
        <InputText id="username" v-model="username" class="w-full" />
        <label for="username">Username</label>
      </IftaLabel>
      <IftaLabel id="password" class="w-full">
        <Password
          v-model="password"
          class="w-full"
          :feedback="false"
          input-class="w-full"
        />
        <label for="password">Password</label>
      </IftaLabel>
      <Button :disabled="!username.trim() || !password.trim()" label="Accedi" />
    </div>
  </div>
</template>

<script setup lang="ts">
import Button from "primevue/button";
import IftaLabel from "primevue/iftalabel";
import InputText from "primevue/inputtext";
import Password from "primevue/password";
import { ref } from "vue";

const username = ref("");
const password = ref("");
</script>

<style scoped>
.background {
  background-image: url("../assets/img3.jpg");
  background-size: cover;
  background-position: center;
}
</style>
