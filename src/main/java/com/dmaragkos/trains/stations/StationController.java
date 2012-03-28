package com.dmaragkos.trains.stations;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 1:24 PM
 */
public interface StationController {

    public Station getStart();
    public Station getNextStation(Station station);
    public int getSize();
    
}
