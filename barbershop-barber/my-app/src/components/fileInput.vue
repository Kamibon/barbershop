<template>
  <div>
    <div
      class="cursor-pointer rounded-xl border border-gray-400 shadow"
      v-bind="getRootProps()"
    >
      <div v-if="!selectedFile">
        <input v-bind="getInputProps()" />
        <p class="text-brand-primary" v-if="isDragActive">Rilascia qui ...</p>
        <p class="text-gray-400" v-else>
          Rilascia file qui, o clicca per selezionare file
        </p>
      </div>
      <p v-else class="text-gray-400">Hai inserito il file.</p>
    </div>
    <button @click="open">open</button>
  </div>
</template>

<script setup>
import { useDropzone } from "vue3-dropzone";
import { defineProps, onUnmounted, ref } from "vue";

const props = defineProps({
  onSelectFile: {
    type: Function,
    required: true,
  },
});

const selectedFile = ref(false);

const onDrop = (acceptFiles) => {
  if (acceptFiles.length > 0) {
    props.onSelectFile(acceptFiles[0]);
    selectedFile.value = true;
  }
};

onUnmounted(() => {
  selectedFile.value = false;
});

const { getRootProps, getInputProps, isDragActive, open } = useDropzone({
  onDrop,
});
</script>
