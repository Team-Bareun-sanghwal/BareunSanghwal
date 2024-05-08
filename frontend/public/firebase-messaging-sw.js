self.addEventListener('install', function (e) {
  console.log('fcm sw install..');
  self.skipWaiting();
});

self.addEventListener('activate', function (e) {
  console.log('fcm sw activate..');
});

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

self.addEventListener('notificationclick', function (event) {
  event.notification.close();
  event.waitUntil(clients.openWindow(event.notification.data.url));
});
