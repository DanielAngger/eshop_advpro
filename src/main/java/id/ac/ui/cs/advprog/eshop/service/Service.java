package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();
    T findById(String id);
    void update(String carId, T generic);
    void deleteCarById(String Id);
    T create(T generic);
}