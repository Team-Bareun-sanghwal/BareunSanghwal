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
} satisfies Meta<typeof StatisticsPieChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Statistics_PieChart: Story = {
  parameters: {},
};
