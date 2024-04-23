import { ThemeColor, streakColors } from '../CalenderConfig';

interface IAchievement {
  proportion: number;
  themeColor: ThemeColor;
}
const Achievement = ({ proportion, themeColor }: IAchievement) => {
  const bgColor = streakColors[themeColor];
  const progressBarStyle = {
    width: `${proportion}%`,
    backgroundColor: bgColor,
  };

  return (
    <div className="flex w-full my-1 mx-2">
      <label className="w-2/12 text-center">🔥 달성률</label>
      <div className="bg-custom-medium-gray mr-4 w-full rounded-xl z-10 overflow-hidden text-center relative">
        <div className="absolute w-full z-30 text-custom-white">
          {proportion}%
        </div>
        <div style={progressBarStyle} className="h-full z-20"></div>
      </div>
    </div>
  );
};

export default Achievement;
