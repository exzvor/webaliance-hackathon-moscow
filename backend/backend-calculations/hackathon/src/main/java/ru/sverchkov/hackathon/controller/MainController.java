package ru.sverchkov.hackathon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;
import ru.sverchkov.hackathon.service.MarketValueCalculationService;

import java.util.List;

@RestController
public class MainController {

    private static final String GET_WITH_CORRECTIONS = "/api/v1/correct";

    private final MarketValueCalculationService marketValueCalculationService;

    public MainController(MarketValueCalculationService marketValueCalculationService) {
        this.marketValueCalculationService = marketValueCalculationService;
    }

    @GetMapping(GET_WITH_CORRECTIONS)
    public ResponseEntity<List<ObjectDto>> getWithCorrections(@RequestBody RequestDto requestDto){
        return ResponseEntity.ok(marketValueCalculationService.calculate(requestDto).getObjectDtos());
    }
}
