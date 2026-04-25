import { api } from ".";
import keycloak from "../keycloak/keycloak";

const url = "api/appointments";

const getAllAppointments = () => api.get(url);
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
