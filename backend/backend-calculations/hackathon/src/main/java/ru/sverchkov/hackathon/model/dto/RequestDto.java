package ru.sverchkov.hackathon.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestDto {

    private List<ObjectDto> objectDtos;
}
