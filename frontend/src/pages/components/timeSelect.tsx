import React from "react";

const generateTimes = (
  startHour: number = 9,
  endHour: number = 18,
  intervalMinutes: number = 45,
): string[] => {
  const times: string[] = [];

  let hour: number = startHour;
  let minute: number = 0;

  while (hour < endHour || (hour === endHour && minute === 0)) {
    const formattedTime = `${String(hour).padStart(2, "0")}:${String(
      minute,
    ).padStart(2, "0")}`;
    times.push(formattedTime);

    minute += intervalMinutes;

    if (minute >= 60) {
      hour += Math.floor(minute / 60);
      minute = minute % 60;
    }
  }

  return times;
};

interface TimeSelectProps {
  value: string;
  onChange: (value: string) => void;
  disabled?: boolean;
}

const TimeSelect: React.FC<TimeSelectProps> = ({
  value,
  onChange,
  disabled = false,
}) => {
  const times: string[] = generateTimes();

  return (
    <select
      className="  bg-white border border-brand-primary rounded-2xl p-2 text-lg"
      value={value}
      onChange={(e: React.ChangeEvent<HTMLSelectElement>) =>
        onChange(e.target.value)
      }
      disabled={disabled}
    >
      <option value="">Seleziona un orario</option>

      {times.map((time) => (
        <option key={time} value={time}>
          {time}
        </option>
      ))}
    </select>
  );
};

export default TimeSelect;
