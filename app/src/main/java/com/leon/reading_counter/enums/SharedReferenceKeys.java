package com.leon.reading_counter.enums;

public enum SharedReferenceKeys {
    USERNAME("username"),
    PASSWORD("password"),
    TOKEN("token");

    private final String value;

    SharedReferenceKeys(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
