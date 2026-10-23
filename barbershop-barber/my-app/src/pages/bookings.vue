<template>
  <Layout>
    <div class="flex gap-2 items-end">
      <BarbersDropdown :change="setSelectedBarber" />
      <button type="button" @click="resetBarberSelection" class="text-red-500 cursor-pointer">
        Ripristina
      </button>
    </div>
    <div class="p-4 md:grid md:grid-cols-4 flex flex-col gap-4">
      <AppointmentCard v-for="value in filteredAppointments" :key="value.id" :name="value.clientName"
        :barber="value.barber" :date="`${value.date}`"
        :service="value.service.name" @delete="setSelectedAppointment(value.id)" />
    </div>
    <div class="text-black font-semibold pl-4" v-if="!appointments.content.length || !filteredAppointments.length"
      aria-live="polite">
      Nessun risultato trovato
    </div>
    <ConfirmModal :is-open="selectedAppointment !== null" @confirm="confirmDelete"
      @deny="setSelectedAppointment(null)" />
  </Layout>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import { deleteAppointment, getAllAppointments } from "../api/booking";
import AppointmentCard from "../components/appointmentCard.vue";
import BarbersDropdown from "../components/barbersDropdown.vue";
import ConfirmModal from "../components/confirmModal.vue";
import type { Booking } from "../dtos/booking";
import Layout from "./layout.vue";

const appointments = ref<{ content: Booking[] }>({ content: [] });
const selectedBarber = ref("");
const selectedAppointment = ref<number | null>(null);
const isLoading = ref(false);
const error = ref<string | null>(null);

  watch(selectedBarber, (value)=>console.log(value))

const filteredAppointments = computed(() => {
  if (!selectedBarber.value.trim()) {
    return appointments.value.content;
  }
  return appointments.value.content.filter(
    (item) => item.barber === selectedBarber.value,
  );
});

const loadAppointments = async () => {
  isLoading.value = true;
  try {
    const res = await getAllAppointments();
    appointments.value = res.data;
  } catch (e) {
    error.value = "Errore nel caricamento degli appuntamenti";
    console.error(e);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  loadAppointments();
});

const setSelectedBarber = (barberName: string) => {
  selectedBarber.value = barberName;
};

const resetBarberSelection = () => {
  selectedBarber.value = "";
};

const setSelectedAppointment = (value: number | null) => {
  selectedAppointment.value = value;
};

const confirmDelete = () => {
  if (selectedAppointment.value) {
    deleteAppointment(selectedAppointment.value)
      .then(() => {
        loadAppointments();
        selectedAppointment.value = null;
      })
      .catch((e) => {
        error.value = "Errore nella cancellazione dell'appuntamento";
        console.error(e);
      });
  }
};
</script>

<style scoped></style>
