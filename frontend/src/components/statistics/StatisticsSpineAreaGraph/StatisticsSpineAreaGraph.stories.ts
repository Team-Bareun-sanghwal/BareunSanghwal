import type { Meta, StoryObj } from '@storybook/react';
import { StatisticsSpineAreaGraph } from './StatisticsSpineAreaGraph';

const meta = {
  title: 'Statistics/StatisticsSpineAreaGraph',
  component: StatisticsSpineAreaGraph,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    data: { description: 'achievement_per_hour 데이터' },
  },
} satisfies Meta<typeof StatisticsSpineAreaGraph>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Statistics_PieChart: Story = {
  args: {
    data: [
      {
        time: 0,
        value: 34,
      },
      {
        time: 1,
        value: 8,
      },
      {
        time: 2,
        value: 2,
      },
      {
        time: 3,
        value: 0,
      },
      {
        time: 4,
        value: 0,
      },
      {
        time: 5,
        value: 3,
      },
      {
        time: 6,
        value: 14,
      },
      {
        time: 7,
        value: 37,
      },
      {
        time: 8,
        value: 26,
      },
      {
        time: 9,
        value: 17,
      },
      {
        time: 10,
        value: 15,
      },
      {
        time: 11,
        value: 28,
      },
      {
        time: 12,
        value: 73,
      },
      {
        time: 13,
        value: 83,
      },
      {
        time: 14,
        value: 54,
      },
      {
        time: 15,
        value: 43,
      },
      {
        time: 16,
        value: 36,
      },
      {
        time: 17,
        value: 38,
      },
      {
        time: 18,
        value: 43,
      },
      {
        time: 19,
        value: 96,
      },
      {
        time: 20,
        value: 71,
      },
      {
        time: 21,
        value: 73,
      },
      {
        time: 22,
        value: 35,
      },
      {
        time: 23,
        value: 22,
      },
    ],
  },
};
