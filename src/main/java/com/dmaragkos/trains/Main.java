package com.dmaragkos.trains;

import com.dmaragkos.trains.Train;
import com.dmaragkos.trains.stations.LinkedStationController;
import com.dmaragkos.trains.stations.StationController;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 6:47 PM
 */
public class Main {

    private static final int NO_OF_STATIONS = 8;
    private static final int NO_OF_TRAINS = 4;
    private static final Train[] trains = new Train[NO_OF_TRAINS];

    public static void main(String[] args) {
        StationController stationController = new LinkedStationController(NO_OF_STATIONS);
        for (int i = 0; i < NO_OF_TRAINS; i++) {
            trains[i] = new Train(i, 100, i + 1, stationController);
        }
        for (int i = 0; i < NO_OF_TRAINS; i++) {
            new Thread(trains[i]).start();
        }
    }
}
