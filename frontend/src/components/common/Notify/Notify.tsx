import { $Fetch } from '@/apis';
export default async function Notify() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/daily-phrase`,
    cache: 'no-cache',
  });
  const { phrase } = response.data;
  return (
    <div className="w-full my-[0.5rem] h-[2.5rem] bg-gradient-to-r from-custom-pink to-custom-sky flex items-center overflow-hidden">
      <div className="flex flowing-text font-semibold text-[1.4rem] gap-[1rem] text-custom-white">
        <span>{phrase}</span>
        <span>{phrase}</span>
        <span>{phrase}</span>
      </div>
    </div>
  );
}


// 'use client';

// export default function Notify({ text }: { text: string }) {
//   return (
//     <div className="w-full my-[0.5rem] h-[2.5rem] bg-gradient-to-r from-custom-pink to-custom-sky flex items-center overflow-hidden">
//       <div className="flex flowing-text font-semibold text-[1.4rem] gap-[1rem] text-custom-white">
//         <span>{text}</span>
//         <span>{text}</span>
//         <span>{text}</span>
//       </div>
//     </div>
//   );
// }

