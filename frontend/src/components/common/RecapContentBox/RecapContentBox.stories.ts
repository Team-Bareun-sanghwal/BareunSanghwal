import type { Meta, StoryObj } from '@storybook/react';
import { RecapContentBox } from './RecapContentBox';

const meta = {
  title: 'Common/RecapContentBox',
  component: RecapContentBox,
  tags: ['autodocs'],
  argTypes: {
    recapTotalData: {
      description: '모든 연도의 리캡(RECAP) 데이터',
    },
  },
} satisfies Meta<typeof RecapContentBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Recap: Story = {
  args: {
    recapTotalData: [
      {
        year: 2024,
        recapList: [
          {
            recapId: 16,
            image: 'basic',
            period: new Date('2024-03-26'),
          },
          {
            recapId: 18,
            image: 'basic',
            period: new Date('2024-02-26'),
          },
          {
            recapId: 19,
            image: 'basic',
            period: new Date('2024-01-26'),
          },
          {
            recapId: 19,
            image: 'basic',
            period: new Date('2024-01-26'),
          },
        ],
      },
      {
        year: 2023,
        recapList: [
          {
            recapId: 5,
            image: 'basic',
            period: new Date('2023-12-26'),
          },
          {
            recapId: 7,
            image: 'basic',
            period: new Date('2023-11-26'),
          },
        ],
      },
    ],
  },
};
