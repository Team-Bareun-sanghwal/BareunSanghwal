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
  },
};
export const RedTree: Story = {
  args: {
    color: '#303030',
  },
};

export const GrayTree: Story = {
  args: {
    color: 'Gray',
  },
};

export const BlueTree: Story = {
  args: {
    color: 'Blue',
  },
};
