export const convertEnglishDayNameToIndex = (englishDayName: string) => {
  switch (englishDayName) {
    case 'monday': {
      return 1;
    }
    case 'tuesday': {
      return 2;
    }
    case 'wednesday': {
      return 3;
    }
    case 'thursday': {
      return 4;
    }
    case 'friday': {
      return 5;
    }
    case 'saturday': {
      return 6;
    }
    case 'sunday': {
      return 7;
    }
    default: {
      return -1;
    }
  }
};

export const convertEnglishDayNameToKoreanDayName = (
  englishDayName: string,
) => {
  switch (englishDayName) {
    case 'monday': {
      return '월';
    }
    case 'tuesday': {
      return '화';
    }
    case 'wednesday': {
      return '수';
    }
    case 'thursday': {
      return '목';
    }
    case 'friday': {
      return '금';
    }
    case 'saturday': {
      return '토';
    }
    case 'sunday': {
      return '일';
    }
    default: {
      return '월';
    }
  }
};

export const getHeightStyle = (heightRate: number) => {
  return heightRate <= 5
    ? 10
    : heightRate <= 10
      ? 20
      : heightRate <= 15
        ? 30
        : heightRate <= 20
          ? 40
          : heightRate <= 25
            ? 50
            : heightRate <= 30
              ? 60
              : heightRate <= 35
                ? 70
                : heightRate <= 40
                  ? 80
                  : heightRate <= 45
                    ? 90
                    : heightRate <= 50
                      ? 100
                      : heightRate <= 55
                        ? 110
                        : heightRate <= 60
                          ? 120
                          : heightRate <= 65
                            ? 130
                            : heightRate <= 70
                              ? 140
                              : heightRate <= 75
                                ? 150
                                : heightRate <= 80
                                  ? 160
                                  : heightRate <= 85
                                    ? 170
                                    : heightRate <= 90
                                      ? 180
                                      : heightRate <= 95
                                        ? 190
                                        : 200;
};
