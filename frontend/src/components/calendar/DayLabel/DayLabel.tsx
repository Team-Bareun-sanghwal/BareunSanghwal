export const DayLabel = () => {
  const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  return (
    <div className="grid grid-cols-7 gap-4 p-1 mx-2.5">
      {days.map((day, idx) => (
        <div key={idx} className="text-center text-custom-medium-gray">
          {day}
        </div>
      ))}
    </div>
  );
};
