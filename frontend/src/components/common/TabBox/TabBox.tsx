import { ReactElement, useState } from 'react';

type TabLists = {
  title: string;
  component: ReactElement;
};

interface TabBoxProps {
  tabs: TabLists[];
}

export const TabBox = ({ tabs }: TabBoxProps) => {
  const [selectedTabIndex, setSelectedTabIndex] = useState<number>(0);

  return (
    <section className={`flex flex-col justify-center gap-[2rem]`}>
      <ul
        className={`w-full py-[1rem] custom-semibold-text flex list-none border-b-[0.15rem] border-custom-medium-gray`}
      >
        {tabs?.map((tab, index) => {
          return (
            <>
              <li
                key={`title-${index}`}
                className={`${selectedTabIndex === index ? 'text-custom-black' : 'text-custom-medium-gray'} flex-grow text-center cursor-pointer relative`}
                onClick={() => setSelectedTabIndex(index)}
              >
                <>{tab.title}</>
                {selectedTabIndex === index && (
                  <div className="absolute -bottom-[1rem] left-1/3 w-1/3 h-[0.5rem] rounded-t-[2rem] bg-custom-black"></div>
                )}
              </li>
            </>
          );
        })}
      </ul>

      <>{tabs[selectedTabIndex].component}</>
    </section>
  );
};
