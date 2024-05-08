import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';
import { $Fetch } from '@/apis';

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: process.env.NEXT_PUBLIC_API_KEY,
  authDomain: process.env.NEXT_PUBLIC_AUTH_DOMAIN,
  projectId: process.env.NEXT_PUBLIC_PROJECT_ID,
  storageBucket: process.env.NEXT_PUBLIC_STORAGE_BUCKET,
  messagingSenderId: process.env.NEXT_PUBLIC_MESSAGING_SENDER_ID,
  appId: process.env.NEXT_PUBLIC_APP_ID,
};

const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

export async function requestPermission() {
  // console.log('권한 요청 중...');

  const permission = await Notification.requestPermission();
  if (permission === 'denied') {
    // console.log('알림 권한 허용 안됨');
    return;
  }

  // console.log('알림 권한이 허용됨');
  const token = await getToken(messaging, {
    vapidKey: process.env.NEXT_PUBLIC_VAPID_KEY,
  });

  if (token) {
    await $Fetch({
      method: 'POST',
      url: `${process.env.NEXT_PUBLIC_BASE_URL}/notifications`,
      cache: 'default',
      data: {
        notificationToken: token,
      },
    });
  }

  onMessage(messaging, (payload) => {
    console.log('메시지가 도착했습니다.', payload);
  });
}
