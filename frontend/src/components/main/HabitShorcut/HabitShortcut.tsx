import { HabitBtn } from '@/components/calendar/HabitBtn/HabitBtn';
interface IHabitList {
  name: string;
  alias: string;
  memberHabitId: number;
  habitTrackerId: number;
  icon: string;
  succeededTime: string;
}
interface IHabitShortcutProps {
  allHabits: IHabitList[];
  todayHabits: IHabitList[];
}

export const HabitShortcut = ({
  allHabits,
  todayHabits,
}: IHabitShortcutProps) => {
  const sortedHabits = () => {
    const succeeded = todayHabits.filter(
      (habit) => habit.succeededTime !== null,
    );
    const yet = todayHabits.filter((habit) => habit.succeededTime == null);
    return { succeeded, yet };
  };
  return (
    <>
      <div className="flex items-center justify-left gap-4 min-w-full pl-1 ml-6 my-4">
        {allHabits?.length !== 7 && (
          <>
            <HabitBtn
              memberHabitId={0}
              alias="추가"
              icon="+"
              shortcut={true}
              add={true}
            />
            <div className="w-1 h-20 mb-4 conten-center bg-gray-200 rounded-full" />
          </>
        )}
        <div className="flex overflow-x-auto gap-4 pb-2">
          {sortedHabits().yet.map((habit) => (
            <HabitBtn
              key={habit.memberHabitId}
              memberHabitId={habit.habitTrackerId}
              alias={habit.alias}
              icon={habit.icon}
              shortcut={true}
              succeededTime={habit.succeededTime}
              today={true}
            />
          ))}
          {sortedHabits().succeeded.map((habit) => (
            <HabitBtn
              key={habit.memberHabitId}
              memberHabitId={habit.habitTrackerId}
              alias={habit.alias}
              icon={habit.icon}
              shortcut={true}
              succeededTime={habit.succeededTime}
              today={true}
            />
          ))}

          <div className="flex min-w-8 h-20" />
        </div>
      </div>
    </>
  );
};
