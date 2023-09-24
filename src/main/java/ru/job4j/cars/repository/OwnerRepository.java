package ru.job4j.cars.repository;

import ru.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Optional;

public interface OwnerRepository {

    Optional<Owner> save(Owner owner);

    boolean deleteById(int id);

    void update(Owner owner);

    Optional<Owner> findById(int id);

    Collection<Owner> findAll();
}
