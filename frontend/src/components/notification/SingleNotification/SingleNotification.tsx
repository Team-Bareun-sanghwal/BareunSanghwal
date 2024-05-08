interface INotificationType {
  index: number;
  notificationId: number;
  icon: string;
  content: string;
  isRead: boolean;
  createdAt: string;
}

export const SingleNotification = ({
  index,
  notificationId,
  icon,
  content,
  isRead,
  createdAt,
}: INotificationType) => {
  const borderStyle = index === 0 ? 'border-b border-t' : 'border-b';
  const backgroundStyle = isRead ? 'bg-custom-light-gray' : '';
  const date = createdAt.replace('T', ' ');

  return (
    <div
      className={`w-screen h-[10rem] px-[2.5rem] flex items-center justify-between ${borderStyle} ${backgroundStyle} border-custom-light-gray`}
    >
      <div className="bg-custom-medium-gray w-[3rem] h-[3rem]" />
      <div className="w-[26rem]">
        <div className="text-custom-dark-gray custom-medium-text">
          {content}
        </div>
        <div className="text-custom-medium-gray text-right">{date}</div>
      </div>
    </div>
  );
};
