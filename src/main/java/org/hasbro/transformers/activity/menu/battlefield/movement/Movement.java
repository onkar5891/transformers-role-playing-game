package org.hasbro.transformers.activity.menu.battlefield.movement;

public enum Movement {
    UP("Move Up"),
    DOWN("Move Down"),
    LEFT("Move Left"),
    RIGHT("Move Right");

    private String title;

    Movement(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
