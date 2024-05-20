import type { Meta, StoryObj } from '@storybook/react';
import { Achievement } from './Achievement';

const meta = {
  title: 'calender/Achievement',
  component: Achievement,
  parameters: {
    layout: 'padded',
  },
  tags: ['autodocs'],
  argTypes: {
    proportion: { description: '진행도(0~100, %생략)' },
    themeColor: { description: '테마 색상' },
  },
} satisfies Meta<typeof Achievement>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Rose38: Story = {
  args: {
    proportion: 38,
    themeColor: 'rose',
  },
};
export const Minchodan92: Story = {
  args: {
    proportion: 92,
    themeColor: 'minchodan',
  },
};
export const Red56: Story = {
  args: {
    proportion: 56,
    themeColor: 'red',
  },
};
export const Blue21: Story = {
  args: {
    proportion: 21,
    themeColor: 'blue',
  },
};
