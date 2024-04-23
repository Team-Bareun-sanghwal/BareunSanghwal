import type { Config } from 'tailwindcss';

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/**/*.{js,ts,jsx,tsx,mdx}',
  ],
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
        'custom-black-with-opacity': 'rgba(39, 39, 42, 0.8)',
        'custom-matcha-with-opacity': 'rgba(21, 72, 27, 0.8)',
        'custom-kakao': '#fee500',
        'custom-google': '#ffffff',
      },
    },
  },
  plugins: [],
};
export default config;
