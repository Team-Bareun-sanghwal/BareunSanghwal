import type { Meta, StoryObj } from '@storybook/react';
import { Button } from './Button';

const meta = {
  title: 'Common/Button',
  component: Button,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    isActivated: { description: '버튼 활성화 여부' },
    label: { description: '버튼에 들어갈 텍스트' },
  },
} satisfies Meta<typeof Button>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Activated: Story = {
  args: {
    isActivated: true,
    label: '확인',
  },
};

export const NonActivated: Story = {
  args: {
    isActivated: false,
    label: '취소',
  },
};
