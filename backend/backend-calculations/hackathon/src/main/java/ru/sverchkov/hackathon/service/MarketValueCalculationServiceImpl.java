package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.ObjectResponseDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;
import ru.sverchkov.hackathon.model.dto.RequestReferenceObjectDto;
import ru.sverchkov.hackathon.model.entity.ObjectEntity;
import ru.sverchkov.hackathon.model.entity.ObjectResponseEntity;
import ru.sverchkov.hackathon.model.entity.ReferenceObjectEntity;
import ru.sverchkov.hackathon.model.entity.RequestEntity;
import ru.sverchkov.hackathon.repository.ObjectRepository;
import ru.sverchkov.hackathon.repository.ObjectResponseRepository;
import ru.sverchkov.hackathon.repository.ReferenceObjectRepository;
import ru.sverchkov.hackathon.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketValueCalculationServiceImpl implements MarketValueCalculationService {

    private final RequestRepository requestRepository;
    private final ObjectRepository objectRepository;
    private final ObjectResponseRepository objectResponseRepository;
    private final ReferenceObjectRepository referenceObjectRepository;

    private final BidPriceAdjustmentService bidPriceAdjustmentService;

    private final CostAdjustmentPerFloorService costAdjustmentPerFloorService;

    private final AreaCorrectionService areaCorrectionService;

    private final BalconyAdjustmentService balconyAdjustmentService;

    private final FinishingCorrectionService finishingCorrectionService;


    public MarketValueCalculationServiceImpl(RequestRepository requestRepository, ObjectRepository objectRepository, ObjectResponseRepository objectResponseRepository, ReferenceObjectRepository referenceObjectRepository, BidPriceAdjustmentService bidPriceAdjustmentService, CostAdjustmentPerFloorService costAdjustmentPerFloorService, AreaCorrectionService areaCorrectionService, BalconyAdjustmentService balconyAdjustmentService, FinishingCorrectionService finishingCorrectionService) {
        this.requestRepository = requestRepository;
        this.objectRepository = objectRepository;
        this.objectResponseRepository = objectResponseRepository;
        this.referenceObjectRepository = referenceObjectRepository;
        this.bidPriceAdjustmentService = bidPriceAdjustmentService;
        this.costAdjustmentPerFloorService = costAdjustmentPerFloorService;
        this.areaCorrectionService = areaCorrectionService;
        this.balconyAdjustmentService = balconyAdjustmentService;
        this.finishingCorrectionService = finishingCorrectionService;
    }

    @Override
    public List<ObjectResponseDto> calculate(RequestDto requestDto) {
        List<ObjectResponseDto> objectResponseDtos = new ArrayList<>();
        requestRepository.save(requestDtoToEntity(requestDto));
        requestDto.getObjectDtos().forEach(objectDto -> {
            objectRepository.save(objectDtoToEntity(objectDto));
        });
        requestDto.getRequestReferenceObjectDtos().forEach(requestReferenceObjectDto -> {
            referenceObjectRepository.save(referenceObjectDtoToEntity(requestReferenceObjectDto));
        });


        requestDto.getObjectDtos()
                .forEach((objectDto -> {
                    objectResponseDtos.add(ObjectResponseDto.builder()
                            .id(objectDto.getId())
                            .age(objectDto.getAge())
                            .area(objectDto.getArea())
                            .balcony(objectDto.getBalcony())
                            .condition(objectDto.getCondition())
                            .currentFloor(objectDto.getCurrentFloor())
                            .floors(objectDto.getFloors())
                            .location(objectDto.getLocation())
                            .walls(objectDto.getWalls())
                            .subway(objectDto.getSubway())
                            .rooms(objectDto.getRooms())
                            .build());
                }));

        bidPriceAdjustmentService.makeABargainingOrientation(objectResponseDtos, requestDto);

        costAdjustmentPerFloorService.makePriceAdjustmentsByFloor(objectResponseDtos,requestDto);

        areaCorrectionService.makeAdjustmentsToTheArea(objectResponseDtos,requestDto);

        balconyAdjustmentService.makeAdjustmentsToThePresenceOfABalcony(objectResponseDtos,requestDto);

        finishingCorrectionService.makeAdjustmentsToTheFinish(objectResponseDtos,requestDto);
        objectResponseDtos.forEach(objectResponseDto -> {
            objectResponseRepository.save(objectResponseDtoToEntity(objectResponseDto));
        });
        return objectResponseDtos;
    }

    private RequestEntity requestDtoToEntity(RequestDto requestDto){
        List<ObjectEntity> objectEntities = new ArrayList<>();
        for(ObjectDto objectDto : requestDto.getObjectDtos()){
            objectEntities.add(objectDtoToEntity(objectDto));
        }
        List<ReferenceObjectEntity> referenceObjectEntities = new ArrayList<>();
        for(RequestReferenceObjectDto requestReferenceObjectDto : requestDto.getRequestReferenceObjectDtos()){
            referenceObjectEntities.add(referenceObjectDtoToEntity(requestReferenceObjectDto));
        }
        return RequestEntity.builder()
                .objectEntities(objectEntities)
                .referenceObjectEntities(referenceObjectEntities)
                .build();
    }

    private ObjectEntity objectDtoToEntity(ObjectDto objectDto){
        return ObjectEntity.builder()
                .id(objectDto.getId())
                .age(objectDto.getAge())
                .balcony(objectDto.getBalcony())
                .area(objectDto.getArea())
                .rooms(objectDto.getRooms())
                .walls(objectDto.getWalls())
                .subway(objectDto.getSubway())
                .currentFloor(objectDto.getCurrentFloor())
                .location(objectDto.getLocation())
                .floors(objectDto.getFloors())
                .condition(objectDto.getCondition())
                .build();
    }

    private ReferenceObjectEntity referenceObjectDtoToEntity(RequestReferenceObjectDto requestReferenceObjectDto){
        return ReferenceObjectEntity.builder()
                .id(requestReferenceObjectDto.getId())
                .age(requestReferenceObjectDto.getAge())
                .area(requestReferenceObjectDto.getArea())
                .balcony(requestReferenceObjectDto.getBalcony())
                .condition(requestReferenceObjectDto.getCondition())
                .rooms(requestReferenceObjectDto.getRooms())
                .subway(requestReferenceObjectDto.getSubway())
                .walls(requestReferenceObjectDto.getWalls())
                .currentFloor(requestReferenceObjectDto.getCurrentFloor())
                .marketPrice(requestReferenceObjectDto.getMarketPrice())
                .location(requestReferenceObjectDto.getLocation())
                .floors(requestReferenceObjectDto.getFloors())
                .build();
    }

    private ObjectResponseEntity objectResponseDtoToEntity(ObjectResponseDto objectResponseDto){
        return ObjectResponseEntity.builder()
                .id(objectResponseDto.getId())
                .marketPrice(objectResponseDto.getMarketPrice())
                .condition(objectResponseDto.getCondition())
                .rooms(objectResponseDto.getRooms())
                .floors(objectResponseDto.getFloors())
                .walls(objectResponseDto.getWalls())
                .balcony(objectResponseDto.getBalcony())
                .area(objectResponseDto.getArea())
                .age(objectResponseDto.getAge())
                .currentFloor(objectResponseDto.getCurrentFloor())
                .location(objectResponseDto.getLocation())
                .subway(objectResponseDto.getSubway())
                .build();
    }

}
