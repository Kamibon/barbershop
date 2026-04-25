<template>
    <Layout>
        <div class=" flex flex-col gap-2 items-center">
            <div class="p-4 items-center grid-cols-2 grid md:grid-cols-4 gap-4 w-full">
                <AltServiceCard :key="value.id" v-for="value in services" :id="value.id" :name="value.name"
                    :price="value.price" :duration="value.duration" :imageUrl="value.imagePath"
                    :update="() => updated = true" />
            </div>
            <div v-if="!services.length" class="flex flex-col items-center justify-center gap-2">
                <h1 class="text-black font-semibold">Inserisci qui i tuoi servizi</h1>
                <span class="text-red-400 font-semibold animate-bounce text-xl">⬇</span>
            </div>
            <i class="cursor-pointer pi pi-plus bg-gray-400 hover:bg-amber-500 rounded-full p-4"
                aria-label="Clicca per aggiungere servizi" v-on:click="() => {
                    setModalOpen(true);
                }"></i>
        </div>
        <Modal :is-open="openModal" @confirm="sendRequest" @cancel="() => setModalOpen(false)" title="Aggiungi servizio" "
            >
            <div class=" w-full flex flex-col gap-2 p-4">
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
            <FileInput :onSelectFile="(file: File) => setFile(file)" />
            </div>
        </Modal>
    </Layout>
</template>

<script setup lang="ts">
import IftaLabel from 'primevue/iftalabel';
import InputText from 'primevue/inputtext';
import { ref, watch } from 'vue';
import { createService, getAllServices } from '../api/services';
import AltServiceCard from '../components/altServiceCard.vue';
import FileInput from '../components/fileInput.vue';
import Modal from '../components/modal.vue';
import type { Service } from '../dtos/service';
import Layout from './layout.vue';

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

const loadServices = async () => {
    try {
        const { data } = await getAllServices();
        services.value = data;
    } catch (error) {
        console.error(error);
    }
};

loadServices();

watch(updated, (newValue) => {
    if (newValue) {
        loadServices();
        updated.value = false;
    }
});

const setModalOpen = (value: boolean) => openModal.value = value

const setFile = (file: File) => selectedFile.value = file

const sendRequest = async () => {
    const { duration, price, name } = serviceRequest.value
    if (duration <= 0 || price <= 0 || !name.trim())
        throw new Error("The request is not valid")
    try {
        await createService(serviceRequest.value, selectedFile.value!)
        updated.value = true
        serviceRequest.value.duration = 0
        serviceRequest.value.price = 0
        serviceRequest.value.name = ''
    } catch (error) {
        console.log(error)
    }
    setModalOpen(false)
}

</script>

<style scoped></style>