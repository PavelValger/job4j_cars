package ru.job4j.cars.mappers;

import org.mapstruct.Mapper;
import ru.job4j.cars.dto.OwnerCreating;
import ru.job4j.cars.model.History;
import ru.job4j.cars.model.Owner;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    default History getHistoryFromOwnerCreating(OwnerCreating ownerCreating) {
        History history = new History();
        history.setStartAt(LocalDateTime
                .ofInstant(ownerCreating.getStartAt().toInstant(), TimeZone.getDefault().toZoneId()));
        history.setEndAt(LocalDateTime
                .ofInstant(ownerCreating.getEndAt().toInstant(), TimeZone.getDefault().toZoneId()));
        return history;
    }

    default Owner getOwnerFromOwnerCreating(OwnerCreating ownerCreating) {
        Owner owner = new Owner();
        owner.setName(ownerCreating.getName());
        owner.setHistory(getHistoryFromOwnerCreating(ownerCreating));
        return owner;
    }
}
