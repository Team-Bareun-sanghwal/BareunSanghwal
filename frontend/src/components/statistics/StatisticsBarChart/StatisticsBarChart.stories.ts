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
} satisfies Meta<typeof StatisticsBarChart>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Statistics_PieChart: Story = {
  parameters: {},
};
