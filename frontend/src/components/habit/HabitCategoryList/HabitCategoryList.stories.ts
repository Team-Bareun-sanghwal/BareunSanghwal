import type { Meta, StoryObj } from '@storybook/react';
import { HabitCategoryList } from './HabitCategoryList';

const meta = {
  title: 'Habit/HabitCategoryList',
  component: HabitCategoryList,
  tags: ['autodocs'],
  argTypes: {
    label: { description: '라벨 텍스트' },
    habitListData: {
      description: 'name과 habitId 속성으로 이루어진 데이터 객체들의 배열',
    },
  },
} satisfies Meta<typeof HabitCategoryList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Popular: Story = {
  args: {
    mode: 'POPULAR',
    label: '사람들이 가장 많이 진행하고 있는 해빗 10개',
    habitListData: [
      {
        name: '근력 쌓기',
        habitId: 1,
      },
      {
        name: '운동하기',
        habitId: 2,
      },
      {
        name: '수면 시간 지키기',
        habitId: 3,
      },
      {
        name: '청결 유지하기',
        habitId: 4,
      },
      {
        name: '물 마시기',
        habitId: 5,
      },
      {
        name: '잘 씻기',
        habitId: 6,
      },
      {
        name: '일찍 기상하기',
        habitId: 7,
      },
      {
        name: '축구하기',
        habitId: 8,
      },
      {
        name: '영단어 외우기',
        habitId: 9,
      },
      {
        name: '스트레칭',
        habitId: 10,
      },
    ],
  },
};

export const Similar: Story = {
  args: {
    mode: 'SIMILAR',
    label: '나와 비슷한 사람들이 진행하고 있는 해빗 10개',
    habitListData: [
      {
        name: '근력 쌓기',
        habitId: 1,
      },
      {
        name: '운동하기',
        habitId: 2,
      },
      {
        name: '수면 시간 지키기',
        habitId: 3,
      },
      {
        name: '청결 유지하기',
        habitId: 4,
      },
      {
        name: '물 마시기',
        habitId: 5,
      },
      {
        name: '잘 씻기',
        habitId: 6,
      },
      {
        name: '일찍 기상하기',
        habitId: 7,
      },
      {
        name: '축구하기',
        habitId: 8,
      },
      {
        name: '영단어 외우기',
        habitId: 9,
      },
      {
        name: '스트레칭',
        habitId: 10,
      },
    ],
  },
};
