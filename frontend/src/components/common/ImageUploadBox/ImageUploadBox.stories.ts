import type { Meta, StoryObj } from '@storybook/react';
import { ImageUploadBox } from './ImageUploadBox';

const meta = {
  title: 'Common/ImageUploadBox',
  component: ImageUploadBox,
  tags: ['autodocs'],
  argTypes: {},
} satisfies Meta<typeof ImageUploadBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Main: Story = {};
