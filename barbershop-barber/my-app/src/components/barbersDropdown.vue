<template>
  <div class="flex flex-col w-64 text-sm pl-4 pt-2">
    <p class="font-medium text-gray-800 py-4">Filtra per:</p>

    <!-- Contenitore di riferimento -->
    <div class="relative w-full">
      <button
        aria-haspopup="listbox"
        :aria-expanded="isOpen"
        type="button"
        @click.stop="toggleDropdown"
        class="cursor-pointer group flex items-center justify-between w-full text-left px-2 py-2 border rounded bg-white text-gray-700 border-gray-300 shadow-sm hover:bg-gray-50 focus:outline-none"
      >
        <div class="flex items-center gap-2">
          <span>{{ selectedBarber ?? "Seleziona un barbiere" }}</span>
        </div>

        <svg
          width="11"
          height="17"
          viewBox="0 0 11 17"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M9.92546 6L5.68538 1L1.44531 6"
            stroke="#6B7280"
            stroke-opacity="0.7"
            stroke-width="1.5"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M1.44564 11L5.68571 16L9.92578 11"
            stroke="#6B7280"
            stroke-opacity="0.7"
            stroke-width="1.5"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </button>

      <!-- Dropdown in sovraimpressione -->
      <ul
        v-if="isOpen"
        role="listbox"
        class="absolute top-full left-0 mt-1 w-full bg-white border border-gray-300 rounded shadow-lg z-50 py-2"
      >
        <li
          class="px-2 py-2 flex items-center gap-2 cursor-pointer text-gray-600 hover:bg-indigo-500 hover:text-white"
          @click="selectBarber(null)"
        >
          Seleziona un barbiere
        </li>

        <li
          v-for="value in users"
          :key="value.id"
          :class="[
            'px-2 py-2 flex items-center gap-2 cursor-pointer',
            value.name === selectedBarber
              ? 'bg-indigo-500 text-white'
              : 'hover:bg-indigo-500 hover:text-white text-gray-600',
          ]"
          @click="selectBarber(value.name)"
        >
          <img
            class="w-6 h-6 rounded-full"
            :src="value.image"
            :alt="value.name"
          />
          <span>{{ value.name }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from "vue";

const emit = defineEmits<{
  (e: "change", barber: string | null): void;
}>();

const handleClickOutside = (e: MouseEvent) => {
  isOpen.value = false;
};

onMounted(() => {
  document.addEventListener("click", handleClickOutside);
});

onBeforeUnmount(() => {
  document.removeEventListener("click", handleClickOutside);
});

const selectBarber = (barber: string | null) => {
  selectedBarber.value = barber;
  isOpen.value = false;
  emit("change", barber);
};

const toggleDropdown = () => {
  isOpen.value = !isOpen.value;
};

const users = [
  {
    id: 1,
    name: "Mario",
    image:
      "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?q=80&w=50",
  },
  {
    id: 2,
    name: "Luigi",
    image:
      "https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=50",
  },
  {
    id: 3,
    name: "Paolo",
    image:
      "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=50&h=50&auto=format&fit=crop",
  },
];

const isOpen = ref(false);
const selectedBarber = ref<string | null>(null);
</script>

<style scoped></style>
