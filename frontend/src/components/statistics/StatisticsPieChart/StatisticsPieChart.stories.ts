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
        habit: '위스키 마시기',
        value: 27,
      },
      {
        habit: '와인 마시기',
        value: 21,
      },
      {
        habit: '악기 연주',
        value: 18,
      },
      {
        habit: '요리하기',
        value: 15,
      },
      {
        habit: '축구',
        value: 11,
      },
      {
        habit: '기타',
        value: 8,
      },
    ],
  },
};
