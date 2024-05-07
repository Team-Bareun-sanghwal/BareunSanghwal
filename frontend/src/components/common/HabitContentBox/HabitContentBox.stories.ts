import type { Meta, StoryObj } from '@storybook/react';
import { HabitContentBox } from './HabitContentBox';

const meta = {
  title: 'Common/HabitContentBox',
  component: HabitContentBox,
  tags: ['autodocs'],
  argTypes: {
    habitTotalData: {
      description: '모든 연도의 해빗(HABIT) 데이터',
    },
  },
} satisfies Meta<typeof HabitContentBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Habit: Story = {
  args: {
    habitTotalData: [
      {
        year: 2024,
        habitTrackerList: [
          {
            content: '운동!!!!!1',
            habitTrackerId: 1,
            image: 'basic',
            succeededTime: new Date('2024-03-26'),
            period: '3월 3일',
          },
          {
            content: '운동!!!!!2',

            habitTrackerId: 2,
            image: 'basic',
            succeededTime: new Date('2024-03-25'),
            period: '3월 3일',
          },
          {
            content: '운동!!!!!3',
            period: '3월 3일',
            habitTrackerId: 3,
            image: 'basic',
            succeededTime: new Date('2024-03-24'),
          },
          {
            content: '운동!!!!!4',
            period: '3월 3일',
            habitTrackerId: 4,
            image: 'basic',
            succeededTime: new Date('2024-03-23'),
          },
        ],
      },
      {
        year: 2023,
        habitTrackerList: [
          {
            content: '운동!!!!!5',
            period: '3월 3일',
            habitTrackerId: 5,
            image: 'basic',
            succeededTime: new Date('2023-12-26'),
          },
          {
            content: '운동!!!!!6',
            period: '3월 3일',
            habitTrackerId: 6,
            image: 'basic',
            succeededTime: new Date('2023-11-26'),
          },
        ],
      },
    ],
  },
};
