import type { Meta, StoryObj } from '@storybook/react';
import { SelectBox } from './SelectBox';

const meta = {
  title: 'Common/SelectBox',
  component: SelectBox,
  tags: ['autodocs'],
  argTypes: {
    label: { description: '라벨 텍스트' },
    options: { description: '옵션 배열' },
  },
} satisfies Meta<typeof SelectBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Gender: Story = {
  args: {
    label: '성별',
    options: ['남자', '여자', '말하고 싶지 않아요'],
  },
};

export const Job: Story = {
  args: {
    label: '직업',
    options: ['학생', '회사원', '주부', '취준생', '자영업'],
  },
};

export const NoLabel: Story = {
  args: {
    options: ['학생', '회사원', '주부', '취준생', '자영업'],
  },
};
