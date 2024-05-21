'use client';

import { useEffect, useState } from 'react';
import { getNotificationList } from '../../../app/notification/_apis/getNotificationList';
import { SingleNotification } from '../SingleNotification/SingleNotification';
import { ErrorPage } from '@/components/common/ErrorPage/ErrorPage';

interface INotificationType {
  index: number;
  notificationId: number;
  icon: string;
  content: string;
  isRead: boolean;
  createdAt: string;
}

export const NotificationList = () => {
  const [notificationList, setNotificationList] = useState([]);

  const getConsistentList = async () => {
    const result = await getNotificationList();
    setNotificationList(result.notificationList);
  };

  useEffect(() => {
    getConsistentList();
  }, []);

  return (
    <>
      {notificationList.length === 0 ? (
        <div className="h-full flex items-center">
          <ErrorPage
            errorTitle="아직 도착한 알림이 없어요"
            errorDescription=""
            buttonText=""
            nextPage=""
          />
        </div>
      ) : (
        <div className="flex flex-col items-center">
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
      )}
    </>
  );
};
