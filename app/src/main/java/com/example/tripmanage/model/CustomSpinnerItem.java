package com.example.tripmanage.model;

public class CustomSpinnerItem {

    private String mName;
    private String mValue;

    public CustomSpinnerItem(String name, String value) {
        mName = name;
        mValue = value;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }
}
