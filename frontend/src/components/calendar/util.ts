interface ITimeRemainig {
  hoursRemaining: number;
  minutesRemaining: number;
}
export const getToday = () => {
  const today = new Date();
  var day = ('0' + today.getDate()).slice(-2);
  return day;
};

export const getThisMonth = () => {
  const today = new Date();
  const month =
    today.getMonth() < 10 ? `0${today.getMonth() + 1}` : today.getMonth() + 1;
  return month + '';
};

export const getThisYear = () => {
  const today = new Date();
  const year = today.getFullYear();
  return year + '';
};

export const getTimeRemaining = (): ITimeRemainig => {
  const now = new Date();
  const midnight = new Date();
  midnight.setHours(24, 0, 0, 0);

  const time = midnight.getTime() - now.getTime();

  const hoursRemaining = Math.floor(time / (1000 * 60 * 60));
  const minutesRemaining = Math.floor((time % (1000 * 60 * 60)) / (1000 * 60));

  return { hoursRemaining, minutesRemaining };
};
