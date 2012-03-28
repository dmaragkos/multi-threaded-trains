package com.dmaragkos.trains.stations;

import com.dmaragkos.trains.cargo.Cargo;
import com.dmaragkos.trains.Train;
import com.dmaragkos.trains.cargo.CargoProducer;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 11:46 AM
 */
public class StationImpl implements Station {

    private static final Logger logger = Logger.getLogger(StationImpl.class);
    
    private int id;
    private Station nextStation;
    private int distance;
    private Lock railLock;
    private BlockingQueue<Cargo> cargoQueue;
    private CargoProducer cargoProducer;
    private volatile boolean terminateProductionRequested;
    
    public StationImpl(int id, Station nextStation, CargoProducer cargoProducer, int distance) {
        this.id = id;
        this.nextStation = nextStation;
        this.distance = distance;
        this.railLock = new ReentrantLock();
        this.cargoQueue = new LinkedBlockingQueue<Cargo>();
        this.cargoProducer = cargoProducer;
        new Thread(this).start();
    }

    /**
     * Occupies the rail that leads to this station
     * @param train the train trying to occupy the rail
     */
    public void occupyRail(Train train) {
        train.doWait();
        railLock.lock();
        train.doMove();
    }

    /**
     * Releases the rail that leads to this station once the train has arrived
     * @param train the traing arriving at this station
     */
    public void releaseRail(Train train) {
        railLock.unlock();
        train.doStop();
    }

    /**
     * Producer adds cargo to the station
     * @param cargo
     */
    public void deliverCargo(Cargo cargo) {
        logger.debug(String.format("Delivering cargo %s to station %s", cargo, this));
        try { cargoQueue.put(cargo); } catch (InterruptedException e) {}
    }

    /**
     * Loads carg to train
     * @return
     */
    public Cargo loadCargoToTrain() {
        Cargo cargo = cargoQueue.poll();
        if (cargo != null) {
            logger.debug(String.format("Loading cargo %s from station %s to train", cargo, this));
            cargo.load();
        }
        return cargo;
    }

    public void run() {
        while (!terminateProductionRequested) {
            deliverCargo(cargoProducer.produceCargo());
            if(cargoProducer.getProdInterval() < 0) return;
            try { Thread.sleep(cargoProducer.getProdInterval()); } catch (InterruptedException e) {}
        }
    }

    public int getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public int getCargoSize() {
        return cargoQueue.size();
    }

    public void terminateProduction() {
        this.terminateProductionRequested = false;
    }

    @Override
    public String toString() {
        return String.format("Station no[%s]", id);
    }
}
