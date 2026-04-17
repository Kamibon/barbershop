import React, { useEffect, useState } from "react";
import Calendar from "react-calendar";
import "./calendar.css";
import { formatDay } from "react-calendar/dist/shared/dateFormatter.js";
import TimeSelect from "./components/timeSelect";
import type { Service } from "../types/Service";
import { getAllServices } from "../api/services";
import { barbers } from "../api/barbers";
import { createAppointment } from "../api/appointments";
import SuccessToast from "./components/successToast";
import FailedToast from "./components/failedToast";

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

export const Appointments = () => {
  const [chosenDate, setChosenDate] = useState<Value>(new Date());
  const [chosenBarber, setChosenBarber] = useState<number>(0);
  const [chosenTime, setChosenTime] = useState<string>("");
  const [phoneNumber, setPhoneNumber] = useState<string | null>(null);
  const [chosenService, setChosenService] = useState<number | null>(null);
  const [clientName, setClientName] = useState<string>("");

  const [allServices, setAllServices] = useState<Service[]>([]);
  const [success, setSuccess] = useState(false);
  const [failed, setFailed] = useState(false);

  const regexPhone = /^\+?(\([0-9]{3}\))?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/;

  useEffect(() => {
    getAllServices()
      .then((res) => setAllServices(res.data))
      .catch((e) => console.log(e));
  }, []);

  useEffect(() => {
    let id: number;
    if (success || failed) {
      id = setTimeout(() => {
        setSuccess(false);
        setFailed(false);
      }, 5000);
      return () => clearTimeout(id);
    }
  }, [success, failed]);

  return (
    <div className=" p-4 w-full">
      <SuccessToast
        title={"Appuntamento creato!"}
        message={
          "Il tuo appuntamento è stato registrato con successo. A presto!"
        }
        open={success}
        onClose={() => setSuccess(false)}
      />
      <FailedToast
        title={"Creazione appuntamento fallita :("}
        open={failed}
        onClose={() => setFailed(false)}
      />
      <span className=" font-bold text-[#0F0F0F] text-lg lg:text-3xl ">
        Scegli il giorno che vuoi tu, all'orario che vuoi tu, con il barbiere
        che vuoi tu!
      </span>
      <Calendar
        defaultValue={new Date()}
        locale="it"
        minDate={new Date()}
        tileDisabled={({ date }) => date < new Date()}
        value={chosenDate}
        formatDay={(locale, date) => formatDay(locale, date)}
        onClickDay={(value) => {
          setChosenDate(value);
        }}
        onChange={(value) => setChosenDate(value)}
        tileClassName={({ date }) =>
          date > new Date()
            ? "font-semibold text-base text-[#2B2B2B] cursor-pointer p-3"
            : date !== chosenDate
              ? "text-gray-300 text-base p-3 cursor-not-allowed"
              : "font-semibold bg-[#0F0F0F] rounded-full text-base text-white cursor-pointer p-3"
        }
        className={
          "rounded-2xl border p-3 shadow-2xl border-gray-400 mt-2 flex flex-col items-center"
        }
      />
      <div className="flex lg:flex-row flex-col gap-2 mt-4 max-w-full">
        <select
          value={barbers.find((elem) => elem.id === chosenBarber)?.name}
          onChange={(e) =>
            setChosenBarber(
              barbers.find((elem) => elem.name === e.target.value)!.id,
            )
          }
          className=" bg-white border border-[#C9A24D] rounded-2xl p-2 text-lg"
        >
          <option>Seleziona il barbiere</option>
          {barbers.map((item) => (
            <option key={item.name}>{item.name}</option>
          ))}
        </select>
        <select
          value={allServices.find((elem) => elem.id === chosenService)?.name}
          onChange={(e) =>
            setChosenService(
              allServices.find((elem) => elem.name === e.target.value)!.id,
            )
          }
          className=" bg-white border border-[#C9A24D] rounded-2xl p-2 text-lg"
        >
          <option>Seleziona il servizio</option>
          {allServices.map((item) => (
            <option key={item.name + item.id.toString()}>{item.name}</option>
          ))}
        </select>
        <TimeSelect
          value={chosenTime}
          onChange={(value) => setChosenTime(value)}
        />
        <input
          className=" bg-white border border-[#C9A24D] rounded-2xl p-2 w-full md:grow text-lg"
          value={clientName}
          placeholder="Nome e cognome"
          onChange={(e) => setClientName(e.target.value)}
        />
        <input
          className=" bg-white border border-[#C9A24D] rounded-2xl w-full md:grow p-2 text-lg"
          value={phoneNumber?.toString()}
          placeholder="Numero di telefono"
          onChange={(e) => setPhoneNumber(e.target.value)}
        />
      </div>
      <div className="w-full flex items-center justify-center pt-4">
        <button
          onClick={() =>
            createAppointment({
              barberId: chosenBarber,
              clientName: clientName,
              date: chosenDate as unknown as Date,
              dateTime: chosenTime,
              phoneNumber: phoneNumber!,
              serviceId: chosenService!,
            })
              .then((res) =>
                res.status === 201 ? setSuccess(true) : setFailed(true),
              )
              .catch((e) => setFailed(true))
          }
          disabled={
            !chosenDate ||
            !chosenTime.trim() ||
            !phoneNumber?.trim() ||
            !regexPhone.test(phoneNumber) ||
            !clientName.trim()
          }
          className="bg-[#C9A24D] cursor-pointer hover:bg-amber-500 disabled:opacity-30 rounded-xl px-4 py-2 text-white"
          aria-label="Prenota"
        >
          Prenota
        </button>
      </div>
    </div>
  );
};
