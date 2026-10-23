import { useEffect, useState } from "react";
import { createAppointment } from "../api/appointments";

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

export const useAppointmentForm = () => {
  const [chosenDate, setChosenDate] = useState<Value>(new Date());
  const [chosenBarber, setChosenBarber] = useState<number | null>(null);
  const [chosenTime, setChosenTime] = useState<string>("");
  const [phoneNumber, setPhoneNumber] = useState<string | null>(null);
  const [chosenService, setChosenService] = useState<number | null>(null);
  const [clientName, setClientName] = useState<string>("");
  const [success, setSuccess] = useState(false);
  const [failed, setFailed] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const regexPhone = /^\+?(\([0-9]{3}\))?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/;

  const valid =
    chosenDate !== null &&
    chosenBarber !== null &&
    chosenService !== null &&
    chosenTime.trim().length > 0 &&
    clientName.trim().length > 0 &&
    phoneNumber &&
    regexPhone.test(phoneNumber);


  const resetForm = ()=>{
    setChosenDate(new Date())
    setChosenBarber(null)
    setChosenService(null)
    setClientName("")
    setPhoneNumber("")
    setChosenTime("")
  }


  const handleSubmit = async () => {
    if (submitting) return;

    setSubmitting(true);
    setSuccess(false);
    setFailed(false);

    if (valid)
      try {
        const res = await createAppointment({
          barberId: chosenBarber!,
          clientName: clientName.trim(),
          date: chosenDate as Date,
          dateTime: chosenTime,
          phoneNumber: phoneNumber!,
          serviceId: chosenService!,
        });

        if (res.status === 201) {
          setSuccess(true);
        } else {
          setFailed(true);
        }
      } catch {
        setFailed(true);
      } finally {
        setSubmitting(false);
      }
  };

  useEffect(() => {
    let id: number;
    if (success || failed) {
      id = setTimeout(() => {
        setSuccess(false);
        setFailed(false);
        resetForm()
      }, 5000);
      return () => clearTimeout(id);
    }
  }, [success, failed]);

  const appointmentForm = {
    chosenDate,
    setChosenDate,
    chosenBarber,
    setChosenBarber,
    chosenTime,
    setChosenTime,
    phoneNumber,
    setPhoneNumber,
    chosenService,
    setChosenService,
    clientName,
    setClientName,
    success,
    setSuccess,
    failed,
    setFailed,
    submitting,
    setSubmitting,
    valid,
    handleSubmit,
  };

  return appointmentForm;
};
