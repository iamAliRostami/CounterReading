package com.leon.reading_counter.enums;
public enum HighLowStateEnum {
    NORMAL(0),
    HIGH(2),
    LOW(4),
    ZERO(8),
    UN_CALCULATED(16);

    private final int value;

    HighLowStateEnum(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
