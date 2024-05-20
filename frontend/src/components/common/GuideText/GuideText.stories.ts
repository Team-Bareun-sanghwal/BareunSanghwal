import type { Meta, StoryObj } from '@storybook/react';
import { GuideText } from './GuideText';

const meta = {
  title: 'Common/GuideText',
  component: GuideText,
  tags: ['autodocs'],
  argTypes: {
    text: {
      description: '안내 텍스트',
    },
  },
} satisfies Meta<typeof GuideText>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Activated: Story = {
  args: {
    text: '작성한 내용은 목록에서 확인할 수 있어요.',
  },
};
