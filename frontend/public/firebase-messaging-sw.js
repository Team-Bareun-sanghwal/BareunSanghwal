// self.addEventListener('install', function (e) {
//   console.log('fcm sw install..');
//   self.skipWaiting();
// });

// self.addEventListener('activate', function (e) {
//   console.log('fcm sw activate..');
// });

importScripts(
  'https://www.gstatic.com/firebasejs/9.0.2/firebase-app-compat.js',
);
importScripts(
  'https://www.gstatic.com/firebasejs/9.0.2/firebase-messaging-compat.js',
);

// navigator.serviceWorker.register('firebase-messaging-sw');

firebase.initializeApp({
  apiKey: 'AIzaSyAbcSOk4v64N_OtDrAwg6NqCfD4V9ybRVM',
  authDomain: 'bareun-life.firebaseapp.com',
  projectId: 'bareun-life',
  storageBucket: 'bareun-life.appspot.com',
  messagingSenderId: '440103641572',
  appId: '1:440103641572:web:5da3f683fe540b222827f1',
});

const messaging = firebase.messaging();

// push 발생 시 알림 띄우기
self.addEventListener('push', function (e) {
  if (!e.data.json()) return;

  const resultData = e.data.json();
  const notificationTitle = resultData.notification.title;
  const notificationOptions = {
    body: resultData.notification.body,
    icon: resultData.notification.image,
    tag: resultData.notification.tag,
    data: resultData.data,
    ...resultData,
  };
  registration.showNotification(notificationTitle, notificationOptions);
});

// 알림 클릭 시 알림 페이지로 이동
self.addEventListener('notificationclick', function (event) {
  event.notification.close();
  event.waitUntil(clients.openWindow(event.notification.data.url));
});
