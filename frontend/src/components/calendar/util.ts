/**
 * @fileoverview util functions for calendar
 * @module calendar/util
 * @requires none
 * @exports Time
 * @exports Today
 * @exports LastDay
 * @exports getFirstDay
 * @exports getYear
 * @exports getMonth
 * @exports convertMonthFormat
 * @exports getToday
 * @exports getDateFormat
 * @exports getTimeRemaining
 */

export interface IStreaksReponse {
  achieveProportion: number;
  dayOfWeekFirst: number;
  memberHabitList: IMemberHabit[];
  dayInfo: IDayInfo[];
}
export interface IMemberHabit {
  memberHabitId: number;
  alias: string;
  icon: string;
}
export interface IDayInfo {
  day: number;
  achieveCount: number;
  totalCount: number;
}
export const Time = () => {
  const hour = new Date().getHours();
  if (hour < 5 || hour >= 20) return 'night';
  if (hour < 7) return 'midnight';
  if (hour < 10) return 'morning';
  if (hour < 17) return 'lunch';
  else return 'dinner';
};

const Today = () => {
  return new Date().getDate();
};

const LastDay = () => {
  const now = new Date();
  const month = now.getMonth();
  const year = now.getFullYear();
  return new Date(year, month + 1, 0).getDate();
};

export const getFirstDay = () => {
  const today = new Date();
  const day = new Date(today.getFullYear(), today.getMonth(), 1);
  return day.getDay();
};

export const getYear = (): string => {
  const today = new Date();
  return today.getFullYear() + '';
};

export const getMonth = (format: boolean): string => {
  const month = new Date().getMonth() + 1;
  return format ? convertMonthFormat(month) : month + '';
};

export const convertMonthFormat = (month: number): string => {
  return ('0' + month).slice(-2);
};

export const getToday = (format: boolean): string => {
  const today = new Date().getDate();
  return format ? ('0' + today).slice(-2) : today + '';
};

export const getDateFormat = (month: boolean): string => {
  let format = `${getYear()}-${getMonth(true)}`;
  return month ? format : `${format}-${getToday(true)}`;
};

export const getTimeRemaining = (): {
  hoursRemaining: number;
  minutesRemaining: number;
} => {
  const now = new Date();
  const midnight = new Date();
  midnight.setHours(24, 0, 0, 0);

  const time = midnight.getTime() - now.getTime();

  const hoursRemaining = Math.floor(time / (1000 * 60 * 60));
  const minutesRemaining = Math.floor((time % (1000 * 60 * 60)) / (1000 * 60));

  return { hoursRemaining, minutesRemaining };
};

export const setDayInfo = (
  dayInfo: IDayInfo[],
  dayOfWeekFirst: number,
): IDayInfo[] => {
  let dayInfoList: IDayInfo[] = [];
  const today = Today();

  for (let i = 0; i < dayOfWeekFirst; i++) {
    dayInfoList.push({ day: -1, achieveCount: 0, totalCount: 0 });
  }
  for (let i = 1; i <= today; i++) {
    const existingDayInfo = dayInfo.find((info) => info.day === i);
    if (existingDayInfo) {
      dayInfoList.push(existingDayInfo);
    } else {
      dayInfoList.push({ day: i, achieveCount: 0, totalCount: 0 });
    }
  }
  for (let i = today + 1; i <= LastDay(); i++) {
    dayInfoList.push({ day: i, achieveCount: 0, totalCount: 0 });
  }
  return dayInfoList;
};
