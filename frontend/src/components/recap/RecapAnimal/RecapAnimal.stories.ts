import type { Meta, StoryObj } from '@storybook/react';
import { RecapAnimal } from './RecapAnimal';

const meta = {
  title: 'Recap/RecapAnimal',
  component: RecapAnimal,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    mostSubmitTime: { description: '최다 리캡 달성 시간대' },
  },
} satisfies Meta<typeof RecapAnimal>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Dawn: Story = {
  args: {
    mostSubmitTime: 'DAWN',
  },
};

export const Morning: Story = {
  args: {
    mostSubmitTime: 'MORNING',
  },
};

export const Evening: Story = {
  args: {
    mostSubmitTime: 'EVENING',
  },
};

export const Night: Story = {
  args: {
    mostSubmitTime: 'NIGHT',
  },
};
