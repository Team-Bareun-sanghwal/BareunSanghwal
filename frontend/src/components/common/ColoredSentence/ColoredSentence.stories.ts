import type { Meta, StoryObj } from '@storybook/react';
import { ColoredSentence } from './ColoredSentence';

const meta = {
  title: 'Common/ColoredSentence',
  component: ColoredSentence,
  parameters: {
    layout: 'centered',

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
} satisfies Meta<typeof ColoredSentence>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    textFront: '총',
    textMiddle: ' 149',
    textBack: '일 동안 스트릭을 달성하고',
  },
};
