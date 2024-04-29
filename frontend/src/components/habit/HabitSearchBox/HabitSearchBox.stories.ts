import type { Meta, StoryObj } from '@storybook/react';
import { HabitSearchBox } from './HabitSearchBox';

const meta = {
  title: 'Habit/HabitSearchBox',
  component: HabitSearchBox,
  tags: ['autodocs'],
  argTypes: {
    searchedList: { description: '검색된 목록' },
  },
} satisfies Meta<typeof HabitSearchBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    searchedList: [
      { name: '운동하기', habitId: 1 },
      { name: '생활 운동', habitId: 2 },
    ],
  },
};
