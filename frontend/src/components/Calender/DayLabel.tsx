const DayLabel = () => {
  const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  return (
    <div className="flex w-full grid grid-cols-7 gap-4 p-1">
      {days.map((day, idx) => (
        <div key={idx} className="text-center text-custom-medium-gray">
          {day}
        </div>
      ))}
    </div>
  );
};
export default DayLabel;
