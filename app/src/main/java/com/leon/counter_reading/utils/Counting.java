package com.leon.counter_reading.utils;

import static com.leon.counter_reading.utils.CalendarTool.findDifferentDays;

public class Counting {

    double dailyAverage(int preNumber, int currentNumber, String preDate) {
        return (currentNumber - preNumber) / (double) findDifferentDays(preDate);
    }

    double monthlyAverage(int preNumber, int currentNumber, String preDate) {
        return dailyAverage(preNumber, currentNumber, preDate) * 30;
    }
}
