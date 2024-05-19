'use client';

import { useEffect, useState } from 'react';
import { getNotificationList } from '../../../app/notification/_apis/getNotificationList';
import { SingleNotification } from '../SingleNotification/SingleNotification';

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
    <div className="flex flex-col items-center">
      {notificationList ? (
        notificationList.map((no: INotificationType, index: number) => {
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
        })
      ) : (
        <div>dkdkdks</div>
      )}
    </div>
  );
};
