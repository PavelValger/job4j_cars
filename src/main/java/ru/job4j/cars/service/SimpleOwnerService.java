package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleOwnerService implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Override
    public Optional<Owner> save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public boolean deleteById(int id) {
        return ownerRepository.deleteById(id);
    }

    @Override
    public void update(Owner owner) {
        ownerRepository.update(owner);
    }

    @Override
    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Collection<Owner> findAll() {
        return ownerRepository.findAll();
    }
}
