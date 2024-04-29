import { NavBar } from '@/components';

export default function Layout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      {children}
      <NavBar mode="HABIT" />
    </>
  );
}
