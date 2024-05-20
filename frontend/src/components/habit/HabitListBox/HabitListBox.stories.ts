import type { Meta, StoryObj } from '@storybook/react';
import { HabitListBox } from './HabitListBox';

const meta = {
  title: 'Habit/HabitListBox',
  component: HabitListBox,
  tags: ['autodocs'],
  argTypes: {
    mode: {
      description:
        'GOING(현재 진행 중인 해빗)과 UPDATE(편집 상태의 해빗), COMPLETED(완료한 해빗)',
    },
    name: { description: '해빗 카테고리' },
    alias: { description: '해빗 별칭' },
    iconSrc: { description: '해빗 아이콘' },
    createdAt: { description: '해빗을 등록한 날짜' },
    completedAt: {
      description: '해빗을 완료한 날짜로 mode가 COMPLETED일 때만 입력',
    },
  },
} satisfies Meta<typeof HabitListBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Going: Story = {
  args: {
    mode: 'GOING',
    name: '건강하기',
    alias: '물 2L 마시기',
    iconSrc: '/images/icon-clock.png',
    createdAt: new Date('2024-03-22'),
  },
};

export const Update: Story = {
  args: {
    mode: 'UPDATE',
    name: '건강하기',
    alias: '물 2L 마시기',
    iconSrc: '/images/icon-clock.png',
    createdAt: new Date('2024-03-22'),
  },
};

export const Completed: Story = {
  args: {
    mode: 'COMPLETED',
    name: '건강하기',
    alias: '물 2L 마시기',
    iconSrc: '/images/icon-clock.png',
    createdAt: new Date('2024-03-22'),
    completedAt: new Date('2024-04-23'),
  },
};
