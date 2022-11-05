package ru.sverchkov.hackathon.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectResponseDto {

    private Long id;

    private String location;

    private Integer rooms;

    private String age;

    private Integer floors;

    private String walls;

    private Integer currentFloor;

    private Integer area;

    private Boolean balcony;

    private Integer subway;

    private String condition;

    private Float marketPrice;
}
