import type { Meta, StoryObj } from '@storybook/react';
import Pallete from './Pallete';
const meta = {
  title: 'point/Pallete',
  component: Pallete,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    color: { description: '스트릭 구매시 획득한 색상' },
  },
} satisfies Meta<typeof Pallete>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Red: Story = {
  args: {
    color: 'red',
  },
};
export const Minchodan: Story = {
  args: {
    color: 'minchodan',
  },
};

export const Spring: Story = {
  args: {
    color: 'spring',
  },
};

export const Dippindots: Story = {
  args: {
    color: 'dippindots',
  },
};
