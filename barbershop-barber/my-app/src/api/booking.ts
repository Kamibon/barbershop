import { api } from ".";
import type { Booking } from "../dtos/booking";
import type { Page } from "../dtos/page";
import keycloak from "../keycloak/keycloak";

const url = "api/appointments";

const getAllAppointments = () => api.get<Page<Booking>>(url);
const getAppointmentById = (id: number) => api.get(url + "/" + id);
const deleteAppointment = (id: number) => {
  if (!keycloak.value?.token) throw new Error("Missing token");
  return api.delete(url + "/" + id, {
    headers: {
      Authorization: "Bearer " + keycloak.value?.token,
    },
  });
};
const getAppointmentsByName = () => api.get(url + "/barber/apps");

export {
  getAllAppointments,
  getAppointmentById,
  getAppointmentsByName,
  deleteAppointment,
};
