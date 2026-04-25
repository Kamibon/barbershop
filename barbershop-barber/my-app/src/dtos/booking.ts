import type { Barber } from "./barber";
import type { Service } from "./service";

export interface Booking {
  id: number;
  clientName: string;
  barber: Barber;
  dateTime: string;
  date: Date;
  phoneNumber: string;
  service: Service;
}
