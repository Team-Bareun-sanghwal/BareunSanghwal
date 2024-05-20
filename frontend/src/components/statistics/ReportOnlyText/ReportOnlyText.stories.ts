import type { Meta, StoryObj } from '@storybook/react';
import { ReportOnlyText } from './ReportOnlyText';

const meta = {
  title: 'Statistics/ReportOnlyText',
  component: ReportOnlyText,
  parameters: {
    layout: 'centered',

    backgrounds: {
      default: 'light',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    whole_days: { description: '서비스 사용 기간' },
    streak_days: { description: '스트릭을 달성한 날' },
    star_days: { description: '별을 달성한 날' },
    longest_streak: { description: '최장 스트릭' },
  },
} satisfies Meta<typeof ReportOnlyText>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Kakao: Story = {
  args: {
    whole_days: 173,
    streak_days: 149,
    star_days: 52,
    longest_streak: 103,
  },
};
