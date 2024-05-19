import type { Metadata, Viewport } from 'next';
import './globals.css';
import dynamic from 'next/dynamic';
import localFont from 'next/font/local';

const pretendard = localFont({
  src: '../../public/fonts/PretendardVariable.woff2',
  display: 'swap',
  weight: '45 920',
  variable: '--font-pretendard',
});

const Provider = dynamic(() => import('./_components/Provider'), {
  ssr: false,
});

export const metadata: Metadata = {
  title: '바른생활',
  description: '나만의 해빗 관리',
  icons: {
    icon: '/images/icon-favicon.png',
  },
};

export const viewport: Viewport = {
  width: 'device-width',
  initialScale: 1,
  maximumScale: 1,
  userScalable: false,
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko" className={`${pretendard.variable}`}>
      <body className={pretendard.className}>
        <Provider>
          <div className="w-dvw h-dvh bg-custom-white mx-auto">{children}</div>
        </Provider>
      </body>
    </html>
  );
}
