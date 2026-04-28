import { createRouter, createWebHistory } from "vue-router";
import Bookings from "../pages/bookings.vue";
import Services from "../pages/services.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "services",
      component: Services,
    },
    {
      path: "/bookings",
      name: "bookings",
      component: Bookings,
    },
  ],
});

export default router;
