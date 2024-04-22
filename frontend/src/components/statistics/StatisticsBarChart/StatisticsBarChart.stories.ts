import type { Meta, StoryObj } from '@storybook/react';
import { StatisticsBarChart } from './StatisticsBarChart';

const meta = {
  title: 'Statistics/StatisticsBarChart',
  component: StatisticsBarChart,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    data: { description: 'achievement_per_day_week 데이터' },
  },
} satisfies Meta<typeof StatisticsBarChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Statistics_PieChart: Story = {
  args: {
    data: [
      {
        day: '일',
        value: 45,
        colorIdx: 1,
      },
      {
        day: '월',
        value: 67,
        colorIdx: 2,
      },
      {
        day: '화',
        value: 56,
        colorIdx: 1,
      },
      {
        day: '수',
        value: 53,
        colorIdx: 1,
      },
      {
        day: '목',
        value: 32,
        colorIdx: 1,
      },
      {
        day: '금',
        value: 11,
        colorIdx: 0,
      },
      {
        day: '토',
        value: 12,
        colorIdx: 1,
      },
    ],
  },
};
