package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarWritableService extends Service<Car> {
    void update(String carId, Car car);
    void deleteCarById(String carId);
    Car create(Car car);
}