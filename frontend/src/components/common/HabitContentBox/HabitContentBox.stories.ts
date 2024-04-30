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
        habitList: [
          {
            content: '운동!!!!!1',
            day: 1,
            createdAt: new Date('2024-03-26'),
            habitTrackerId: 1,
            image: 'basic',
            succeededTime: new Date('2024-03-26'),
          },
          {
            content: '운동!!!!!2',
            day: 2,
            createdAt: new Date('2024-03-25'),
            habitTrackerId: 2,
            image: 'basic',
            succeededTime: new Date('2024-03-25'),
          },
          {
            content: '운동!!!!!3',
            day: 3,
            createdAt: new Date('2024-03-24'),
            habitTrackerId: 3,
            image: 'basic',
            succeededTime: new Date('2024-03-24'),
          },
          {
            content: '운동!!!!!4',
            day: 4,
            createdAt: new Date('2024-03-23'),
            habitTrackerId: 4,
            image: 'basic',
            succeededTime: new Date('2024-03-23'),
          },
        ],
      },
      {
        year: 2023,
        habitList: [
          {
            content: '운동!!!!!5',
            day: 5,
            createdAt: new Date('2023-12-22'),
            habitTrackerId: 5,
            image: 'basic',
            succeededTime: new Date('2023-12-26'),
          },
          {
            content: '운동!!!!!6',
            day: 6,
            createdAt: new Date('2023-11-22'),
            habitTrackerId: 6,
            image: 'basic',
            succeededTime: new Date('2023-11-26'),
          },
        ],
      },
    ],
  },
};
