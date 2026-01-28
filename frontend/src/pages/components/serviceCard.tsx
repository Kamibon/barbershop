import React, { useState, useCallback } from "react";
import { bUrl } from "../../App";
import { PiClock, PiMoney } from "react-icons/pi";

interface Props {
  id: number;
  name: string;
  duration: number;
  price: number;
  imageUrl: string;
}

const ServiceCard = (props: Props) => {
  const { id, name, duration, price, imageUrl } = props;
  const [tilt, setTilt] = useState({ x: 0, y: 0 });

  const threshold = 12;

  const handleMove = useCallback(
    (e) => {
      const { left, top, width, height } =
        e.currentTarget.getBoundingClientRect();
      const x = (e.clientX - left) / width - 0.5;
      const y = (e.clientY - top) / height - 0.5;
      setTilt({ x: y * -threshold, y: x * threshold });
    },
    [threshold],
  );

  const resetTilt = () => {
    setTilt({ x: 0, y: 0 });
  };

  const cardStyle = {
    transform: `perspective(1000px) rotateX(${tilt.x}deg) rotateY(${tilt.y}deg)`,
    transition: "transform 200ms ease-out",
    cursor: "pointer",
    backgroundColor: "white",
    borderRadius: "0.75rem",
    boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
  };

  return (
    <div
      className="card"
      style={cardStyle}
      onMouseMove={handleMove}
      onMouseLeave={resetTilt}
    >
      <img
        src={imageUrl ? `${bUrl}${imageUrl}` : "/hair.jpg"}
        alt="City skyline"
        className="w-full h-52 object-cover rounded-t-2xl"
      />
      <h3 className="mt-3 px-4 mb-1 text-lg font-semibold text-gray-800">
        {name}
      </h3>
      <div className="px-4 pb-2">
        <div className="flex items-center gap-2 text-gray-600">
          <PiClock />
          <span>{duration} min</span>
        </div>
        <div className="flex items-center gap-2 text-gray-600">
          <PiMoney />
          <span>{price}</span>
        </div>
      </div>
    </div>
  );
};

export default ServiceCard;
