package com.dmaragkos.trains;

import com.dmaragkos.trains.cargo.Cargo;
import com.dmaragkos.trains.stations.Station;
import com.dmaragkos.trains.stations.StationController;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 11:46 AM
 */
public class Train implements Runnable {

    private static final Logger logger = Logger.getLogger(Train.class);

    private StationController stationController;
    private Station currentStation;
    private Map<Integer, List<Cargo>> cargo;
    private TrainState state;
    private int speed;
    private int capacity;
    private int id;

    private volatile boolean terminateRequested;

    public Train(int id, int speed, int capacity, StationController stationController) {
        this.id = id;
        this.speed = speed;
        this.stationController = stationController;
        this.state = TrainState.STOPPED;
        this.currentStation = stationController.getStart();
        this.capacity = capacity;
        this.cargo = new HashMap<Integer, List<Cargo>>(capacity);
    }

    /**
     * Travels to next station according to the train's speed
     */
    public void travelToNextStation() {
        unloadCargo();
        loadCargo();
        occupyRailToNextStation();
        travel();
        arriveAtNextStation();
    }

    public void run() {
        while(!terminateRequested) {
            travelToNextStation();
        }
    }

    private void occupyRailToNextStation() {
        logger.debug(String.format("Train %s tries to occupy rail from station %s to station %s", this, currentStation, stationController.getNextStation(currentStation)));
        stationController.getNextStation(currentStation).occupyRail(this);
        logger.debug(String.format("Train %s travels from station %s to station %s with speed %d", this, currentStation, stationController.getNextStation(currentStation), speed));
    }

    private void arriveAtNextStation() {
        logger.debug(String.format("Train %s arrives at station %s", this, stationController.getNextStation(currentStation)));
        stationController.getNextStation(currentStation).releaseRail(this);
        currentStation = stationController.getNextStation(currentStation);
    }

    private void travel() {
        try { Thread.sleep(currentStation.getDistance()/speed); } catch (InterruptedException e) {}
    }

    private void loadCargo() {
//        logger.debug(String.format("Loading cargo from station %s", currentStation));
        while (cargo.size() <= capacity) {
            Cargo cargoToLoad = currentStation.loadCargoToTrain();
            if(cargoToLoad == null) break;
            loadCargo(cargoToLoad);
        }
    }
    
    private void loadCargo(Cargo cargoToLoad) {
        logger.debug(String.format("Trains %s is loading cargo %s from station %s", this, cargoToLoad, currentStation));
        List<Cargo> cargoList = cargo.get(cargoToLoad.getDestination());
        if (cargoList == null) {
            cargoList = new ArrayList<Cargo>();
            cargo.put(cargoToLoad.getDestination(), cargoList);
        }
        cargoList.add(cargoToLoad);
    }
    
    private void unloadCargo() {
        List<Cargo> cargoList = cargo.get(currentStation.getId());
        if(cargoList != null) {
            for(Cargo cargoToUnload : cargoList) {
                logger.debug(String.format("Trains %s is unloading cargo %s at station %s", this, cargoToUnload, currentStation));
                cargoToUnload.unload();
            }
            cargoList.clear();
        }
    }

    public void doMove() {
        logger.debug(String.format("Train %s %s from %s", this, TrainState.MOVING, currentStation));
        this.state = TrainState.MOVING; 
    }

    public void doWait() {
        logger.debug(String.format("Train %s %s", this, TrainState.WAITING));
        this.state = TrainState.WAITING;
    }

    public void doStop() {
        logger.debug(String.format("Train %s %s", this, TrainState.STOPPED));
        this.state = TrainState.STOPPED;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public Map<Integer, List<Cargo>> getCargo() {
        return cargo;
    }

    // TODO: Does it need synchronization?
    public void terminate() {
        this.terminateRequested = true;
    }

    @Override
    public String toString() {
        return String.format("Train no.[%s]", id);
    }
}
