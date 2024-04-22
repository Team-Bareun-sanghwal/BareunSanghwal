import type { Meta, StoryObj } from '@storybook/react';
import { TextAreaBox } from './TextAreaBox';

const meta = {
  title: 'Common/TextAreaBox',
  component: TextAreaBox,
  tags: ['autodocs'],
} satisfies Meta<typeof TextAreaBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {};
