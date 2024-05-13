import type { Meta, StoryObj } from '@storybook/react';
import Tree from './Tree';
const meta = {
  title: 'tree/Tree',
  component: Tree,

  tags: ['autodocs'],
  argTypes: {
    color: { description: '나무 색상' },
  },
} satisfies Meta<typeof Tree>;

export default meta;
type Story = StoryObj<typeof meta>;

export const GreenTree: Story = {
  args: {
    color: 'green',
    time: 'morning',
    level: 1,
  },
};
export const RedTree: Story = {
  args: {
    color: '#303030',
    time: 'night',
    level: 2,
  },
};

export const GrayTree: Story = {
  args: {
    color: 'Gray',
    time: 'midnight',
    level: 3,
  },
};

export const BlueTree: Story = {
  args: {
    color: 'Blue',
    time: 'lunch',
    level: 4,
  },
};
