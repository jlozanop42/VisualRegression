package com.visual.enums;

public enum ImagesTypes {

    BASELINE("baseline"),
    DIFF_IMAGES("diffImages"),
    SCREENSHOTS("screenshots");

    ImagesTypes(String value) {
        this.value = value;
    }
    private String value;
    public String getValue() {
        return value;
    }
}
