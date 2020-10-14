package com.leon.reading_counter.enums;

public enum SharedReferenceNames {
    ACCOUNT("com.app.leon.reading_counter.account_info");

    private final String value;

    SharedReferenceNames(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
