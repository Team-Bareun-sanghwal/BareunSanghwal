import type { Meta, StoryObj } from '@storybook/react';
import { HabitButton } from './HabitButton';

const meta = {
  title: 'Habit/HabitButton',
  component: HabitButton,
  tags: ['autodocs'],
  argTypes: {
    isDone: { description: '해당 해빗 기록 진행 여부' },
    label: { description: '해빗 이름' },
    iconSrc: { description: '해빗 아이콘' },
  },
} satisfies Meta<typeof HabitButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const AlreadyWrite: Story = {
  args: {
    isDone: true,
    label: '물 2L 마시기',
    iconSrc: '/images/icon-clock.png',
  },
};

export const NoWrite: Story = {
  args: {
    isDone: false,
    label: '물 2L 마시기',
    iconSrc: '/images/icon-clock.png',
  },
};
