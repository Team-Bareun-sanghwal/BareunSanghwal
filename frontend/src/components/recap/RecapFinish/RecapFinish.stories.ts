import type { Meta, StoryObj } from '@storybook/react';
import { RecapFinish } from './RecapFinish';

const meta = {
  title: 'Recap/RecapFinish',
  component: RecapFinish,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    month: { description: '리캡 월' },
  },
} satisfies Meta<typeof RecapFinish>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    month: 1,
  },
};
