import type { Meta, StoryObj } from '@storybook/react';
import DayLabel from './DayLabel';

const meta = {
  title: 'DayLabel',
  component: DayLabel,
  tags: ['autodocs'],
} satisfies Meta<typeof DayLabel>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Story: Story = {};
