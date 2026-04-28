import React, { useEffect } from "react";
import { useService } from "../stores/serviceStore";
import ServiceCard from "./components/serviceCard";

export const Service = () => {
  const serviceStore = useService();
  useEffect(() => {
    serviceStore.findAllService();
  }, []);

  return (
    <div className="p-4 flex flex-col items-center md:grid md:grid-cols-4 md:gap-4 gap-2 w-full">
      {serviceStore.findAllServiceResponse.length ? (
        serviceStore.findAllServiceResponse.map((item) => (
          <ServiceCard
            key={item.id}
            id={item.id}
            name={item.name}
            duration={item.duration}
            price={item.price}
            imageUrl={item.imagePath}
          />
        ))
      ) : (
        <h1>
          Ehi! Sei arrivato un po' troppo presto. Non abbiamo preparato ancora i
          nostri servizi 😃
        </h1>
      )}
    </div>
  );
};
