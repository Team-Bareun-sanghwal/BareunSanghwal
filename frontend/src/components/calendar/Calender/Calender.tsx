'use client';
import { Streak } from '../Streak/Streak';
import { DayLabel } from '../DayLabel/DayLabel';
import { Achievement } from '../Acheivement/Achievement';
import { MonthLabel } from '../MonthLabel/MonthLabel';
import { HabitBtnList } from '../HabitBtnList/HabitBtnList';
import { $Fetch } from '@/apis';
import { getYear, getMonth, convertMonthFormat } from '../util';
import { ThemeColor } from '../CalenderConfig';
import { IMemberHabit, IDayInfo, setDayInfo } from '@/app/mock';
import { useEffect, useState } from 'react';

const getStreaks = async (year: number, month: number, habitId: number) => {
  const response = await $Fetch({
    method: 'GET',
    url:
      habitId > 0
        ? `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(month)}/${habitId}`
        : `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(month)}`,
    cache: 'default',
  });
  return response;
};

const getHabits = async (year: number, month: number) => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/month/${year}-${convertMonthFormat(month)}`,
    cache: 'default',
  });
  return response;
};
export const Calender = ({ themeColor }: { themeColor: ThemeColor }) => {
  const [year, setYear] = useState<number>(parseInt(getYear()));
  const [month, setMonth] = useState<number>(parseInt(getMonth(false)));
  const [habitId, setHabitId] = useState<number>(-1);
  const [achieveProportion, setAchieveProportion] = useState<number>(0);
  const [days, setDays] = useState<IDayInfo[]>([]);
  const [memberHabitDtoList, setMemberHabitDtoList] = useState<IMemberHabit[]>(
    [],
  );

  useEffect(() => {
    console.log(parseInt(getYear()) === year && parseInt(getMonth(false)) === month)
    getStreaks(year, month, habitId)
      .then((response) => {
        console.log(response.data);
        const { achieveProportion, dayInfo, dayOfWeekFirst } = response.data;
        setAchieveProportion(achieveProportion);
        setDays(setDayInfo(dayInfo, dayOfWeekFirst,(
          parseInt(getYear()) === year && parseInt(getMonth(false)) === month
        ))
        );
      })
      .catch(() => {
        setAchieveProportion(0);
        setDays([]);
      });
  }, [year, month, habitId]);

  useEffect(() => {
    getHabits(year, month)
      .then((response) => {
        console.log(response.data);
        const { memberHabitDtoList } = response.data;
        setMemberHabitDtoList(memberHabitDtoList);
      })
      .catch(() => {
        setMemberHabitDtoList([]);
      });
  }, [year, month]);

  const isUnique =
    themeColor === 'dippindots' ||
    themeColor === 'rainbow' ||
    themeColor === 'rose' ||
    themeColor === 'sunny_summer';
  return (
    <>
      <MonthLabel
        month={month}
        year={year}
        setMonth={setMonth}
        setYear={setYear}
      />
      <HabitBtnList
        habitId={habitId}
        setHabitId={setHabitId}
        memberHabitDtoList={memberHabitDtoList}
      />
      <Achievement proportion={achieveProportion} themeColor={themeColor} />
      <DayLabel />
      <div className="grid grid-cols-7 gap-4 p-1 m-2.5">
        {days.map((info, index) =>
          info.dayNumber < 0 ? (
            <div key={index}></div>
          ) : (
            <Streak
              key={index}
              themeColor={themeColor}
              achieveCount={info.achieveCount}
              achieveType={info.achieveType}
              year={year}
              month={month}
              dayNumber={info.dayNumber}
              isUnique={isUnique}
              totalCount={info.totalCount}
              habitId={habitId}
            />
          ),
        )}
      </div>
    </>
  );
};
