'use client';

import { PropsWithChildren } from 'react';
import { OverlayProvider } from '@/hooks/use-overlay';

export default function Provider({ children }: PropsWithChildren) {
  return <OverlayProvider>{children}</OverlayProvider>;
}
