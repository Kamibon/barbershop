<template>
    <div>
        <div class="cursor-pointer rounded-xl border border-gray-400 shadow" v-bind="getRootProps()">
            <input v-bind="getInputProps()" />
            <p class="text-[#C9A24D]" v-if="isDragActive">Rilascia qui ...</p>
            <p class="text-gray-400" v-else>Rilascia file qui, o clicca per selezionare file</p>
        </div>
        <button @click="open">open</button>
    </div>
</template>

<script setup>
import { useDropzone } from "vue3-dropzone";
import { defineProps } from "vue";

const props = defineProps({
  onSelectFile: {
    type: Function,
    required: true
  }
});

const onDrop = (acceptFiles) => {
  if (acceptFiles.length > 0) {
    props.onSelectFile(acceptFiles[0]);
  }
};

const { getRootProps, getInputProps, isDragActive, open } = useDropzone({
  onDrop
});
</script>