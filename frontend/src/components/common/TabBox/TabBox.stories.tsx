import type { Meta, StoryObj } from '@storybook/react';
import { TabBox } from './TabBox';
import { TextAreaBox } from '../TextAreaBox/TextAreaBox';
import { ImageUploadBox } from '../ImageUploadBox/ImageUploadBox';

const meta = {
  title: 'Common/TabBox',
  component: TabBox,
  tags: ['autodocs'],
  argTypes: {
    tabs: {
      description: '탭 제목(title)과 컴포넌트(component) 속성을 갖는 객체',
    },
  },
} satisfies Meta<typeof TabBox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const HabitWrite: Story = {
  args: {
    tabs: [
      {
        title: '텍스트 작성',
        component: <TextAreaBox text="안녕" setText={() => {}} />,
      },
      {
        title: '이미지 첨부',
        component: <ImageUploadBox image={null} setImage={() => {}} />,
      },
    ],
  },
};
