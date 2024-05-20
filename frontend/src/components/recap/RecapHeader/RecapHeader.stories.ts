import type { Meta, StoryObj } from '@storybook/react';
import { RecapHeader } from './RecapHeader';

const meta = {
  title: 'Recap/RecapHeader',
  component: RecapHeader,
  parameters: {
    backgrounds: {
      default: 'dark',
    },
    nextRouter: {
      pathname: '/recap', // 리캡 리스트 경로
    },
    nextjs: {
      appDirectory: true,
    },
  },
  tags: ['autodocs'],
  argTypes: {
    memberName: { description: '사용자 닉네임' },
    year: { description: '리캡 년도' },
    month: { description: '리캡 월' },
  },
} satisfies Meta<typeof RecapHeader>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Title: Story = {
  args: { memberName: '감자도리', year: 2024, month: 1 },
  parameters: {
    nextjs: {
      router: {
        pathname: 'recap', // 리캡 리스트 경로
      },
    },
  },
};
