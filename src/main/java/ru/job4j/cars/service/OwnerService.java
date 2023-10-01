package ru.job4j.cars.service;

import ru.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Optional;

public interface OwnerService {

    Optional<Owner> save(Owner owner);

    boolean deleteById(int id);

    void update(Owner owner);

    Optional<Owner> findById(int id);

    Collection<Owner> findAll();
}
