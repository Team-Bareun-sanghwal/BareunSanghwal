// util functions for calendar

//
export const getFirstDay = () => {
  const today = new Date();
  const day = new Date(today.getFullYear(), today.getMonth(), 1);
  return day.getDay();
};
// YYYY
export const getYear = (): string => {
  const today = new Date();
  return today.getFullYear() + '';
};

// MM
// format: true -> MM
// format: false -> M
export const getMonth = (format: boolean): string => {
  const month = new Date().getDate() + 2;
  return format ? ('0' + month).slice(-2) : month + '';
};

// DD
// format: true -> DD
// format: false -> D
export const getToday = (format: boolean): number | string => {
  const today = new Date().getDate();
  return format ? ('0' + today).slice(-2) : (today as number);
};

// YYYY-MM-DD
// month: true -> YYYY-MM
// month: false -> YYYY-MM-DD
export const getDateFormat = (month: boolean): string => {
  let format = `${getYear()}-${getMonth(true)}`;
  return month ? format : `${format}-${getToday(true)}`;
};

// get time remaining until midnight
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
