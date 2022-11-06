package ru.sverchkov.hackathon.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectDto {

    private String location;

    private Integer analog;

    private String age;

    private Boolean balcony;

    private Integer counter;

    private Integer currentFloor;

    private Integer floors;

    private Integer subway;

    private Boolean etalon;

    private Integer marketPrice;

    private Integer price;

    private String metroStation;

    private Integer rooms;

    private String walls;

    private Integer area;

    private String condition;

    private Long userId;

}
