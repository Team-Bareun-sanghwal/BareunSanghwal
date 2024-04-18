import '../src/app/globals.css';
import type { Preview } from '@storybook/react';

const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
    backgrounds: {
      default: 'dark',
    },
    nextjs: {
      router: {
        push(...args) {
          return Promise.resolve(true);
        },
      },
    },
  },
};

export default preview;
