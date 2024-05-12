import { NoHabit } from '../HabitChecker/HabitChecker.stories';
import { HabitBtn } from '@/components/calendar/HabitBtn/HabitBtn';
interface IHabitList {
  name: string;
  alias: string;
  memberHabitId: number;
  icon: string;
}
interface IHabitShortcutProps {
  allHabits: IHabitList[];
  todayHabits: IHabitList[];
}
export const HabitShortcut = ({
  allHabits,
  todayHabits,
}: IHabitShortcutProps) => {
  return (
    <>
      <div className="flex justify-left gap-4 min-w-full pl-1 ml-4 my-4">
        {allHabits?.length !== 7 && (
          <>
            <HabitBtn memberHabitId={0} alias="추가" icon="+" shortcut={true} />
            <div className="w-1 h-24 bg-gray-100 rounded-full" />
          </>
        )}
        <div className="flex overflow-x-auto gap-4">
          {allHabits?.map((habit) => (
            <HabitBtn
              key={habit.memberHabitId}
              memberHabitId={habit.memberHabitId}
              alias={habit.alias}
              icon={habit.icon}
              shortcut={true}
            />
          ))}
        </div>
      </div>
    </>
  );
};
