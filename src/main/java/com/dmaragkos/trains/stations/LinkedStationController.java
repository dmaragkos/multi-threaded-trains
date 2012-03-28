package com.dmaragkos.trains.stations;

import com.dmaragkos.trains.cargo.CargoProducer;

import java.util.Random;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 12:01 PM
 */
public class LinkedStationController implements StationController {

    private static final Random random = new Random();
    private static final int MAX_DISTANCE = 1000;

    private int numberOfStations;
    private Station start;

    public LinkedStationController(Station start) {
        this.start = start;
    }

    public LinkedStationController(int numberOfStations) {
        this.numberOfStations = numberOfStations;
        initStations();
    }

    private void initStations() {
        Station lastStation = new StationImpl(numberOfStations - 1, null, new CargoProducer(numberOfStations, numberOfStations - 1), random.nextInt(MAX_DISTANCE));
        Station prevStation = lastStation;
        for (int i = numberOfStations - 2; i >= 0; i--) {
            prevStation = new StationImpl(i, prevStation, new CargoProducer(numberOfStations, i), random.nextInt(MAX_DISTANCE));
        }
        start = prevStation;
        lastStation.setNextStation(prevStation);
    }

    public Station getStart() {
        return start;
    }

    public Station getNextStation(Station station) {
        return station.getNextStation();
    }

    public int getSize() {
        return numberOfStations;
    }
}
