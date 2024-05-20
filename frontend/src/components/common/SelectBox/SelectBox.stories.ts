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
  parameters: {
    backgrounds: {
      default: 'Light',
    },
  },
} satisfies Meta<typeof SelectBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Gender: Story = {
  args: {
    label: '성별',
    options: [
      { key: 'M', value: '남자' },
      { key: 'F', value: '여자' },
      { key: 'N', value: '말하고 싶지 않아요' },
    ],
    defaultValue: 'F',
    setDefaultValue: () => {},
  },
};

export const Job: Story = {
  args: {
    label: '직업',
    options: [
      { key: 'STUDENT', value: '학생' },
      { key: 'EMPLOYEE', value: '회사원' },
      { key: 'HOUSEWIFE', value: '주부' },
      { key: 'JOB_SEEKER', value: '취준생' },
      { key: 'SELF_EMPLOYED', value: '자영업' },
    ],
    defaultValue: 'JOB_SEEKER',
    setDefaultValue: () => {},
  },
};
