import { LoginButton } from '@/components/loading/LoginButton/LoginButton';

export default function Home() {
  return (
    <div className="bg-custom-matcha">
      <main className="custom-bold-text text-custom-light-green">
        hello, world!
      </main>
      <LoginButton platform="kakao" />
      <LoginButton platform="google" />
    </div>
  );
}
