<template>
    <Layout>
        <div class="flex gap-2 items-end">
            <BarbersDropdown :change-selected="(value: number) => setSelectedBarber(value)" />
            <span v-on:click="() => setSelectedBarber(0)" class=" text-red-500 cursor-pointer">Ripristina</span>
        </div>
        <div class=" p-4 md:grid md:grid-cols-4 flex flex-col gap-4">
            <AppointmentCard
                v-for="value in selectedBarber === 0 ? appointments : appointments.filter(item => item.barber.id === selectedBarber)"
                :name="value.clientName" :barber="value.barber.name"
                :date="new Date(value.date).toLocaleDateString() + ' ' + value.dateTime" :service="value.service.name"
                :delete-function="() => setSelectedAppointment(value.id)" />
        </div>
        <div class="text-black font-semibold pl-4" v-if="appointments.length === 0 || (selectedBarber !== 0 && appointments.filter(item => item.barber.id === selectedBarber).length === 0)">
            Nessun risultato trovato
        </div>
        <ConfirmModal :is-open="selectedAppointment !== null"
            v-on:confirm="() => deleteAppointment(selectedAppointment!)?.then(() => {setUpdated(true); setSelectedAppointment(null)})
            .catch(e => console.log(e))"
            v-on:deny="() => setSelectedAppointment(null)" />
    </Layout>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue';
import Layout from './layout.vue';
import { deleteAppointment, getAllAppointments } from '../api/booking';
import AppointmentCard from '../components/appointmentCard.vue';
import type { Booking } from '../dtos/booking';
import BarbersDropdown from '../components/barbersDropdown.vue';
import ConfirmModal from '../components/confirmModal.vue';

const appointments = ref<Booking[]>([])
const selectedBarber = ref(0)
const selectedAppointment = ref<number | null>(null)
const updated = ref(true)

watchEffect(() => {
    if (updated.value) {
        getAllAppointments().then(res => appointments.value = res.data)
        updated.value = false
    }

})

const setSelectedBarber = (barber: number) => {
    selectedBarber.value = barber
    console.log(barber)
}

const setSelectedAppointment = (value: number | null) => {
    selectedAppointment.value = value
}

const setUpdated = (value: boolean) => {
    updated.value = value
}

</script>

<style scoped></style>