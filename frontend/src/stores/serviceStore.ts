import { create } from "zustand";
import type { PromiseStatuses } from "../types/PromiseStatuses";
import { api, baseUrl } from "../api";
import type { Service } from "../types/Service";
import type { Page } from "../types/Page";

interface ServiceState {
  findAllService: () => void;
  findAllServiceResponse: Service[];
  findAllServiceStatus: PromiseStatuses;
  setFindAllServicesStatus: (status: PromiseStatuses) => void;
}

export const useService = create<ServiceState>((set) => ({
  findAllService: async () => {
    const { data } = await api.get<Page<Service>>(baseUrl + "services");
    set({ findAllServiceResponse: data.content });
  },
  findAllServiceResponse: [],
  findAllServiceStatus: "idle",
  setFindAllServicesStatus: (status: PromiseStatuses) =>
    set({ findAllServiceStatus: status }),
}));
