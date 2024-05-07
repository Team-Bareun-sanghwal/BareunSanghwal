import type { Metadata } from 'next';
import './globals.css';
import dynamic from 'next/dynamic';
const Provider = dynamic(() => import('./_components/Provider'), {
  ssr: false,
});

export const metadata: Metadata = {
  title: '바른생활',
  description: '나만의 해빗 관리',
  icons: {
    icon: '/images/icon-block1.png',
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body className="bg-custom-black-with-opacity">
        <Provider>
          <div className="w-[36rem] min-h-screen bg-custom-white mx-auto">
            {children}
          </div>
        </Provider>
      </body>
    </html>
  );
}
