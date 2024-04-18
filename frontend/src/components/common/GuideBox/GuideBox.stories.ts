import type { Meta, StoryObj } from '@storybook/react';
import { GuideBox } from './GuideBox';

const meta = {
  title: 'Common/GuideBox',
  component: GuideBox,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    guideText: { description: '안내 텍스트' },
  },
} satisfies Meta<typeof GuideBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    guideText:
      '지금은 총 2개의 해빗을 관리하고 있어요. 아래 목록에서 바로 기록하러 갈 수 있습니다. 편집도 가능해요!',
  },
};
