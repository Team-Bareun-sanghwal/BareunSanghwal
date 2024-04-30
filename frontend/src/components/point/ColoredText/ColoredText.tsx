import { ThemeColor } from '@/components/calendar/CalenderConfig';

const ColoredText = ({ color }: { color: ThemeColor }) => {
  const isUnique =
    color === 'dippindots' ||
    color === 'rainbow' ||
    color === 'rose' ||
    color === 'sunny_summer';
  const customClassName = `text-2xl font-bold mr-2 text-streak-${color}${isUnique ? '-4' : ''}`;

  return (
    <div className="flex w-full">
      {/* {isUnique && <p className="">와우!</p>} */}
      <p className={customClassName}>#{color}</p>
      <p className="text-2xl">색상을 획득했어요!</p>
    </div>
  );
};
export default ColoredText;
