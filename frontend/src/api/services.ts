import { api } from ".";
import type { Service } from "../types/Service";

const url = "api/services";

const getAllServices = () => api.get<Service[]>(url);
const getServiceById = (id: number) => api.get(url + "/" + id);

export { getAllServices, getServiceById };