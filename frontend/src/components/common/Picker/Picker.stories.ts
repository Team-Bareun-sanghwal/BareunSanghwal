import type { Meta, StoryObj } from '@storybook/react';
import { Picker } from './Picker';

const meta = {
  title: 'Common/Picker',
  component: Picker,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
} satisfies Meta<typeof Picker>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Red: Story = {
  args: {
    label: '이모지를 골라주세요',
  },
};
