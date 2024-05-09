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

export const setTokenHandler = async () => {
  const messaging = getMessaging(app);

  await getToken(messaging, {
    vapidKey: process.env.NEXT_PUBLIC_VAPID_KEY,
  })
    .then(async (currentToken) => {
      if (!currentToken) {
        console.log('토큰이 안 나와잉....');
      } else {
        console.log('토큰 발급 완');
        const result = await $Fetch({
          method: 'POST',
          url: `${process.env.NEXT_PUBLIC_BASE_URL}/notifications`,
          cache: 'default',
          data: {
            notificationToken: token,
          },
        });
        if ((await result.status) === 200) {
          window.location.href = 'https://bareun.life/main';
        }
      }
    })
    .catch((error) => {
      console.error(error);
    });

  // // Your web app's Firebase configuration
  // if ('Notification' in window) {
  //   // Notification API가 지원되는 경우
  //   if (Notification.permission === 'default') {
  //     // 권한이 설정되지 않은 경우, 사용자에게 권한 요청
  //     Notification.requestPermission().then((permission) => {
  //       if (permission === 'granted') {
  //         console.log('Notification permission granted.');
  //       } else {
  //         console.log('Notification permission denied.');
  //         return;
  //       }
  //     });
  //   } else if (Notification.permission === 'granted') {
  //     console.log('Notification permission already granted.');
  //   } else {
  //     console.log('Notification permission denied.');
  //     return;
  //   }
  // } else {
  //   // Notification API가 지원되지 않는 경우
  //   console.log('This browser does not support notifications.');
  //   return;
  // }
};
