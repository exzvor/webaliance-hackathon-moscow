package ru.sverchkov.hackathon.service;

import ru.sverchkov.hackathon.model.dto.ObjectResponseDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;

public interface MarketValueCalculationService {
    List<ObjectResponseDto> calculate(RequestDto requestDto);
}
