import { useEffect, useState } from "react";
import Calendar from "react-calendar";
import { formatDay } from "react-calendar/dist/shared/dateFormatter.js";
import { barbers } from "../api/barbers";
import { getAllServices } from "../api/services";
import { useAppointmentForm } from "../hooks/useAppointmentForm";
import type { Service } from "../types/Service";
import "./calendar.css";
import FailedToast from "./components/failedToast";
import SuccessToast from "./components/successToast";
import TimeSelect from "./components/timeSelect";

export const Appointments = () => {
  
  const [allServices, setAllServices] = useState<Service[]>([]);

  const appointmentForm = useAppointmentForm()

  useEffect(() => {
    getAllServices()
      .then((res) => setAllServices(res.data.content))
      .catch((e) => console.log(e));
  }, []);

  useEffect(() => {
    let id: number;
    if (appointmentForm.success || appointmentForm.failed) {
      id = setTimeout(() => {
        appointmentForm.setSuccess(false);
        appointmentForm.setFailed(false);
      }, 5000);
      return () => clearTimeout(id);
    }
  }, [appointmentForm.success, appointmentForm.failed]);

  return (
    <div className=" p-4 w-full">
      <SuccessToast
        title={"Appuntamento creato!"}
        message={
          "Il tuo appuntamento è stato registrato con successo. A presto!"
        }
        open={appointmentForm.success}
        onClose={() => appointmentForm.setSuccess(false)}
      />
      <FailedToast
        title={"Creazione appuntamento fallita :("}
        open={appointmentForm.failed}
        onClose={() => appointmentForm.setFailed(false)}
      />
      <span className=" font-bold text-brand-secondary text-lg lg:text-3xl ">
        Scegli il giorno che vuoi tu, all'orario che vuoi tu, con il barbiere
        che vuoi tu!
      </span>
      <Calendar
        defaultValue={new Date()}
        locale="it"
        minDate={new Date()}
        tileDisabled={({ date }) => date < new Date()}
        value={appointmentForm.chosenDate}
        formatDay={(locale, date) => formatDay(locale, date)}
        onClickDay={(value) => {
          appointmentForm.setChosenDate(value);
        }}
        onChange={(value) => appointmentForm.setChosenDate(value)}
        tileClassName={({ date }) =>
          date > new Date()
            ? "font-semibold text-base text-[#2B2B2B] cursor-pointer p-3"
            : date !== appointmentForm.chosenDate
              ? "text-gray-300 text-base p-3 cursor-not-allowed"
              : "font-semibold bg-brand-secondary rounded-full text-base text-white cursor-pointer p-3"
        }
        className={
          "rounded-2xl border p-3 shadow-2xl border-gray-400 mt-2 flex flex-col items-center"
        }
      />
      <div className="flex lg:flex-row flex-col gap-2 mt-4 max-w-full">
        <select
          value={appointmentForm.chosenBarber ?? ""}
          onChange={(e) =>
            appointmentForm.setChosenBarber(e.target.value ? Number(e.target.value) : null)
          }
          className=" bg-white border border-brand-primary rounded-2xl p-2 text-lg"
        >
          <option value="">Seleziona il barbiere</option>
          {barbers.map((item) => (
            <option key={item.id} value={item.id}>{item.name}</option>
          ))}
        </select>
        <select
          value={appointmentForm.chosenService ?? ""}
          onChange={(e) =>
            appointmentForm.setChosenService(e.target.value ? Number(e.target.value) : null)
          }
          className=" bg-white border border-brand-primary rounded-2xl p-2 text-lg"
        >
          <option value="">Seleziona il servizio</option>
          {allServices.map((item) => (
            <option value={item.id} key={item.id}>
              {item.name}
            </option>
          ))}
        </select>
        <TimeSelect
          value={appointmentForm.chosenTime}
          onChange={(value) => appointmentForm.setChosenTime(value)}
        />
        <input
          className=" bg-white border border-brand-primary rounded-2xl p-2 w-full md:grow text-lg"
          value={appointmentForm.clientName}
          placeholder="Nome e cognome"
          onChange={(e) => appointmentForm.setClientName(e.target.value)}
        />
        <input
          className=" bg-white border border-brand-primary rounded-2xl w-full md:grow p-2 text-lg"
          value={appointmentForm.phoneNumber?.toString()}
          placeholder="Numero di telefono"
          onChange={(e) => appointmentForm.setPhoneNumber(e.target.value)}
        />
      </div>
      <div className="w-full flex items-center justify-center pt-4">
        <button
          onClick={() => appointmentForm.handleSubmit()}
          disabled={
            !appointmentForm.valid
          }
          className="bg-brand-primary cursor-pointer hover:bg-amber-500 disabled:opacity-30 rounded-xl px-4 py-2 text-white"
          aria-label="Prenota"
        >
          Prenota
        </button>
      </div>
    </div>
  );
};
