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
        console.log('3. 토큰이 안 나와잉....');
      } else {
        console.log('3. 토큰 발급 완료');
        const result = await $Fetch({
          method: 'POST',
          url: `${process.env.NEXT_PUBLIC_BASE_URL}/notifications`,
          cache: 'default',
          data: {
            notificationToken: currentToken,
          },
        });
        if ((await result.status) === 201) {
          console.log('4. 토큰 등록 완료!!');
          // window.location.href = 'https://bareun.life/main';
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
      // 푸시 승인됐을 때 처리할 내용
      console.log('2. 푸시 승인됨 : ', Notification.permission);
      setToken();
    } else {
      // 푸시 거부됐을 때 처리할 내용
      console.log('2. 푸시 거부됨 : ', Notification.permission);
    }
  });
};

export const checkPermission = () => {
  if (Notification.permission === 'default') {
    console.log('1. 알림 기본 값 없음');
    popPermission();
  } else {
    console.log('1. 알림 기본 값 존재 : ', Notification.permission);
  }
};
