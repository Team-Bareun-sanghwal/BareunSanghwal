import type { Meta, StoryObj } from '@storybook/react';
import { Streak } from './Streak';

const meta = {
  title: 'calender/Streak',
  component: Streak,
  parameters: {
    layout: 'padded',
  },
  tags: ['autodocs'],
  argTypes: {
    themeColor: { description: '스트릭테마 색상' },
    isUnique: {
      description:
        '희귀 색상인지 여부(dippindots, rose, rainbow, sunny_summer)',
    },
    achieveCount: { description: '해당 일 달성 해빗 갯수' },
    dayNumber: { description: '날짜(일)' },
    habitCnt: { description: '해당 일 총 해빗 갯수' },
    onClick: { description: '스트릭 리커버리 사용 이벤트' },
  },
} satisfies Meta<typeof Streak>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Story1: Story = {
  args: {
    achieveType: 'NOT_EXISTED',
    themeColor: 'rose',
    isUnique: true,
    achieveCount: 4,
    dayNumber: 14,
    habitCnt: 7,
    onClick: () => {
      console.log('heeey');
    },
  },
};

export const Story2: Story = {
  args: {
    achieveType: 'NOT_EXISTED',
    themeColor: 'black',
    isUnique: false,
    achieveCount: 2,
    dayNumber: 14,
    habitCnt: 4,
    onClick: () => {
      console.log('yooo');
    },
  },
};
