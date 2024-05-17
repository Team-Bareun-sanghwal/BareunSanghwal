/** @type {import('next').NextConfig} */

import withPWAInit from '@ducanh2912/next-pwa';
import withBundleAnalyzer from '@next/bundle-analyzer';

const withPWA = withPWAInit({
  dest: 'public',
});

const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'kr.object.ncloudstorage.com',
      },
    ],
  },
};

const exportObject =
  process.env.NEXT_PUBLIC_ANALYZE === 'true'
    ? withBundleAnalyzer(nextConfig)
    : withPWA(nextConfig);

export default exportObject;

// export default withPWA(nextConfig);
