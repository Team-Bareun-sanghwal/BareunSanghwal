interface IPropType {
  title: string;
  colorIdx: 0 | 1 | 2 | 3 | 4;
}

export const ColoredRecapTitle = ({ title, colorIdx }: IPropType) => {
  let textColor = '';
  let textAlign = '';
  switch (colorIdx) {
    case 0:
      textColor = 'text-[#F8A4C8]';
      textAlign = 'ml-auto';
      break;
    case 1:
      textColor = 'text-[#DEA3C9]';
      textAlign = 'mr-auto';
      break;
    case 2:
      textColor = 'text-[#C3A2CA]';
      textAlign = 'mx-auto';
      break;
    case 3:
      textColor = 'text-[#7D9DCC]';
      textAlign = 'mr-auto';
      break;
    case 4:
      textColor = 'text-[#5B9ED0]';
      textAlign = 'ml-auto';
      break;
  }

  return (
    <p className={`${textColor} ${textAlign} custom-semibold-text text-center`}>
      {title}
    </p>
  );
};
