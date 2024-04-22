import { NavBar } from '@/components/common/NavBar/NavBar';

export default function Home() {
  return (
    <>
      <main className="custom-bold-text text-custom-light-green">
        hello, world!
      </main>

      <NavBar mode="HOME" />
    </>
  );
}
