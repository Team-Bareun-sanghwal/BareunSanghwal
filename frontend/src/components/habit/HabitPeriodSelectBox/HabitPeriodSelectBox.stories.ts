import type { Meta, StoryObj } from '@storybook/react';
import { HabitPeriodSelectBox } from './HabitPeriodSelectBox';

const meta = {
  title: 'Habit/HabitPeriodSelectBox',
  component: HabitPeriodSelectBox,
  tags: ['autodocs'],
  argTypes: {},
} satisfies Meta<typeof HabitPeriodSelectBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {},
};
