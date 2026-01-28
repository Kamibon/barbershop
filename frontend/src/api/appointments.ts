import { api } from ".";
import type { CreateBookingRequest } from "../types/Appointment";

const url = "api/appointments";

const getAllAppointments = () => api.get(url);
const getAppointmentById = (id: number) => api.get(url + "/" + id);
const createAppointment = (body: CreateBookingRequest)=> api.post(url, body);
const deleteAppointment = (id: number) => api.delete(url + "/" + id);

export {getAllAppointments, getAppointmentById, deleteAppointment, createAppointment}