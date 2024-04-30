import type { Meta, StoryObj } from '@storybook/react';
import Item from './Item';

const meta = {
  title: 'point/Item',
  component: Item,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    name: { description: '상품명' },
    introduction: { description: '상품 소개' },
    description: { description: '상품 설명' },
    price: { description: '가격' },
    iconPath: { description: '아이콘 경로' },
  },
} satisfies Meta<typeof Item>;

export default meta;
type Story = StoryObj<typeof meta>;

export const StreakRecovoery: Story = {
  args: {
    name: '스트릭 리커버리',
    introduction: '최근 한 달 중 하나의 스트릭을 복구할 수 있어요',
    description: ' 주의! 리캡에는 포함되지 않아요',
    price: 400,
    iconPath: '/images/icon-item-recovery.png',
  },
};
export const Streak: Story = {
  args: {
    name: '알쏭달쏭 스트릭',
    introduction: '스트릭 색상을 바꿀 수 있어요',
    description:
      '아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요',
    price: 20,
    iconPath: '/images/icon-item-streak-color.png',
  },
};
export const Tree: Story = {
  args: {
    name: '알쏭달쏭 나무',
    introduction: '사용하면 나무의 색상을 12가지 색상 중 하나로 바꿔줘요',
    description:
      '아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요.',
    price: 20,
    iconPath: '/images/icon-item-tree.png',
  },
};
