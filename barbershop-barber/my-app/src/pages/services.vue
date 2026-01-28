<template>
    <Layout>
        <div class=" flex flex-col gap-2 items-center">
            <div class="p-4 flex flex-col items-center md:grid md:grid-cols-4 gap-4 w-full">

                <AltServiceCard :key="value.id" v-for="value in services" :id="value.id" :name="value.name"
                    :price="value.price" :duration="value.duration" :imageUrl="value.imagePath" :update="() => updated = true" />
            </div>
            <i class="cursor-pointer pi pi-plus bg-gray-400 hover:bg-amber-500 rounded-full p-4"
                aria-label="Clicca per aggiungere servizi" v-on:click="() => {
                    setModalOpen(true);
                }"></i>
        </div>
        <Modal :is-open="openModal" title="Aggiungi servizio" v-on:confirm="() => sendRequest()"
            v-on:cancel="setModalOpen(false)">
            <div class="w-full flex flex-col gap-2 p-4">
                <IftaLabel class="w-full">
                    <InputText v-model="serviceRequest.name" id="name" class="w-full" />
                    <label for="name">Nome del servizio</label>
                </IftaLabel>
                <IftaLabel class="w-full">
                    <InputText type="number" v-model.number="serviceRequest.price" id="price" class="w-full" />
                    <label for="price">Prezzo</label>
                </IftaLabel>
                <IftaLabel class="w-full">
                    <InputText type="number" v-model.number="serviceRequest.duration" id="duration" class="w-full" />
                    <label for="duration">Durata</label>
                </IftaLabel>
                <FileInput :onSelectFile="(file)=> setFile(file)"/>
            </div>
        </Modal>
    </Layout>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue';
import Layout from './layout.vue';
import { createService, getAllServices, getServiceImage } from '../api/services';
import type { Service } from '../dtos/service';
import Modal from '../components/modal.vue';
import InputText from 'primevue/inputtext';
import IftaLabel from 'primevue/iftalabel';
import AltServiceCard from '../components/altServiceCard.vue';
import FileInput from '../components/fileInput.vue';

const services = ref<Service[]>([])
const openModal = ref(false)
const updated = ref(false)
const serviceRequest = ref(
    {
        name: "",
        price: 0,
        duration: 0
    }
)
const selectedFile = ref<File | null>(null)

watchEffect(() => {
    getAllServices().then((res) => services.value = res.data)
    if (updated.value) {
        getAllServices().then((res) => services.value = res.data)
        updated.value = false
    }
})

const setModalOpen = (value: boolean) => openModal.value = value

const setFile = (file:File) => selectedFile.value = file

const sendRequest = () => {
    createService(serviceRequest.value, selectedFile.value!).then(() => updated.value = true
    ).catch(e => console.log(e))
    setModalOpen(false)
}

</script>

<style scoped></style>