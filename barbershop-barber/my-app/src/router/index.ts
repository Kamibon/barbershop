import { createRouter, createWebHistory } from 'vue-router'
import Login from '../pages/login.vue'
import Services from '../pages/services.vue'
import Bookings from '../pages/bookings.vue'


const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'services',
      component: Services
    },
    {
      path: '/bookings',
      name: 'bookings',
      component: Bookings
    }
  ]
})

export default router
