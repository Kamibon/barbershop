import React, { type ReactNode } from "react";

interface Props {
  isOpen: boolean;
  title: string;
  body?: ReactNode;
  onConfirm: () => void;
  onCancel: () => void;
}

const Modal = ({ isOpen, title, body, onConfirm, onCancel }: Props) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white rounded-lg p-6 max-w-md w-full text-center shadow-lg">
        <h2 className="text-xl font-semibold mb-4">{title}</h2>
        {body}
        <div className="flex justify-around">
          <button
            className="bg-green-500 hover:bg-green-600 text-white font-semibold px-6 py-2 rounded transition"
            onClick={onConfirm}
          >
            Procedi
          </button>
          <button
            className="bg-red-500 hover:bg-red-600 text-white font-semibold px-6 py-2 rounded transition"
            onClick={onCancel}
          >
            Annulla
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
