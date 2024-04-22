import type { Meta, StoryObj } from '@storybook/react';
import { StatisticsPieChart } from './StatisticsPieChart';

const meta = {
  title: 'Statistics/StatisticsPieChart',
  component: StatisticsPieChart,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    data: { description: 'achievement 데이터' },
  },
} satisfies Meta<typeof StatisticsPieChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Statistics_PieChart: Story = {
  args: {
    data: [
      {
        x: '위스키 마시기',
        y: 27,
      },
      {
        x: '와인 마시기',
        y: 21,
      },
      {
        x: '악기 연주',
        y: 18,
      },
      {
        x: '요리하기',
        y: 15,
      },
      {
        x: '축구',
        y: 11,
      },
      {
        x: '기타',
        y: 8,
      },
    ],
  },
};
