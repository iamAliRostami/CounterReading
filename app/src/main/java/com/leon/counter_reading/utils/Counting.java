package com.leon.counter_reading.utils;

import com.leon.counter_reading.tables.ReadingData;

import static com.leon.counter_reading.utils.CalendarTool.findDifferentDays;

public class Counting {

    public static double dailyAverage(int preNumber, int currentNumber, String preDate) {
        return (currentNumber - preNumber) / (double) findDifferentDays(preDate);
    }

    public static double monthlyAverage(int preNumber, int currentNumber, String preDate) {
        return dailyAverage(preNumber, currentNumber, preDate) * 30;
    }

    public static int checkHighLow(ReadingData.OnOffLoadDto onOffLoadDto,
                                   ReadingData.KarbariDto karbariDto,
                                   ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto,
                                   int currentNumber) {
        double average = monthlyAverage(onOffLoadDto.preNumber, currentNumber, onOffLoadDto.preDate);
        double preAverage = onOffLoadDto.preAverage;
        int difference = currentNumber - onOffLoadDto.preNumber;
        if (karbariDto.isMaskooni) {
            if (readingConfigDefaultDto.highConstBoundMaskooni < difference)
                return 1;
            else if (readingConfigDefaultDto.lowConstBoundMaskooni > difference)
                return -1;
            else if ((100 + readingConfigDefaultDto.highPercentBoundMaskooni) * average < (preAverage * 100))
                return 1;
            else if ((100 - readingConfigDefaultDto.lowPercentBoundMaskooni) * average > (preAverage * 100))
                return -1;
        } else if (karbariDto.isSaxt) {
            if (readingConfigDefaultDto.highConstBoundSaxt < average)
                return 1;
            else if (readingConfigDefaultDto.lowConstBoundSaxt > average)
                return -1;
            else if ((100 + readingConfigDefaultDto.highPercentBoundSaxt) * average < (preAverage * 100))
                return 1;
            else if ((100 - readingConfigDefaultDto.lowPercentBoundSaxt) * average > (preAverage * 100))
                return -1;
        } else if (onOffLoadDto.ahadTejariOrFari > 0) {
            if ((100 + readingConfigDefaultDto.highPercentRateBoundNonMaskooni) * average < (preAverage * 100))
                return 1;
            else if ((100 - readingConfigDefaultDto.lowPercentRateBoundNonMaskooni) * average > (preAverage * 100))
                return -1;
        }
        return 0;
    }
}