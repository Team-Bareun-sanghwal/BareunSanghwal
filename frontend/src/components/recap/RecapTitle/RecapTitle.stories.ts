import type { Meta, StoryObj } from '@storybook/react';
import { RecapTitle } from './RecapTitle';

const meta = {
  title: 'Recap/RecapTitle',
  component: RecapTitle,
  parameters: {
    layout: 'centered',

    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    title: { description: '리캡 타이틀 문장' },
  },
} satisfies Meta<typeof RecapTitle>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Title: Story = {
  args: {
    title: '이번 달에 실천한 해빗이에요',
  },
};
