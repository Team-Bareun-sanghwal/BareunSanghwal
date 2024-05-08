import { getNotificationList } from './_apis/getNotificationList';
import { SingleNotification } from './../../components/notification/SingleNotification/SingleNotification';
import { BackToHomeButton } from '@/components/notification/BackToHomeButton/BackToHomeButton';

interface INotificationType {
  notificationId: number;
  icon: string;
  content: string;
  isRead: boolean;
  createdAt: string;
}

export default async function Page() {
  const result = await getNotificationList();
  const notificationList = result.notificationList;

  return (
    <div className="bg-custom-white p-[1rem] flex flex-col justify-between min-h-full">
      <nav className="flex self-start gap-[0.5rem] items-center">
        <BackToHomeButton />
        <span className="custom-bold-text">신규 해빗 등록</span>
      </nav>
      <div className="flex flex-col items-center mt-[1rem]">
        {notificationList.map((no: INotificationType, index: number) => {
          return (
            <SingleNotification
              key={no.notificationId}
              index={index}
              notificationId={no.notificationId}
              icon={no.icon}
              content={no.content}
              isRead={no.isRead}
              createdAt={no.createdAt}
            />
          );
        })}
      </div>
    </div>
  );
}
