package ru.ifmo.se.pokemon;

public enum Stat {
    HP(false), ATTACK(false), DEFENSE(false), SPEED(false), SPECIAL_ATTACK(false), SPECIAL_DEFENSE(false),
    ACCURACY(true), EVASION(true);

    private boolean hidden;

    Stat(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }
}
