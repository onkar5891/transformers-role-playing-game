package org.hasbro.transformers.activity.menu.main;

public enum MainMenu {
    START("Start New Battle"),
    LOAD("Load Battle"),
    EXIT("Exit");

    private String title;

    MainMenu(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
