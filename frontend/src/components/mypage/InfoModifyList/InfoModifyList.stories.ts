import type { Meta, StoryObj } from '@storybook/react';
import InfoModifyList from './InfoModifyList';

const meta = {
  title: 'MyPage/InfoModifyList',
  component: InfoModifyList,
  parameters: {
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    userData: { description: '회원 데이터' },
  },
} satisfies Meta<typeof InfoModifyList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    userData: {
      nickname: '잉잉',
      birthDate: '1998-03-27',
      gender: 'F',
      job: 'STUDENT',
    },
  },
};
