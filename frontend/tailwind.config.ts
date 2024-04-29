import type { Config } from 'tailwindcss';

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  safelist: generateStreakColorSafelist(),
  theme: {
    extend: {
      colors: {
        'custom-white': '#ffffff',
        'custom-black': '#27272a',
        'custom-matcha': '#15481b',
        'custom-green': '#277530',
        'custom-yellow-green': '#5bb227',
        'custom-light-green': '#9bdea2',
        'custom-light-gray': '#edeef7',
        'custom-purple': '#94439e',
        'custom-dark-gray': '#52525b',
        'custom-medium-gray': '#d4d4d8',
        'custom-error': '#cd2626',
        'custom-warning': '#fee500',
        'custom-success': '#65f54d',
        'custom-pink': '#db719e',
        'custom-sky': '#5a9ed0',
        'custom-sky-pastel': '#EDEEF7',
        'custom-black-with-opacity': 'rgba(39, 39, 42, 0.8)',
        'custom-matcha-with-opacity': 'rgba(21, 72, 27, 0.8)',
        'custom-kakao': '#FEE500',
        'custom-google': '#FFFFFF',

        // Streak colors

        'streak-none': '#D4D4D8',
        // common
        'streak-red': '#FF2725',
        'streak-purple': '#94439e',
        'streak-indigo': '#5F3DC5',
        'streak-blue': '#364FC6',
        'streak-green': '#2C8A3E',
        'streak-yellow': '#E67701',
        'streak-orange': '#D9480F',
        'streak-black': '#3E3E3E',

        // rare
        'streak-wine': '#CF296F',
        'streak-gold': '#D38200',
        'streak-bareun_sanghwal': '#15481B',
        'streak-minchodan': '#3EB489',
        'streak-cherry_blossom': '#FF8780',
        'Streak-react': '#5ED3F3',
        'streak-spring': '#6AAD3D',

        //unique
        'streak-dippindots': {
          '1': '#FFFDDE',
          '2': '#E7FBBE',
          '3': '#D9D7F1',
          '4': '#9ADCFF',
          '5': '#FFF89A',
          '6': '#FFB2A6',
          '7': '#FF8AAE',
        },
        'streak-rainbow': {
          '1': '#C92B2A',
          '2': '#D9480F',
          '3': '#E67701',
          '4': '#2C8A3E',
          '5': '#364FC6',
          '6': '#5F3DC5',
          '7': '#862E9C',
        },
        'streak-rose': {
          '1': '#3E3E3E',
          '2': '#AA0000',
          '3': '#F70031',
          '4': '#F73D3A',
          '5': '#B20025',
          '6': '#F75273',
          '7': '#F7A6B6',
        },
        'streak-sunny_summer': {
          '1': '#0F4267',
          '2': '#5B87A7',
          '3': '#E0E0E0',
          '4': '#BDAE9F',
          '5': '#A88D72',
          '6': '#FFD362',
          '7': '#FFB800',
        },
      },
    },
  },
  plugins: [],
};
function generateStreakColorSafelist() {
  const streakColors = [
    'red',
    'purple',
    'indigo',
    'blue',
    'green',
    'yellow',
    'orange',
    'black',
    'wine',
    'gold',
    'bareun_sanghwal',
    'minchodan',
    'cherry_blossom',
    'react',
    'spring',
    'dippindots',
    'rainbow',
    'rose',
    'sunny_summer',
  ];
  const dayVariations = Array.from({ length: 7 }, (_, i) => i + 1);
  const opacities = [10, 40, 55, 60, 70, 80, 90, 100];

  let safelist: string[] = [];

  streakColors.forEach((color) => {
    if (
      color === 'dippindots' ||
      color === 'rainbow' ||
      color === 'rose' ||
      color === 'sunny_summer'
    ) {
      // Unique Colors
      dayVariations.forEach((day) => {
        opacities.forEach((opacity) => {
          safelist.push(
            `bg-streak-${color}-${day} opacity-${opacity} text-white text-xl aspect-square rounded-lg relative`,
          );
        });
      });
    } else {
      // Common/Rare Colors
      opacities.forEach((opacity) => {
        safelist.push(
          `bg-streak-${color} opacity-${opacity} text-white text-xl aspect-square rounded-lg relative`,
        );
      });
    }
  });
  return safelist;
}
export default config;
