import type { Meta, StoryObj } from '@storybook/react';
import { ScrollDatePicker } from './ScrollDatePicker';

const meta = {
  title: 'Common/ScrollDatePicker',
  component: ScrollDatePicker,
  parameters: {
    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    birthDay: { description: '생일 데이터' },
    setBirthDay: { description: '생일 state 변경 함수' },
  },
} satisfies Meta<typeof ScrollDatePicker>;

export default meta;
type Story = StoryObj<typeof meta>;

export const HaveData: Story = {
  args: { birthDay: '1999-01-07', setBirthDay: () => {} },
};

export const NoData: Story = {
  args: { birthDay: '', setBirthDay: () => {} },
};
