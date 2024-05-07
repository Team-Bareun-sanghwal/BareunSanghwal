import type { Meta, StoryObj } from '@storybook/react';
import { ColoredRecapTitle } from './ColoredRecapTitle';

const meta = {
  title: 'Recap/ColoredRecapTitle',
  component: ColoredRecapTitle,
  parameters: {
    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    title: { description: '해빗 이름' },
    colorIdx: { description: '%로 계산된 인덱스' },
  },
} satisfies Meta<typeof ColoredRecapTitle>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Title: Story = {
  args: {
    title: '프로틴 주스 마시기',
    colorIdx: 3,
  },
};
