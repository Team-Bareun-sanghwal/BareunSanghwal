import type { Meta, StoryObj } from '@storybook/react';
import { Calender } from './Calender';
import { StreaksResponse } from '@/app/mock';

const meta = {
  title: 'Calender/Calender',
  component: Calender,
  tags: ['autodocs'],
  argTypes: {
    dayOfWeekFirst: { description: '해당 월 1일의 요일' },
    memberHabitList: { description: '해빗 리스트' },
    dayInfo: { description: '일 별 스트릭 정보' },
    themeColor: { description: '스트릭테마 색상' },
  },
  parameters: {},
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
  parameters: {
    viewports: { defaultViewport: 'mobile1' },
  },
};
