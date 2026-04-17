import React from "react";
import img1 from "../assets/img1.jpg";

export const WhoAreWe = () => {
  return (
    <div className="flex flex-col p-4">
      <span className="text-[#2B2B2B] text-md lg:text-3xl text-center">
        Ciao! Benvenuto sul sito di BarberShop. Siamo tre giovani barbieri che
        puoi trovare a Pincolandia, in Via Tizio, 47. Lascia che ci
        presentiamo!☺️
      </span>
      <div className=" flex flex-col md:flex-row items-center md:items-start gap-4 mt-6">
        <img src={img1} className="w-80 h-80 rounded-2xl" />
        <div className="flex flex-col items-center gap-2 p-4">
          <span className="text-[#0F0F0F] font-bold text-xl">Mario</span>
          <span className="text-[#2B2B2B] text-center text-base lg:text-lg">
            Mario ha 10 anni di esperienza e una passione per i tagli eleganti e
            senza tempo. Ama consigliare lo stile giusto per ogni cliente e
            trasformare una semplice visita in un momento di relax.
          </span>
        </div>
      </div>
      <div className=" flex flex-col md:flex-row-reverse items-center md:items-start gap-4 mt-6">
        <img src={img1} className="w-80 h-80 rounded-2xl" />
        <div className="flex flex-col items-center gap-2 p-4">
          <span className="text-[#0F0F0F] font-bold text-xl">Luigi</span>
          <span className="text-[#2B2B2B] text-center text-base lg:text-lg">
            Giovane e creativo, Luigi è sempre aggiornato sulle ultime tendenze.
            Perfetto per chi vuole un look alla moda o un tocco unico. La sua
            attenzione ai dettagli è ciò che rende ogni taglio impeccabile.
          </span>
        </div>
      </div>
      <div className=" flex flex-col md:flex-row items-center md:items-start gap-4 mt-6">
        <img src={img1} className="w-80 h-80 rounded-2xl" />
        <div className="flex flex-col items-center gap-2 p-4">
          <span className="text-[#0F0F0F] font-bold text-xl">Paolo</span>
          <span className="text-[#2B2B2B] text-center text-base lg:text-lg">
            Paolo è il maestro della rasatura tradizionale con pennello e lama.
            Con pazienza e precisione, offre un’esperienza rilassante e
            rigenerante per chi ama prendersi cura di barba e viso.
          </span>
        </div>
      </div>
    </div>
  );
};
