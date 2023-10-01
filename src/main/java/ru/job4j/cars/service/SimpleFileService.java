package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.FileRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;

    @Override
    public File save(FileDto fileDto) {
        return null;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public Collection<File> findAll() {
        return fileRepository.findAll();
    }
}
