type ThemeColor = keyof typeof colors;

interface IAchievement {
  proportion: number;
  themeColor: ThemeColor;
}

const Achievement = ({ proportion, themeColor }: IAchievement) => {
  const bgColor = colors[themeColor];
  const progressBarStyle = {
    width: `${proportion}%`,
    backgroundColor: bgColor,
  };

  return (
    <div className="flex my-1 mx-2">
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

const colors = {
  red: '#FF2725',
  purple: '#94439e',
  indigo: '#5F3DC5',
  blue: '#364FC6',
  green: '#2C8A3E',
  yellow: '#E67701',
  orange: '#D9480F',
  black: '#3E3E3E',
  wine: '#CF296F',
  gold: '#D38200',
  bareunSanghwal: '#15481B',
  minchodan: '#3EB489',
  cherryBlossom: '#FF8780',
  react: '#5ED3F3',
  spring: '#6AAD3D',
  dippindots: '#FFFDDE',
  rainbow: '#C92B2A',
  rose: '#3E3E3E',
  streak_sunny_summer: '#0F4267',
};
