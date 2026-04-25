<template>
    <Layout>
        <div class="flex gap-2 items-end">
            <BarbersDropdown :change-selected="setSelectedBarber" />
            <span @click="resetBarberSelection" class="text-red-500 cursor-pointer">Ripristina</span>
        </div>
        <div class="p-4 md:grid md:grid-cols-4 flex flex-col gap-4">
            <AppointmentCard v-for="value in filteredAppointments" :key="value.id" :name="value.clientName"
                :barber="value.barber.name" 
                :date="`${new Date(value.date).toLocaleDateString()} ${value.dateTime}`"
                :service="value.service.name" @delete="setSelectedAppointment(value.id)" />
        </div>
        <div class="text-black font-semibold pl-4" v-if="!appointments.length || !filteredAppointments.length"
            aria-live="polite">
            Nessun risultato trovato
        </div>
        <ConfirmModal :is-open="selectedAppointment !== null" @confirm="confirmDelete"
            @deny="setSelectedAppointment(null)" />
    </Layout>
</template>

<script setup lang="ts">
import { ref, computed, watchEffect } from 'vue';
import Layout from './layout.vue';
import { deleteAppointment, getAllAppointments } from '../api/booking';
import AppointmentCard from '../components/appointmentCard.vue';
import BarbersDropdown from '../components/barbersDropdown.vue';
import ConfirmModal from '../components/confirmModal.vue';
import type { Booking } from '../dtos/booking';

const appointments = ref<Booking[]>([]);
const selectedBarber = ref(0);
const selectedAppointment = ref<number | null>(null);
const isLoading = ref(false);
const error = ref<string | null>(null);

const filteredAppointments = computed(() => {
    if (selectedBarber.value === 0) {
        return appointments.value;
    }
    return appointments.value.filter(item => item.barber.id === selectedBarber.value);
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

watchEffect(() => {
    loadAppointments();
});

const setSelectedBarber = (barberId: number) => {
    selectedBarber.value = barberId;
};

const resetBarberSelection = () => {
    selectedBarber.value = 0;
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
            .catch(e => {
                error.value = "Errore nella cancellazione dell'appuntamento";
                console.error(e);
            });
    }
};
</script>

<style scoped></style>