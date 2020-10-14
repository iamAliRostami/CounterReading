package com.leon.reading_counter.enums;

/**
 * Created by Leon on 1/10/2018.
 */

public enum BundleEnum {
    BILL_ID("bill_Id");

    private final String value;

    BundleEnum(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
