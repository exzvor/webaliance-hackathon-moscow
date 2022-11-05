package ru.sverchkov.hackathon.model.dto;

import lombok.Data;

@Data
public class ObjectDto {

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

}
