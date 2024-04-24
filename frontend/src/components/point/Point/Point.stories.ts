import type { Meta, StoryObj } from '@storybook/react';
import Point from './Point';

const meta = {
  title: 'point/Point',
  component: Point,
  // parameters: {
  //   layout: 'centered',
  // },
  tags: ['autodocs'],
  argTypes: {
    point: { description: '포인트' },
  },
} satisfies Meta<typeof Point>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Story1: Story = {
  args: {
    point: 0,
  },
};

export const Story2: Story = {
  args: {
    point: 24,
  },
};

export const Story3: Story = {
  args: {
    point: 441,
  },
};
export const Story4: Story = {
  args: {
    point: 1920,
  },
};

export const Story5: Story = {
  args: {
    point: 38018,
  },
};

export const Story6: Story = {
  args: {
    point: 99999,
  },
};
