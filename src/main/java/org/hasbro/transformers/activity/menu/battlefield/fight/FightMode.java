package org.hasbro.transformers.activity.menu.battlefield.fight;

public enum FightMode {
    ATTACK("Attack"),
    DEFENSE("Defend"),
    STAY_STILL("Stay Still"),
    RETREAT("Retreat");

    private String title;

    FightMode(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
