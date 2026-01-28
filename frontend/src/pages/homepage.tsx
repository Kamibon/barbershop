import React from "react";
import img from "../assets/home.jpg";
import img1 from "../assets/img1.jpg";
import img2 from "../assets/img2.jpg";
import img3 from "../assets/img3.jpg";

const openMaps = () => {
  const place = "Colosseo Roma";
  const url = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
    place
  )}`;
  window.open(url, "_blank");
};

export const Homepage = () => {
  return (
    <>
      <img className="w-full h-125 mb-4" alt="Immagine del locale" src={img} />
      <h1 className="text-[#0F0F0F] text-3xl">BARBERSHOP</h1>
      <h2 className="text-[#C9A24D] text-xl">Diamoci un taglio!</h2>
      <div className="flex gap-2 items-center">
        <span className="text-red-500 animate-bounce">!!</span>
        <span
          onClick={() => openMaps()}
          className="cursor-pointer font-semibold"
        >
          Via Tizio, 47
        </span>
        <span className="text-red-500 animate-bounce">!!</span>
      </div>
      <div className="flex flex-row items-center gap-8">
        <img className=" w-[30%] h-80" src={img1} />
        <img className=" w-[30%] h-80 mt-8" src={img3} />
        <img className=" w-[30%] h-80" src={img2} />
      </div>
    </>
  );
};
