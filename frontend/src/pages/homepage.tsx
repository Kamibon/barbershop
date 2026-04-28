import img from "../assets/home.jpg";
import img1 from "../assets/img1.jpg";
import img2 from "../assets/img2.jpg";
import img3 from "../assets/img3.jpg";

const openMaps = () => {
  const place = "Colosseo Roma";
  const url = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
    place,
  )}`;
  window.open(url, "_blank");
};

export const Homepage = () => {
  return (
    <div className="w-full pb-8">
      <img className="w-full h-125 mb-4" alt="Immagine del locale" src={img} />
      <div className="flex flex-col gap-1 items-center justify-center">
        <h1 className="text-brand-secondary text-3xl">BARBERSHOP</h1>
        <h2 className="text-brand-primary text-xl">Diamoci un taglio!</h2>
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
      </div>
      <div className="flex flex-row items-center justify-between md:justify-center gap-6 px-4 py-2">
        <img className=" w-[30%] h-40 md:h-80" src={img1} />
        <img className=" w-[30%] h-40 md:h-80 md:mt-8 " src={img3} />
        <img className=" w-[30%] h-40 md:h-80" src={img2} />
      </div>
    </div>
  );
};
