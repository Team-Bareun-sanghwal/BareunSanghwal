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
    birthDay: { description: '상위 컴포넌트의 생일 state' },
    setBirthDay: { description: '상위 컴포넌트의 생일 state 변경 함수' },
  },
} satisfies Meta<typeof ScrollDatePicker>;

export default meta;
type Story = StoryObj<typeof meta>;

export const NoBirthday: Story = {
  args: {
    birthDay: '',
    setBirthDay: () => {},
  },
};

export const Birthday: Story = {
  args: {
    birthDay: '1999-1-7',
    setBirthDay: () => {},
  },
};
