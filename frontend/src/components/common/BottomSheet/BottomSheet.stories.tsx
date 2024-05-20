import type { Meta, StoryObj } from '@storybook/react';
import { BottomSheet } from './BottomSheet';

const meta = {
  title: 'Common/BottomSheet',
  component: BottomSheet,
  tags: ['autodocs'],
  argTypes: {
    title: { description: 'BottomSheet의 제목' },
    description: { description: 'BottomSheet의 제목' },
    mode: { description: 'BottomSheet에 들어갈 배경 이미지' },
    open: { description: 'BottomSheet의 초기 상태(boolean)' },
    onClose: { description: 'BottomSheet의 취소 버튼을 제어' },
    onConfirm: { description: 'BottomSheet의 확인 버튼을 제어' },
  },
} satisfies Meta<typeof BottomSheet>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Positive: Story = {
  args: {
    title: '프로틴 주스 마시기 해빗을 삭제하시겠어요?',
    description:
      '해빗을 삭제하면 더 이상 기록할 수 없어요. 그래도 삭제하시겠어요? 해빗을 삭제하면 더 이상 기록할 수 없어요. 그래도 삭제하시겠어요? ',
    mode: 'POSITIVE',
    open: true,
    onClose: () => console.log('취소 버튼 클릭'),
    onConfirm: () => console.log('확인 버튼 클릭'),
  },
};

export const Negative: Story = {
  args: {
    title: '프로틴 주스 마시기 해빗을 삭제하시겠어요?',
    description:
      '해빗을 삭제하면 더 이상 기록할 수 없어요. 그래도 삭제하시겠어요?',
    mode: 'NEGATIVE',
    open: true,
    onClose: () => console.log('취소 버튼 클릭'),
    onConfirm: () => console.log('확인 버튼 클릭'),
  },
};

export const Recovery: Story = {
  args: {
    title: '프로틴 주스 마시기 해빗을 삭제하시겠어요?',
    description:
      '해빗을 삭제하면 더 이상 기록할 수 없어요. 그래도 삭제하시겠어요?',
    mode: 'RECOVERY',
    open: true,
    onClose: () => console.log('취소 버튼 클릭'),
    onConfirm: () => console.log('확인 버튼 클릭'),
  },
};

export const None: Story = {
  args: {
    title: '프로틴 주스 마시기 해빗을 삭제하시겠어요?',
    description:
      '해빗을 삭제하면 더 이상 기록할 수 없어요. 그래도 삭제하시겠어요?',
    mode: 'NONE',
    open: true,
    onClose: () => console.log('취소 버튼 클릭'),
    onConfirm: () => console.log('확인 버튼 클릭'),
  },
};
