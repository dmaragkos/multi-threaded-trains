package com.dmaragkos.trains.cargo;

import org.apache.log4j.Logger;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 1:56 PM
 */
public class CargoImpl implements Cargo {
    
    private static final Logger logger = Logger.getLogger(CargoImpl.class);

    private int stationId;
    private int units;

    public CargoImpl (int stationId, int units) {
        this.stationId = stationId;
        this.units = units;
    }

    public void load() {
        logger.debug(String.format("Loading cargo %s", this));
        try { Thread.sleep(units * LOAD_UNIT_TIME); } catch (InterruptedException e) {}
        logger.debug(String.format("Loaded cargo %s successfully", this));
    }

    public void unload() {
        logger.debug(String.format("Unloading cargo %s", this));
        try { Thread.sleep(units * UNLOAD_UNIT_TIME); } catch (InterruptedException e) {}
        logger.debug(String.format("Unloaded cargo %s successfully", this));
    }

    public int getDestination() {
        return stationId;
    }

    @Override
    public String toString() {
        return String.format("Cargo for station no.[%d] with %d units", stationId, units);
    }
}
