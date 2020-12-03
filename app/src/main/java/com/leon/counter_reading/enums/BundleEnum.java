package com.leon.counter_reading.enums;

public enum BundleEnum {
    BILL_ID("bill_Id"),
    THEME("theme"),
    ON_OFF_LOAD("on_off_load"),
    READING_CONFIG("reading_config"),
    KARBARI_DICTONARY("karbari_dictionary"),
    QOTR_DICTIONARY("qotr_dictionary"),
    POSITION("position"),
    NUMBER("number"),
    TYPE("type");

    private final String value;

    BundleEnum(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
