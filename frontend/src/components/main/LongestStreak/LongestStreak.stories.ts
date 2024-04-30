import type { Meta, StoryObj } from '@storybook/react';
import { LongestStreak } from './LongestStreak';
const meta = {
  title: 'main/LongestStreak',
  component: LongestStreak,
  tags: ['autodocs'],
  argTypes: {
    longestStreakCount: { description: '최장 스트릭 수' },
  },
} satisfies Meta<typeof LongestStreak>;

export default meta;
type Story = StoryObj<typeof meta>;

export const NoStreak: Story = {
  args: {
    longestStreakCount: 0,
  },
};
export const Activated: Story = {
  args: {
    longestStreakCount: 24,
  },
};
