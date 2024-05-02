import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { HabitContentBox } from '@/components';

export default async function Page() {
  // 진행 중인 해빗 목록 fetch
  // 완료한 해빗 목록 fetch

  return (
    <div className="p-[1rem] flex flex-col gap-[1rem]">
      <nav className="flex self-start gap-[0.5rem] items-center mb-[1rem]">
        <ChevronLeftIcon className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray" />
        <span className="custom-bold-text">물 2L 마시기</span>
      </nav>

      <HabitContentBox
        habitTotalData={[
          {
            habitList: [
              {
                content: '운동!!!!!1',
                createdAt: new Date('2024-03-26T00:00:00.000Z'),
                day: 1,
                habitTrackerId: 1,
                image: 'basic',
                succeededTime: new Date('2024-03-26T00:00:00.000Z'),
              },
              {
                content: '운동!!!!!2',
                createdAt: new Date('2024-03-25T00:00:00.000Z'),
                day: 2,
                habitTrackerId: 2,
                image: 'basic',
                succeededTime: new Date('2024-03-25T00:00:00.000Z'),
              },
              {
                content: '운동!!!!!3',
                createdAt: new Date('2024-03-24T00:00:00.000Z'),
                day: 3,
                habitTrackerId: 3,
                image: 'basic',
                succeededTime: new Date('2024-03-24T00:00:00.000Z'),
              },
              {
                content: '운동!!!!!4',
                createdAt: new Date('2024-03-23T00:00:00.000Z'),
                day: 4,
                habitTrackerId: 4,
                image: 'basic',
                succeededTime: new Date('2024-03-23T00:00:00.000Z'),
              },
            ],
            year: 2024,
          },
          {
            habitList: [
              {
                content: '운동!!!!!5',
                createdAt: new Date('2023-12-22T00:00:00.000Z'),
                day: 5,
                habitTrackerId: 5,
                image: 'basic',
                succeededTime: new Date('2023-12-26T00:00:00.000Z'),
              },
              {
                content: '운동!!!!!6',
                createdAt: new Date('2023-11-22T00:00:00.000Z'),
                day: 6,
                habitTrackerId: 6,
                image: 'basic',
                succeededTime: new Date('2023-11-26T00:00:00.000Z'),
              },
            ],
            year: 2023,
          },
        ]}
      />
    </div>
  );
}
