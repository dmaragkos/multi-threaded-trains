package com.dmaragkos.trains.cargo;

import com.dmaragkos.trains.stations.Station;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 11:57 AM
 */
public interface Cargo {

    public static final long LOAD_UNIT_TIME = 20;
    public static final long UNLOAD_UNIT_TIME = 20;
    public void unload();
    public void load();
    public int getDestination();
}
