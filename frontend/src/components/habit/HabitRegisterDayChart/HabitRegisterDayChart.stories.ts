import type { Meta, StoryObj } from '@storybook/react';
import { HabitRegisterDayChart } from './HabitRegisterDayChart';

const meta = {
  title: 'Habit/HabitRegisterDayChart',
  component: HabitRegisterDayChart,
  tags: ['autodocs'],
  argTypes: {
    habitRegisterDayList: {
      description:
        '영어 요일 이름과 요일마다 등록한 사용자 수를 담은 객체의 배열',
    },
  },
} satisfies Meta<typeof HabitRegisterDayChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    dayOfWeek: [1, 2],
    setDayOfWeek: () => {},
    setPeriod: () => {},
    habitRegisterDayList: [
      { englishDayName: 'monday', registerCount: 1024 },
      { englishDayName: 'tuesday', registerCount: 2048 },
      { englishDayName: 'wednesday', registerCount: 3024 },
      { englishDayName: 'thursday', registerCount: 114 },
      { englishDayName: 'friday', registerCount: 1540 },
      { englishDayName: 'saturday', registerCount: 1223 },
      { englishDayName: 'sunday', registerCount: 15 },
    ],
  },
};
