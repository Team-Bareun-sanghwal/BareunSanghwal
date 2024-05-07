importScripts(
  'https://www.gstatic.com/firebasejs/9.0.2/firebase-app-compat.js',
);
importScripts(
  'https://www.gstatic.com/firebasejs/9.0.2/firebase-messaging-compat.js',
);

firebase.initializeApp({
  apiKey: 'AIzaSyAbcSOk4v64N_OtDrAwg6NqCfD4V9ybRVM',
  authDomain: 'bareun-life.firebaseapp.com',
  projectId: 'bareun-life',
  storageBucket: 'bareun-life.appspot.com',
  messagingSenderId: '440103641572',
  appId: '1:440103641572:web:5da3f683fe540b222827f1',
});

const messaging = firebase.messaging();
