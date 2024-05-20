import type { Meta, StoryObj } from '@storybook/react';
import { HabitDayChart } from './HabitDayChart';

const meta = {
  title: 'Habit/HabitDayChart',
  component: HabitDayChart,
  tags: ['autodocs'],
  argTypes: {
    habitList: {
      description: '해빗 이름, 아이디, 요일 정보가 담긴 객체의 배열',
    },
  },
} satisfies Meta<typeof HabitDayChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    habitList: [
      {
        name: '물 2L 마시기',
        habitId: 1,
        habitDayList: [2, 4, 7],
      },
      {
        name: '프로틴 주스 마시기',
        habitId: 2,
        habitDayList: [3, 4, 6],
      },
    ],
  },
};
