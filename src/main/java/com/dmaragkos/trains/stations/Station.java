package com.dmaragkos.trains.stations;

import com.dmaragkos.trains.cargo.Cargo;
import com.dmaragkos.trains.Train;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 1:28 PM
 */
public interface Station extends Runnable {

    public int getId();
    public void occupyRail(Train train);
    public void releaseRail(Train train);
    public int getDistance();
    public void deliverCargo(Cargo cargo);
    public Cargo loadCargoToTrain();
    public Station getNextStation();
    public void setNextStation(Station nextStation);
    public int getCargoSize();
    public void terminateProduction();

}
