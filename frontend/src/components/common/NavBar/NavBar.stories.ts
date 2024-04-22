import type { Meta, StoryObj } from '@storybook/react';
import { NavBar } from './NavBar';

const meta = {
  title: 'Common/NavBar',
  component: NavBar,
  tags: ['autodocs'],
  argTypes: {
    mode: {
      description: '현재 페이지가 가리키는 메뉴',
    },
  },
} satisfies Meta<typeof NavBar>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    mode: 'HOME',
  },
};
