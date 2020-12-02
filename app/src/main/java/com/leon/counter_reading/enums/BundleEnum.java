package com.leon.counter_reading.enums;

public enum BundleEnum {
    BILL_ID("bill_Id"),
    THEME("theme"),
    ON_OFF_LOAD("on_off_load"),
    READING_CONFIG_DEFAULT_DTOS("reading_config_default_dtos"),
    TYPE("type");

    private final String value;

    BundleEnum(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
