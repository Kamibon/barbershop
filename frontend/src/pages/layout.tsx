import React, { type ReactElement } from "react";
import { FaHome } from "react-icons/fa";
import { IoIosPeople } from "react-icons/io";
import { PiOfficeChairFill } from "react-icons/pi";
import { MdOutlineLocalLibrary } from "react-icons/md";

interface Props {
  children: ReactElement[] | ReactElement;
  onChangeTab: (index: number) => void;
}

export const Layout = (props: Props) => {
  const { children, onChangeTab } = props;

  return (
    <div className="flex flex-col items-center justify-center">
      <div className=" bg-linear-to-b from-[#0F0F0F] to-[#C9A24D] w-full h-30 flex items-center justify-around">
        <FaHome
          onClick={() => onChangeTab(0)}
          className="lg:hidden cursor-pointer hover:text-amber-500 size-6"
        />
        <span
          onClick={() => onChangeTab(0)}
          className="text-2xl hidden lg:block text-white cursor-pointer hover:text-amber-500"
        >
          Homepage
        </span>
        <PiOfficeChairFill
          onClick={() => onChangeTab(1)}
          className="lg:hidden cursor-pointer hover:text-amber-500 size-6"
        />
        <span
          onClick={() => onChangeTab(1)}
          className="text-2xl hidden lg:block text-white cursor-pointer hover:text-amber-500"
        >
          I nostri servizi
        </span>
        <MdOutlineLocalLibrary
          onClick={() => onChangeTab(2)}
          className="lg:hidden cursor-pointer hover:text-amber-500 size-6"
        />
        <span
          onClick={() => onChangeTab(2)}
          className="text-2xl hidden lg:block text-white cursor-pointer hover:text-amber-500"
        >
          Prenota
        </span>
        <IoIosPeople
          onClick={() => onChangeTab(3)}
          className="lg:hidden cursor-pointer hover:text-amber-500 size-6"
        />
        <span
          onClick={() => onChangeTab(3)}
          className="text-2xl hidden lg:block text-white cursor-pointer hover:text-amber-500"
        >
          Chi siamo
        </span>
      </div>
      {children}
    </div>
  );
};
