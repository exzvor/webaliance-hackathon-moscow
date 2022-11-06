package ru.sverchkov.hackathon.service;

import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;

public interface BidPriceAdjustmentService {
    void makeABargainingOrientation(List<ObjectDto> objectEtalonDtos, RequestDto requestDto);
}
