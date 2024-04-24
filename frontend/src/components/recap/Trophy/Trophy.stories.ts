import type { Meta, StoryObj } from '@storybook/react';
import { Trophy } from './Trophy';

const meta = {
  title: 'Recap/Trophy',
  component: Trophy,
  parameters: {
    layout: 'centered',

    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    mainHabit: { description: '이번 달 대표 해빗' },
  },
} satisfies Meta<typeof Trophy>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Title: Story = {
  args: {
    mainHabit: '점심 샐러드 먹기',
  },
};
