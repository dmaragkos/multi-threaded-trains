package com.dmaragkos.trains;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 11:48 AM
 */
public enum TrainState {
    MOVING("is moving"),
    STOPPED("is stopped at station"),
    WAITING("is waiting");

    private final String description;

    private TrainState(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
