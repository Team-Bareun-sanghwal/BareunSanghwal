import type { Meta, StoryObj } from '@storybook/react';
import { RecapBarChart } from './RecapBarChart';

const meta = {
  title: 'Recap/RecapBarChart',
  component: RecapBarChart,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    rateByMemberHabitList: { description: 'rateByMemberHabitList 데이터' },
    averageRateByMemberHabit: {
      description: 'averageRateByMemberHabit 데이터',
    },
  },
} satisfies Meta<typeof RecapBarChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Statistics_PieChart: Story = {
  args: {
    rateByMemberHabitList: [
      {
        name: '웨이트 1시간',
        missCount: 5,
        actionCount: 37,
        ratio: 89,
      },
      {
        name: '강의장 계단으로 가기',
        missCount: 3,
        actionCount: 37,
        ratio: 93,
      },
      {
        name: '점심 샐러드 먹기',
        missCount: 8,
        actionCount: 27,
        ratio: 78,
      },
      {
        name: '영양제 먹기',
        missCount: 3,
        actionCount: 27,
        ratio: 90,
      },
      {
        name: '12시 취침',
        missCount: 2,
        actionCount: 15,
        ratio: 80,
      },
    ],
    averageRateByMemberHabit: 63.3,
  },
};
