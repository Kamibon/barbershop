import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import PrimeVue from "primevue/config";
import "primeicons/primeicons.css";
import Aura from "@primeuix/themes/aura";
import router from "./router";

export const bUrl = import.meta.env.VITE_API_BASE_URL

const app = createApp(App);
app.use(PrimeVue, {
  theme: {
    preset: Aura,
  },
});
app.use(router);
app.mount("#app");
