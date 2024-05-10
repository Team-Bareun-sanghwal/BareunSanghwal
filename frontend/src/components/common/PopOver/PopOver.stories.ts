import type { Meta, StoryObj } from '@storybook/react';
import { PopOver } from './PopOver';

const meta = {
  title: 'Common/PopOver',
  component: PopOver,
  tags: ['autodocs'],
  argTypes: {
    title: { description: 'PopOver 타이들' },
    children: { description: 'PopOver 내용' },
  },
} satisfies Meta<typeof PopOver>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    title: '이 나무는 무엇인가요?',
    children: '나무는 트리입니다',
  },
};
