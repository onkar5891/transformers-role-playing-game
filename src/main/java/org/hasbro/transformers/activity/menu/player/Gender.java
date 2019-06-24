package org.hasbro.transformers.activity.menu.player;

public enum Gender {
    MALE("Male"), FEMALE("Female");

    private String title;

    Gender(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}