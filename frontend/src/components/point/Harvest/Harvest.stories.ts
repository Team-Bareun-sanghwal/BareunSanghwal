import type { Meta, StoryObj } from '@storybook/react';
import { Harvest } from './Harvest';

const meta = {
  title: 'Point/Harvest',
  component: Harvest,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    isHarvested: { description: '오늘의 포인트를 수확 했는지 여부' },
  },
} satisfies Meta<typeof Harvest>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Yet: Story = {
  args: {
    isHarvested: true,
  },
};
export const Harvested: Story = {
  args: {
    isHarvested: false,
  },
};
