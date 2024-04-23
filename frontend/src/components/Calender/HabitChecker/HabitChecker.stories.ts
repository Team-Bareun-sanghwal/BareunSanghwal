import type { Meta, StoryObj } from '@storybook/react';
import HabitChecker from './HabitChecker';
const meta = {
  title: 'Calender/HabitChecker',
  component: HabitChecker,
  tags: ['autodocs'],
} satisfies Meta<typeof HabitChecker>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Story: Story = {
  args: {
    habit: '운동하기',
    date: '2021-10-01',
    onCheck: () => {
      console.log('heeey');
    },
  },
};
