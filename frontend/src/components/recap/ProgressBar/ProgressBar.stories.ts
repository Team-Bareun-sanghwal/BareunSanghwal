import type { Meta, StoryObj } from '@storybook/react';
import { ProgressBar } from './ProgressBar';

const meta = {
  title: 'Recap/ProgressBar',
  component: ProgressBar,
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
    pageIdx: { description: '현재 리캡의 인덱스' },
    increasePageIdx: { description: '현재 리캡 state 변경 함수' },
  },
} satisfies Meta<typeof ProgressBar>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Same: Story = {
  args: { pageIdx: 4, increasePageIdx: () => {} },
};
