'use client';

import { PropsWithChildren } from 'react';
import { OverlayProvider } from '@/hooks/use-overlay';
import { usePathname } from 'next/navigation';

export default function Provider({ children }: PropsWithChildren) {
  const pathName = usePathname();
  if (pathName !== '/token') {
    sessionStorage.setItem('previousUrl', pathName);
  }
  return <OverlayProvider>{children}</OverlayProvider>;
}
