import { ThemeColor } from '@/components/calendar/CalenderConfig';
import { Streak } from '@/components/calendar/Streak/Streak';

const Pallete = ({ color }: { color: ThemeColor }) => {
  const isUnique =
    color === 'dippindots' ||
    color === 'rainbow' ||
    color === 'rose' ||
    color === 'sunny_summer';
  return (
    <div className="grid grid-cols-7 h-20 gap-8 p-1">
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
