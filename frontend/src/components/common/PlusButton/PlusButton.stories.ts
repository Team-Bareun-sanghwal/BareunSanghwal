import type { Meta, StoryObj } from '@storybook/react';
import { PlusButton } from './PlusButton';

const meta = {
  title: 'Common/PlusButton',
  component: PlusButton,
  tags: ['autodocs'],
  argTypes: {
    onClick: { description: 'PlusButton 클릭 시 이벤트핸들러' },
  },
} satisfies Meta<typeof PlusButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    onClick: () => {},
  },
};
