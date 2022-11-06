package ru.sverchkov.hackathon.service;

import ru.sverchkov.hackathon.model.dto.RequestDto;


public interface MarketValueCalculationService {
    RequestDto calculate(RequestDto requestDto);
}
