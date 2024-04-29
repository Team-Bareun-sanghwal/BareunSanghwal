import type { Meta, StoryObj } from '@storybook/react';
import { RecapPieChart } from './RecapPieChart';

const meta = {
  title: 'Recap/RecapPieChart',
  component: RecapPieChart,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    rateByHabitList: { description: 'rateByHabitList 데이터' },
    mostSuccessedHabit: { description: 'mostSuccessedHabit 데이터' },
  },
} satisfies Meta<typeof RecapPieChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Statistics_PieChart: Story = {
  args: {
    rateByHabitList: [
      {
        habit: '자기계발',
        rate: 28,
      },
      {
        habit: '운동',
        rate: 25,
      },
      {
        habit: '코딩',
        rate: 24,
      },
      {
        habit: '정신건강',
        rate: 13,
      },
      {
        habit: '기타',
        rate: 10,
      },
    ],
    mostSuccessedHabit: '자기계발',
  },
};
