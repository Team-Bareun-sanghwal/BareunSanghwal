import { $Fetch } from '@/apis';
import { StatisticsPieChart } from '../StatisticsPieChart/StatisticsPieChart';
import { StatisticsBarChart } from '../StatisticsBarChart/StatisticsBarChart';
import { StatisticsSpineAreaGraph } from '../StatisticsSpineAreaGraph/StatisticsSpineAreaGraph';
import { ReportOnlyText } from '../ReportOnlyText/ReportOnlyText';
import { GuideBox } from '@/components/common/GuideBox/GuideBox';
import { ColoredSentence } from '@/components/common/ColoredSentence/ColoredSentence';

// const jsonAccess = await $Fetch({
//   method: 'GET',
//   url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/statistic`,
//   cache: 'default',
// });

// console.log(jsonAccess);

const data = {
  topPracticedHabits: [
    {
      habit: '위스키 마시기',
      value: 27,
    },
    {
      habit: '와인 마시기',
      value: 21,
    },
    {
      habit: '악기 연주',
      value: 18,
    },
    {
      habit: '요리하기',
      value: 15,
    },
    {
      habit: '축구',
      value: 11,
    },
    {
      habit: '기타',
      value: 8,
    },
  ],
  maxPracticedHabit: '위스키 마시기',

  practicePerDayOfWeek: [
    {
      day: '일',
      value: 45,
      colorIdx: 1,
    },
    {
      day: '월',
      value: 67,
      colorIdx: 2,
    },
    {
      day: '화',
      value: 56,
      colorIdx: 1,
    },
    {
      day: '수',
      value: 53,
      colorIdx: 1,
    },
    {
      day: '목',
      value: 32,
      colorIdx: 1,
    },
    {
      day: '금',
      value: 11,
      colorIdx: 0,
    },
    {
      day: '토',
      value: 12,
      colorIdx: 1,
    },
  ],

  achievement_per_hour: [
    {
      time: 0,
      value: 34,
    },
    {
      time: 1,
      value: 8,
    },
    {
      time: 2,
      value: 2,
    },
    {
      time: 3,
      value: 5,
    },
    {
      time: 4,
      value: 5,
    },
    {
      time: 5,
      value: 3,
    },
    {
      time: 6,
      value: 14,
    },
    {
      time: 7,
      value: 37,
    },
    {
      time: 8,
      value: 26,
    },
    {
      time: 9,
      value: 17,
    },
    {
      time: 10,
      value: 15,
    },
    {
      time: 11,
      value: 28,
    },
    {
      time: 12,
      value: 73,
    },
    {
      time: 13,
      value: 83,
    },
    {
      time: 14,
      value: 75,
    },
    {
      time: 15,
      value: 77,
    },
    {
      time: 16,
      value: 80,
    },
    {
      time: 17,
      value: 86,
    },
    {
      time: 18,
      value: 92,
    },
    {
      time: 19,
      value: 96,
    },
    {
      time: 20,
      value: 71,
    },
    {
      time: 21,
      value: 73,
    },
    {
      time: 22,
      value: 35,
    },
    {
      time: 23,
      value: 22,
    },
  ],

  // 5 Figma 수치와 동일하게 설정해둠
  totalDays: 173,
  streakDays: 149,
  starredDays: 52,
  longestStreak: 103,
};

let maxDayValue = 0;
let minDayValue = 999999;
let maxDay = '';
let minDay = '';

data.practicePerDayOfWeek.map((day) => {
  if (maxDayValue < day.value) {
    maxDayValue = day.value;
    maxDay = day.day;
  }
  if (day.value < minDayValue) {
    minDayValue = day.value;
    minDay = day.day;
  }
});

export const StatisticsContent = () => {
  return (
    <div className="py-[2rem] flex flex-col gap-[3rem]">
      <ReportOnlyText
        whole_days={data.totalDays}
        streak_days={data.streakDays}
        star_days={data.starredDays}
        longest_streak={data.longestStreak}
      />

      <div>
        <p className="custom-medium-text">상위 달성 해빗</p>
        <StatisticsPieChart data={data.topPracticedHabits} />
        <GuideBox
          guideText={
            <ColoredSentence
              textFront={'내가 가장 잘 달성한 해빗은 '}
              textMiddle={data.maxPracticedHabit}
              textBack={' !'}
            />
          }
        />
      </div>

      <div className="flex flex-col items-center">
        <p className="custom-medium-text mr-auto">요일 통계</p>
        <StatisticsBarChart data={data.practicePerDayOfWeek} />
        <div className="h-[2rem]" />
        <GuideBox
          guideText={
            <div>
              <ColoredSentence
                textFront={'나는 주로 '}
                textMiddle={`${maxDay}요일`}
                textBack={'에 해빗을 많이 달성하고'}
              />
              <ColoredSentence
                textFront={''}
                textMiddle={`${minDay}요일`}
                textBack={'에 적게 달성하는 편이에요'}
              />
            </div>
          }
        />
      </div>

      <div>
        <p className="custom-medium-text">해빗 달성 시간</p>
        <StatisticsSpineAreaGraph data={data.achievement_per_hour} />
        <div className="h-[2rem]" />
        <GuideBox guideText="내가 해빗을 달성한 시간을 보여주는 그래프예요" />
      </div>
    </div>
  );
};
