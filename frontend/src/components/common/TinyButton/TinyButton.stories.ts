import type { Meta, StoryObj } from '@storybook/react';
import { TinyButton } from './TinyButton';

const meta = {
  title: 'Common/TinyButton',
  component: TinyButton,
  tags: ['autodocs'],
  argTypes: {
    mode: { description: '버튼 모드' },
    label: { description: '버튼에 들어갈 텍스트' },
  },
} satisfies Meta<typeof TinyButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Modify: Story = {
  args: {
    mode: 'MODIFY',
    label: '수정',
  },
};

export const Save: Story = {
  args: {
    mode: 'SAVE',
    label: '저장',
  },
};

export const Recommend: Story = {
  args: {
    mode: 'RECOMMEND',
    label: '다시 추천',
  },
};

export const HabitModify: Story = {
  args: {
    mode: 'MODIFY',
    label: '해빗 편집',
  },
};

export const HabitSave: Story = {
  args: {
    mode: 'SAVE',
    label: '편집 완료',
  },
};
