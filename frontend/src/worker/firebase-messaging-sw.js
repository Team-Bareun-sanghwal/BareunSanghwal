import { initializeApp } from 'firebase/app';
import { getMessaging, getToken } from 'firebase/messaging';
import { $Fetch } from '@/apis';

const firebaseConfig = {
  apiKey: process.env.NEXT_PUBLIC_API_KEY,
  authDomain: process.env.NEXT_PUBLIC_AUTH_DOMAIN,
  projectId: process.env.NEXT_PUBLIC_PROJECT_ID,
  storageBucket: process.env.NEXT_PUBLIC_STORAGE_BUCKET,
  messagingSenderId: process.env.NEXT_PUBLIC_MESSAGING_SENDER_ID,
  appId: process.env.NEXT_PUBLIC_APP_ID,
};

const app = initializeApp(firebaseConfig);

export const setToken = async () => {
  const messaging = getMessaging(app);

  await getToken(messaging, {
    vapidKey: process.env.NEXT_PUBLIC_VAPID_KEY,
  })
    .then(async (currentToken) => {
      if (!currentToken) {
      } else {
        const result = await $Fetch({
          method: 'POST',
          url: `${process.env.NEXT_PUBLIC_BASE_URL}/notifications`,
          cache: 'default',
          data: {
            notificationToken: currentToken,
          },
        });
        if ((await result.status) === 201) {
        }
      }
    })
    .catch((error) => {
      console.error(error);
    });
};

export const popPermission = () => {
  Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      // 푸시 승인
      setToken();
    } else {
      // 푸시 거부
    }
  });
};

export const checkPermission = () => {
  if (Notification.permission === 'default') {
    popPermission();
  } else {
    if (Notification.permission === 'granted') {
      // 푸시 승인
      setToken();
    }
  }
};
