import type { Meta, StoryObj } from '@storybook/react';
import { RecapKeyword } from './RecapKeyword';

const meta = {
  title: 'Recap/RecapKeyword',
  component: RecapKeyword,
  parameters: {
    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    keyword: { description: '리캡 키워드' },
  },
} satisfies Meta<typeof RecapKeyword>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    keyword: '운동',
  },
};
