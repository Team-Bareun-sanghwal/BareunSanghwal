import type { Meta, StoryObj } from '@storybook/react';
import Point from './Point';

const meta = {
  title: 'point/Point',
  component: Point,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    point: { description: '포인트' },
  },
} satisfies Meta<typeof Point>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Zero: Story = {
  args: {
    point: 0,
  },
};

export const Digits2: Story = {
  args: {
    point: 24,
  },
};

export const Digits3: Story = {
  args: {
    point: 441,
  },
};
export const Digits4: Story = {
  args: {
    point: 1920,
  },
};

export const Digits5: Story = {
  args: {
    point: 38018,
  },
};

export const Digits6: Story = {
  args: {
    point: 99999,
  },
};
export const FREE: Story = {
  args: {
    point: -1,
  },
};
