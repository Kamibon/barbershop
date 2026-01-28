export interface Service extends CreateServiceRequest {
  id: number;
}

export interface CreateServiceRequest {
  name: string;
  duration: number;
  price: number;
  imagePath: string
}
