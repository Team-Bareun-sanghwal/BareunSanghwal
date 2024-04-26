import type { Meta, StoryObj } from '@storybook/react';
import { Picker } from './Picker';

const meta = {
  title: 'common/Picker/Picker',
  component: Picker,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    color: { description: '얻은 스트릭 색상' },
  },
} satisfies Meta<typeof Picker>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Red: Story = {
  args: {
    color: 'red',
  },
};
export const React: Story = {
  args: {
    color: 'react',
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
