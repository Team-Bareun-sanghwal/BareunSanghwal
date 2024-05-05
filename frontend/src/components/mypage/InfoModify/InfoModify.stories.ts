import type { Meta, StoryObj } from '@storybook/react';
import { InfoModify } from './InfoModify';

const meta = {
  title: 'MyPage/InfoModify',
  component: InfoModify,
  parameters: {
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    title: { description: '수정할 정보' },
    prevData: { description: '기본 값' },
  },
} satisfies Meta<typeof InfoModify>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Nickname: Story = {
  args: {
    title: '닉네임',
    prevData: '으앙앙',
  },
};

export const Sex: Story = {
  args: {
    title: '성별',
    prevData: 'F',
  },
};

export const Job: Story = {
  args: {
    title: '직업',
    prevData: 'JOB_SEEKER',
  },
};

export const Birthday: Story = {
  args: {
    title: '생일',
    prevData: '1999-01-07',
  },
};