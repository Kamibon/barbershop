import { api } from ".";
import type { CreateServiceRequest, Service } from "../dtos/service";
import keycloak from "../keycloak/keycloak";

const url = "api/services";

const getAllServices = () => api.get<Service[]>(url);
const getServiceById = (id: number) => api.get(url + "/" + id);

const deleteService = (id: number) => api.delete(url + "/" + id);
const createService = (body: CreateServiceRequest, file: File) => {
  if (!keycloak.value?.token) throw Error("Missing token");
  const formData = new FormData();

  formData.append("name", body.name);
  formData.append("duration", body.duration.toString());
  formData.append("price", body.price.toString());

  formData.append("file", file);

  return api.post(url, formData, {
    headers: {
      Authorization: "Bearer " + keycloak.value?.token,
      "Content-Type": "multipart/form-data",
    },
  });
};

const getServiceImage = (fileName: string) => {
  api.get("/api" + fileName);
};

export {
  getAllServices,
  getServiceById,
  createService,
  deleteService,
  getServiceImage,
};
