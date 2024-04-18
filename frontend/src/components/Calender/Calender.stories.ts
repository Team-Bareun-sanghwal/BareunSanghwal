import type { Meta, StoryObj } from '@storybook/react';
import Calender from './Calender';
import { StreaksResponse } from './mockserver';
import { ST } from 'next/dist/shared/lib/utils';
const meta = {
  title: 'Calender',
  component: Calender,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    dayOfWeekFirst: { description: '해당 월 1일의 요일' },
    memberHabitList: { description: '해빗 리스트' },
    dayInfo: { description: '일 별 스트릭 정보' },
    themeColor: { description: '스트릭테마 색상' },
  },
} satisfies Meta<typeof Calender>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Activated: Story = {
  args: {
    dayOfWeekFirst: 0,
    memberHabitList: StreaksResponse.memberHabitList,
    dayInfo: StreaksResponse.dayInfo,
    themeColor: 'rose',
  },
};
