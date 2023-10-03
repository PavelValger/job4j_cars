package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPreview {
    private Integer id;
    private Integer price;
    private Collection<Integer> files;
    private String carName;
    private String engineName;
    private boolean status;
}
