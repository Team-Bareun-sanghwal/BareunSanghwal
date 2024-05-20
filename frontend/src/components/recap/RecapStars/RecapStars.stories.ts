import type { Meta, StoryObj } from '@storybook/react';
import { RecapStars } from './RecapStars';

const meta = {
  title: 'Recap/RecapStars',
  component: RecapStars,
  parameters: {
    layout: 'centered',

    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    collectedStar: { description: '별 개수' },
  },
} satisfies Meta<typeof RecapStars>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Title: Story = {
  args: {
    collectedStar: 13,
  },
};
