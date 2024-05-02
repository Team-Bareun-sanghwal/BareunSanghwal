type Request = {
  method: 'GET' | 'POST' | 'PUT' | 'DELETE';
  url: string;
  data?: object;
  cache:
    | 'no-cache'
    | 'no-store'
    | 'reload'
    | 'force-cache'
    | 'only-if-cached'
    | 'default';
};
export async function $Fetch({ method, url, data, cache }: Request) {
  try {
    const res = await fetch(url, {
      method,
      cache: cache,
      headers: {
        'Content-Type': 'application/json',
        Authorization:
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6IjEiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0MTA0Njg3LCJleHAiOjE3MTMzMzMzOTEwODd9.fiwjrUdcc14-eLMUuhYtYQxLEP9eEynCnUMyBTdjXBI',
      },
      body: JSON.stringify(data),
    });
    if (res.status != 200) {
      throw new Error(`[HTTP ERR] Status : ${res.status}`);
    }
    return res.json();
  } catch (e) {
    console.log('Fetch Error : ', e);
    throw e;
  }
}
