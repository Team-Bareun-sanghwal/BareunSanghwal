import type { Meta, StoryObj } from '@storybook/react';
import { AlertBox } from './AlertBox';

const meta = {
  title: 'Common/AlertBox',
  component: AlertBox,
  tags: ['autodocs'],
  argTypes: {
    mode: { description: 'AlertBox 종류(SUCCESS/WARNING/ERROR)' },
    label: { description: 'AlertBox에 들어갈 텍스트' },
  },
} satisfies Meta<typeof AlertBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Success: Story = {
  args: {
    mode: 'SUCCESS',
    label:
      '해빗 이름은 15자를 넘을 수 없어요. 해빗 이름은 15자가 될 수도 있어요.',
    open: true,
  },
};

export const Warning: Story = {
  args: {
    mode: 'WARNING',
    label: '해빗 이름은 15자를 넘을 수 없어요.',
    open: true,
  },
};

export const Error: Story = {
  args: {
    mode: 'ERROR',
    label: '해빗 이름은 15자를 넘을 수 없어요.',
    open: true,
  },
};
