export const convertBirthday = (birthday: string) => {
  const birthdayArr = birthday.split('-');
  return `${birthdayArr[0]}-${birthdayArr[1].padStart(2, '0')}-${birthdayArr[2].padStart(2, '0')}`;
};
