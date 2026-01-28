export interface Booking extends CreateBookingRequest {
  id: number;
}

export interface CreateBookingRequest {
  clientName: string;
  barberId: number;
  dateTime: string;
  date: Date;
  phoneNumber: string;
  serviceId: number;
}
