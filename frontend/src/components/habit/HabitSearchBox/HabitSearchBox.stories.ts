import type { Meta, StoryObj } from '@storybook/react';
import { HabitSearchBox } from './HabitSearchBox';

const meta = {
  title: 'Habit/HabitSearchBox',
  component: HabitSearchBox,
  tags: ['autodocs'],
  argTypes: {},
} satisfies Meta<typeof HabitSearchBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {
  args: {
    selectedHabitId: 1,
    setSelectedHabitId: () => {},
  },
};
