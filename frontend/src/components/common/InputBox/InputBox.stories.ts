import type { Meta, StoryObj } from '@storybook/react';
import { InputBox } from './InputBox';

const meta = {
  title: 'Common/InputBox',
  component: InputBox,
  tags: ['autodocs'],
  argTypes: {
    mode: { description: '두 가지의 다른 input 활용 방안' },
  },
} satisfies Meta<typeof InputBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Nickname: Story = {
  args: {
    mode: 'NICKNAME',
  },
};

export const HabitNickname: Story = {
  args: {
    mode: 'HABITNICKNAME',
  },
};
