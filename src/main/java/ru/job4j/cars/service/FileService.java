package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;

import java.util.Collection;
import java.util.Optional;

public interface FileService {

    File getFileFromFileDto(FileDto fileDto);

    Optional<File> save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    boolean deleteById(int id);

    Collection<File> findAll(Collection<Integer> files);
}
