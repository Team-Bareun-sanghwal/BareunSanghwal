import type { Meta, StoryObj } from '@storybook/react';
import { RecapImage } from './RecapImage';

const meta = {
  title: 'Recap/RecapImage',
  component: RecapImage,
  parameters: {
    layout: 'centered',
    backgrounds: {
      default: 'dark',
    },
  },
  tags: ['autodocs'],
  argTypes: {
    image: { description: '이미지 url' },
  },
} satisfies Meta<typeof RecapImage>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Image: Story = {
  args: {
    image:
      'https://kr.object.ncloudstorage.com//bareun-object-storage/tracker_achieve_image/0d419ac8-d59f-4041-ab3f-441527f60ec9.png',
  },
};
