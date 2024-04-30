'use client';

import { useEffect, useState } from 'react';
import Picker from 'react-mobile-picker-scroll';

interface IDataProps {
  birthDay: string;
  setBirthDay: (newDate: string) => void;
}

export const ScrollDatePicker = ({ birthDay, setBirthDay }: IDataProps) => {
  // 년, 월, 일 배열 제작
  const yearArr = [];
  for (let i = 1900; i <= new Date().getFullYear(); i += 1) {
    yearArr.push(i);
  }
  const monthArr = [];
  for (let i = 1; i <= 12; i += 1) {
    monthArr.push(i);
  }
  const dayArr = [];
  for (let i = 1; i <= 31; i += 1) {
    dayArr.push(i);
  }

  const [valueGroups, setValueGroups] = useState({
    year: 2024,
    month: 1,
    day: 1,
  });

  const optionGroups = {
    year: yearArr,
    month: monthArr,
    day: dayArr,
  };

  useEffect(() => {
    const birthdayArr = birthDay.split('-');

    setValueGroups({
      year: parseInt(birthdayArr[0]),
      month: parseInt(birthdayArr[1]),
      day: parseInt(birthdayArr[2]),
    });
    setBirthDay(`${birthdayArr[0]}-${birthdayArr[1]}-${birthdayArr[2]}`);
  }, [birthDay, setBirthDay]);

  const handleChange = (name: string, value: number) => {
    setValueGroups({
      ...valueGroups,
      [name]: value,
    });
    setBirthDay(`${valueGroups.year}-${valueGroups.month}-${valueGroups.day}`);
  };

  return (
    <div className="w-full">
      <Picker
        optionGroups={optionGroups}
        valueGroups={valueGroups}
        onChange={(name: string, value: number) => handleChange(name, value)}
      />
    </div>
  );
};
