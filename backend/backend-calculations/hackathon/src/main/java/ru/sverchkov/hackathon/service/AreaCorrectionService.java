package ru.sverchkov.hackathon.service;

import ru.sverchkov.hackathon.model.dto.ObjectResponseDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;

public interface AreaCorrectionService {
    void makeAdjustmentsToTheArea(List<ObjectResponseDto> objectResponseDtos , RequestDto requestDto);
}
