import { ThemeColor } from '@/components/calender/CalenderConfig';
import Streak from '@/components/calender/Streak/Streak';

const Pallete = ({ color }: { color: ThemeColor }) => {
  const isUnique =
    color === 'dippindots' ||
    color === 'rainbow' ||
    color === 'rose' ||
    color === 'sunny_summer';
  return (
    <div className="flex h-10 gap-2 p-1 m-2.5 height">
      {Array.from({ length: 7 }, (_, i) => (
        <Streak
          themeColor={color}
          isUnique={isUnique}
          achieveCount={i}
          key={i}
        />
      ))}
    </div>
  );
};
export default Pallete;
