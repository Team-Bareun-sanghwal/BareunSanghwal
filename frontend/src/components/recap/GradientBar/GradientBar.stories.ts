import type { Meta, StoryObj } from '@storybook/react';
import { GradientBar } from './GradientBar';

const meta = {
  title: 'Recap/GradientBar',
  component: GradientBar,
  parameters: {
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    textFront: { description: '강조 텍스트 앞 문장' },
    textMiddle: { description: '강조 텍스트' },
    textBack: { description: '강조 텍스트 뒷 문장' },
  },
} satisfies Meta<typeof GradientBar>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    textFront: '대단한데요! 평균적으로 ',
    textMiddle: '61.7%',
    textBack: '를 달성했어요!',
  },
};
