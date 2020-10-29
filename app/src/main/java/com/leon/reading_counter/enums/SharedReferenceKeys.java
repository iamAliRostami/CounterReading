package com.leon.reading_counter.enums;

public enum SharedReferenceKeys {
    USERNAME("username"),
    PASSWORD("password"),
    TOKEN("token"),
    REFRESH_TOKEN("refresh_token"),
    ANTIFORGERY("Antiforgery"),
    XSRF("xsrf"),
    THEME_STABLE("theme_stable"),
    THEME_TEMPORARY("theme_temporary");

    private final String value;

    SharedReferenceKeys(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
