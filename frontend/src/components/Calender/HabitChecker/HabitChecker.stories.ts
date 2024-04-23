import type { Meta, StoryObj } from '@storybook/react';
import HabitChecker from './HabitChecker';
const meta = {
  title: 'Main/HabitChecker',
  component: HabitChecker,
  tags: ['autodocs'],
  parameters: {},
  argTypes: {
    achieveCount: { description: '오늘 달성한 해빗 갯수' },
    totalCount: { description: '오늘의 해빗 갯수' },
  },
} satisfies Meta<typeof HabitChecker>;

export default meta;
type Story = StoryObj<typeof meta>;

export const NoHabit: Story = {
  args: {
    achieveCount: 0,
    totalCount: 0,
  },
};
export const Ongoing: Story = {
  args: {
    achieveCount: 1,
    totalCount: 3,
  },
};
export const Complete: Story = {
  args: {
    achieveCount: 3,
    totalCount: 3,
  },
};
