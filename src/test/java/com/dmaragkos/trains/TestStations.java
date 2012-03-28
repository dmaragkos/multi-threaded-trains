package com.dmaragkos.trains;

import com.dmaragkos.trains.cargo.CargoProducer;
import com.dmaragkos.trains.stations.*;
import junit.framework.TestCase;

/**
 * User: dmaragkos
 * Date: 2/19/12
 * Time: 1:12 PM
 */
public class TestStations extends TestCase{


    public void testTravelToNextStation() {
        int NO_OF_STATIONS = 2;
        StationController CONTROLLER = new LinkedStationController(NO_OF_STATIONS);
        Train train = new Train(0, 100, 2, CONTROLLER);
        Station currentStation = CONTROLLER.getStart();
        for(int i = 0;i < NO_OF_STATIONS;i++) {
            train.travelToNextStation();
            assertEquals(train.getCurrentStation(), currentStation.getNextStation());
            currentStation = currentStation.getNextStation();
        }
    }

    public void testCargoProduction() {
        int NO_OF_STATIONS = 2;
        StationController CONTROLLER = new LinkedStationController(NO_OF_STATIONS);
        Station station = CONTROLLER.getStart();
        Thread t = new Thread(station);
        t.start();
        try { Thread.sleep(10000); } catch (InterruptedException e) {}
        station.terminateProduction();
    }

    public void testRails() throws InterruptedException {
        int NO_OF_STATIONS = 2;
        StationController CONTROLLER = new LinkedStationController(NO_OF_STATIONS);
        Train train = new Train(0, 100, 2, CONTROLLER);
        Train train2 = new Train(1, 10, 2, CONTROLLER);
        Thread t = new Thread(train);
        Thread t2 = new Thread(train2);
        t.start();
        t2.start();
        Thread.sleep(20000);
        train.terminate();
        train2.terminate();
    }

    
    public void testCargoDelivered() throws InterruptedException {
        CargoProducer producer = new CargoProducer(2, 0, -1);//produce only once
        Station start = new StationImpl(0, null, producer, 100);
        producer = new CargoProducer(2, 1, -1);
        Station end = new StationImpl(1, null, producer, 100);
        start.setNextStation(end);
        end.setNextStation(start);
        StationController controller = new LinkedStationController(start);
        Thread.sleep(2000);
        Train train = new Train(0, 100, 1, controller);
        train.travelToNextStation();
        assertEquals(train.getCargo().get(1).size(), 1);
        train.travelToNextStation();
        assertEquals(train.getCargo().get(1).size(), 0);
    }
    
    public void testStationsCircle() {
        StationController controller = new LinkedStationController(8);
        Station current = controller.getStart();
        for (int i = 0; i < 8; i++) {
            assertEquals(current.getId(), i);
            current = current.getNextStation();
        }
        assertEquals(current, controller.getStart());
    }

    public void testMaximumCapacity() throws InterruptedException {
        int NO_OF_STATIONS = 2;
        StationController CONTROLLER = new LinkedStationController(NO_OF_STATIONS);
        Train train = new Train(0, 100, 2, CONTROLLER);
        Thread t = new Thread(train);
        t.start();
        Thread.sleep(10000);
    }
    
}
