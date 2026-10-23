import type { Service } from "./service";

export interface Booking {
  id: number;
  clientName: string;
  barber: string;
  dateTime: string;
  date: Date;
  phoneNumber: string;
  service: Service;
}
