import type { Meta, StoryObj } from '@storybook/react';
import ColoredText from './ColoredText';

const meta = {
  title: 'point/ColoredText',
  component: ColoredText,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {
    color: { description: '얻은 스트릭 색상' },
  },
} satisfies Meta<typeof ColoredText>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Red: Story = {
  args: {
    color: 'red',
  },
};
export const React: Story = {
  args: {
    color: 'react',
  },
};

export const Spring: Story = {
  args: {
    color: 'spring',
  },
};

export const Dippindots: Story = {
  args: {
    color: 'dippindots',
  },
};
