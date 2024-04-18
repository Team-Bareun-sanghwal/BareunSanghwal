import type { Meta, StoryObj } from '@storybook/react';
import { LoginButton } from './LoginButton';

const meta = {
  title: 'Loading/LoginButton',
  component: LoginButton,
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
    platform: { description: 'OAuth 플랫폼(카카오/구글)' },
  },
} satisfies Meta<typeof LoginButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    platform: 'kakao',
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
    platform: 'google',
  },
  parameters: {
    nextjs: {
      router: {
        pathname: 'https://www.google.co.kr/?hl=ko',
      },
    },
  },
};
