import type { Meta, StoryObj } from '@storybook/react';
import { ProgressBlock } from './ProgressBlock';

const meta = {
  title: 'Recap/ProgressBlock',
  component: ProgressBlock,
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
    nowIdx: { description: '해당 블록의 인덱스' },
    pageIdx: { description: '현재 리캡의 인덱스' },
    increasePageIdx: { description: '현재 리캡 state 변경 함수' },
  },
} satisfies Meta<typeof ProgressBlock>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Small: Story = {
  args: { nowIdx: 2, pageIdx: 4, increasePageIdx: () => {} },
};

export const Same: Story = {
  args: { nowIdx: 4, pageIdx: 4, increasePageIdx: () => {} },
};

export const Big: Story = {
  args: { nowIdx: 6, pageIdx: 4, increasePageIdx: () => {} },
};
