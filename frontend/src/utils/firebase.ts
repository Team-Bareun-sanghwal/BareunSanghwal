import { initializeApp } from 'firebase/app';
import { getMessaging, getToken } from 'firebase/messaging';

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

async function requestPermission() {
  const permission = await Notification.requestPermission();

  if (permission === 'denied')
    throw new Error('파이어베이스로부터 토큰을 받는데 실패');

  const token = await getToken(messaging, {
    vapidKey: process.env.NEXT_PUBLIC_VAPID_KEY,
  });

  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/notifications`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization:
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6IjEiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0MTA0Njg3LCJleHAiOjE3MTMzMzMzOTEwODd9.fiwjrUdcc14-eLMUuhYtYQxLEP9eEynCnUMyBTdjXBI',
      },
      credentials: 'include',
      body: JSON.stringify({ notificationToken: token }),
    },
  );

  if (!response.ok)
    throw new Error('파이어베이스로부터 반환된 토큰을 전달하는데 실패');
}
