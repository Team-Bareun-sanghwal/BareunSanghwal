import { ThemeColor, streakColors } from '../CalenderConfig';

interface IAchievement {
  proportion: number;
  themeColor: ThemeColor;
}
export const Achievement = ({ proportion, themeColor }: IAchievement) => {
  const bgColor = streakColors[themeColor];
  console.log;
  const progressBarStyle = {
    width: `${proportion}%`,
    backgroundColor: bgColor,
  };

  return (
    <div className="flex w-full my-2 mx-2">
      <label className="w-2/12 text-center">🔥 달성률</label>
      <div className="bg-custom-medium-gray mr-4 w-full rounded-xl z-1 overflow-hidden text-center relative">
        <div className="absolute w-full z-3 text-custom-white">
          {proportion}%
        </div>
        <div style={progressBarStyle} className="h-full z-2"></div>
      </div>
    </div>
  );
};
