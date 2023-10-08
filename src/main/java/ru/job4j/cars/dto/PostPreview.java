package ru.job4j.cars.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPreview {
    private Integer id;
    private Integer price;
    private Integer fileId;
    private String carName;
    private String bodywork;
    private String engineName;
    private boolean status;
    private String userLogin;
}
