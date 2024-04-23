import type { Meta, StoryObj } from '@storybook/react';
import { ErrorPage } from './ErrorPage';

const meta = {
  title: 'Common/ErrorPage',
  component: ErrorPage,
  tags: ['autodocs'],
  argTypes: {
    errorTitle: { description: '오류 제목' },
    errorDescription: { description: '오류에 대한 설명' },
    buttonText: { description: '버튼 텍스트' },
  },
} satisfies Meta<typeof ErrorPage>;

export default meta;
type Story = StoryObj<typeof meta>;

export const NoContent: Story = {
  args: {
    errorTitle: '기록한 내역이 없어요.',
    errorDescription: '해빗 기록이 남도록 참여해보세요!',
    buttonText: '기록하러 가기',
  },
};

export const NotFound: Story = {
  args: {
    errorTitle: '페이지를 찾을 수 없어요.',
    errorDescription: '잘못된 접근입니다. 홈으로 돌아가주세요.',
    buttonText: '홈으로 가기',
  },
};

export const ServerError: Story = {
  args: {
    errorTitle: '예기치 않은 오류가 발생했어요.',
    errorDescription: '오류가 발생했어요. 다시 시도해주세요.',
    buttonText: '홈으로 가기',
  },
};
