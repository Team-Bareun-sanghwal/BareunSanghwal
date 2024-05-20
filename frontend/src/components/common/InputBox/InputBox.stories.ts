import type { Meta, StoryObj } from '@storybook/react';
import { InputBox } from './InputBox';

const meta = {
  title: 'Common/InputBox',
  component: InputBox,
  tags: ['autodocs'],
  parameters: {
    backgrounds: {
      default: 'Light',
    },
  },
  argTypes: {
    mode: { description: '두 가지의 다른 input 활용 방안' },
  },
} satisfies Meta<typeof InputBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Nickname: Story = {
  args: {
    isLabel: true,
    mode: 'NICKNAME',
    defaultValue: '닉네임',
    setDefaultValue: () => {},
  },
};

export const HabitNickname: Story = {
  args: {
    isLabel: true,
    mode: 'HABITNICKNAME',
    defaultValue: '나를 위한 시간 20분 갖기',
    setDefaultValue: () => {},
  },
};

export const NoLabel: Story = {
  args: {
    isLabel: false,
    mode: 'NICKNAME',
    defaultValue: '닉네임',
    setDefaultValue: () => {},
  },
};
