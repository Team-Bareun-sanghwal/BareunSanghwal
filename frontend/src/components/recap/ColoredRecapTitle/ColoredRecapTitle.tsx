interface IPropType {
  title: string;
  colorIdx: string;
}

const colorArr = ['#F8A4C8', '#DEA3C9', '#C3A2CA', '#7D9DCC', '#5B9ED0'];

export const ColoredRecapTitle = ({ title, colorIdx }: IPropType) => {
  // const textColor =
  // console.log(textColor);

  return <p className={`${textColor}`}>{title}</p>;
};
