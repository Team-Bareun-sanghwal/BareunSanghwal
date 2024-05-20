import { BackToHomeButton } from '@/components/notification/BackToHomeButton/BackToHomeButton';
import { NotificationList } from '@/components/notification/NotificationList/NotificationList';

export default async function Page() {
  return (
    <div className="bg-custom-white p-[1rem] flex flex-col justify-between h-screen">
      <nav className="flex self-start gap-[0.5rem] items-center w-full h-fit">
        <BackToHomeButton />
        <span className="custom-bold-text">알림</span>
      </nav>
      <div className="mt-[1rem] mb-auto min-h-[calc(100%-5rem)]">
        <NotificationList />
      </div>
    </div>
  );
}
