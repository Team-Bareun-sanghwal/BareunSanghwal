import { Head } from '@/components/main/Head/Head';
import { NavBar } from '@/components';
export default async function MainLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div>
      <Head />
      {children}
      <NavBar mode="HOME" />
    </div>
  );
}
