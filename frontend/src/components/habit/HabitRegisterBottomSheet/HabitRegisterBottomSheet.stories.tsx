import type { Meta, StoryObj } from '@storybook/react';
import { HabitRegisterBottomSheet } from './HabitRegisterBottomSheet';
import { HabitListBox } from '../HabitListBox/HabitListBox';

const meta = {
  title: 'Habit/HabitRegisterBottomSheet',
  component: HabitRegisterBottomSheet,
  tags: ['autodocs'],
  argTypes: {
    element: {
      description: '들어갈 ReactNode로, HabitListBox의 REGISTER 모드 적용',
    },
    open: { description: 'BottomSheet의 초기 상태(boolean)' },
    onClose: { description: 'BottomSheet의 취소 버튼을 제어' },
    onConfirm: { description: 'BottomSheet의 확인 버튼을 제어' },
  },
} satisfies Meta<typeof HabitRegisterBottomSheet>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    element: (
      <HabitListBox
        alias="물 2L 마시기마시기마시"
        completedAt={new Date('2024-04-23T00:00:00.000Z')}
        createdAt={new Date('2024-03-22T00:00:00.000Z')}
        iconSrc="/images/icon-clock.png"
        dayList={['월', '화', '수', '목', '금', '토', '일']}
        mode="REGISTER"
        name="건강하기"
      />
    ),
    open: true,
    onClose: () => console.log('취소 버튼 클릭'),
    onConfirm: () => console.log('확인 버튼 클릭'),
  },
};
