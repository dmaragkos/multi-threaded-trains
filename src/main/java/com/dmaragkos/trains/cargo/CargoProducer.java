package com.dmaragkos.trains.cargo;

import com.dmaragkos.trains.stations.StationController;

import java.util.Random;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 2:27 PM
 */
public class CargoProducer {

    private static final Random random = new Random();
    public static final long PRODUCTION_INTERVAL = 3000;
    
    private int numberOfStations;
    private int currentStation;
    private long prodInterval;

    /**
     * number of stations instead of @Station in oder to avoid iterating over the list of stations each time
     * @param numberOfStations
     */
    public CargoProducer(int numberOfStations, int currentStation) {
        this.numberOfStations = numberOfStations;
        this.currentStation = currentStation;
        this.prodInterval = PRODUCTION_INTERVAL;
    }

    public CargoProducer(int numberOfStations, int currentStation, long prodInterval) {
        this.numberOfStations = numberOfStations;
        this.currentStation = currentStation;
        this.prodInterval = prodInterval;
    }

    public long getProdInterval() {
        return prodInterval;
    }

    public Cargo produceCargo() {
        return new CargoImpl(getRandomDestination(), random.nextInt(100));
    }         

    // TODO: remove this hack
    private int getRandomDestination() {
        int destination = random.nextInt(numberOfStations);
        if(destination == currentStation) {
            return destination == 0 ? destination + 1 : destination - 1;
        }
        return destination;
    }
}
