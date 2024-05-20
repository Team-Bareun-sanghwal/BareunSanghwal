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
        name: '자기계발',
        ratio: 28,
      },
      {
        name: '운동',
        ratio: 25,
      },
      {
        name: '코딩',
        ratio: 24,
      },
      {
        name: '정신건강',
        ratio: 13,
      },
      {
        name: '기타',
        ratio: 10,
      },
    ],
    mostSuccessedHabit: '자기계발',
  },
};
