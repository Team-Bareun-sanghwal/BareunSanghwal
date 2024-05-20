import type { Meta, StoryObj } from '@storybook/react';
import BorderlessButton from './BorderlessButton';

const meta = {
  title: 'MyPage/BorderlessButton',
  component: BorderlessButton,
  parameters: {
    layout: 'centered',
    nextRouter: {
      pathname: '/profile/[id]',
      asPath: '/profile/lifeiscontent',
      query: {
        id: 'lifeiscontent',
      },
    },
    nextjs: {
      appDirectory: true,
    },
  },
  tags: ['autodocs'],
  argTypes: {
    type: { description: '회원 탈퇴 / 로그아웃 여부' },
  },
} satisfies Meta<typeof BorderlessButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    type: 'leave',
  },
  parameters: {
    nextjs: {
      router: {
        pathname: 'https://www.kakaocorp.com/page/',
      },
    },
  },
};

export const Google: Story = {
  args: {
    type: 'logout',
  },
  parameters: {
    nextjs: {
      router: {
        pathname: 'https://www.google.co.kr/?hl=ko',
      },
    },
  },
};
