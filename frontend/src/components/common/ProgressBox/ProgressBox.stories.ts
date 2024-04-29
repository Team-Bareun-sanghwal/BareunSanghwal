import type { Meta, StoryObj } from '@storybook/react';
import { ProgressBox } from './ProgressBox';

const meta = {
  title: 'Common/ProgressBox',
  component: ProgressBox,
  tags: ['autodocs'],
  argTypes: {
    stages: { description: '각 단계의 이름을 담은 배열' },
    beforeStageIndex: {
      description: '앞 단계의 인덱스(zero-based)',
    },
  },
} satisfies Meta<typeof ProgressBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const FirstStage: Story = {
  args: {
    stages: ['해빗 추천', '별명 설정', '요일 설정', '등록 완료'],
    beforeStageIndex: -1,
  },
};

export const SecondStage: Story = {
  args: {
    stages: ['해빗 추천', '별명 설정', '요일 설정', '등록 완료'],
    beforeStageIndex: 0,
  },
};

export const ThirdStage: Story = {
  args: {
    stages: ['해빗 추천', '별명 설정', '요일 설정', '등록 완료'],
    beforeStageIndex: 1,
  },
};

export const FourthStage: Story = {
  args: {
    stages: ['해빗 추천', '별명 설정', '요일 설정', '등록 완료'],
    beforeStageIndex: 2,
  },
};

export const FifthStage: Story = {
  args: {
    stages: ['해빗 추천', '별명 설정', '요일 설정', '등록 완료'],
    beforeStageIndex: 3,
  },
};
