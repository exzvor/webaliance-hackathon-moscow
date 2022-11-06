package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;
import ru.sverchkov.hackathon.model.entity.ObjectEntity;
import ru.sverchkov.hackathon.model.entity.RequestEntity;
import ru.sverchkov.hackathon.repository.ObjectRepository;
import ru.sverchkov.hackathon.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketValueCalculationServiceImpl implements MarketValueCalculationService {

    private final RequestRepository requestRepository;
    private final ObjectRepository objectRepository;

    private final BidPriceAdjustmentService bidPriceAdjustmentService;

    private final CostAdjustmentPerFloorService costAdjustmentPerFloorService;

    private final AreaCorrectionService areaCorrectionService;

    private final BalconyAdjustmentService balconyAdjustmentService;

    private final FinishingCorrectionService finishingCorrectionService;


    public MarketValueCalculationServiceImpl(RequestRepository requestRepository, ObjectRepository objectRepository,BidPriceAdjustmentService bidPriceAdjustmentService, CostAdjustmentPerFloorService costAdjustmentPerFloorService, AreaCorrectionService areaCorrectionService, BalconyAdjustmentService balconyAdjustmentService, FinishingCorrectionService finishingCorrectionService) {
        this.requestRepository = requestRepository;
        this.objectRepository = objectRepository;
        this.bidPriceAdjustmentService = bidPriceAdjustmentService;
        this.costAdjustmentPerFloorService = costAdjustmentPerFloorService;
        this.areaCorrectionService = areaCorrectionService;
        this.balconyAdjustmentService = balconyAdjustmentService;
        this.finishingCorrectionService = finishingCorrectionService;
    }

    @Override
    public RequestDto calculate(RequestDto requestDto) {
        requestRepository.save(requestDtoToEntity(requestDto));
        requestDto.getObjectDtos().forEach(objectDto -> objectRepository.save(objectDtoToEntity(objectDto)));
        List<ObjectDto> objectEtalonDtos = new ArrayList<>();
        requestDto.getObjectDtos().forEach(objectDto -> {
            if(objectDto.getEtalon()){
                objectEtalonDtos.add(objectDto);
            }
        });

        requestDto.getObjectDtos().removeAll(objectEtalonDtos);

        bidPriceAdjustmentService.makeABargainingOrientation(objectEtalonDtos,requestDto);

        costAdjustmentPerFloorService.makePriceAdjustmentsByFloor(objectEtalonDtos,requestDto);

        areaCorrectionService.makeAdjustmentsToTheArea(objectEtalonDtos,requestDto);

        balconyAdjustmentService.makeAdjustmentsToThePresenceOfABalcony(objectEtalonDtos,requestDto);

        finishingCorrectionService.makeAdjustmentsToTheFinish(objectEtalonDtos,requestDto);
        requestDto.getObjectDtos().forEach(objectDto -> objectRepository.save(objectDtoToEntity(objectDto)));
        return requestDto;
    }


    private ObjectEntity objectDtoToEntity(ObjectDto objectDto){
        ObjectEntity objectEntity = new ObjectEntity();
        objectEntity.setAge(objectDto.getAge());
        objectEntity.setWalls(objectDto.getWalls());
        objectEntity.setArea(objectDto.getArea());
        objectEntity.setRooms(objectDto.getRooms());
        objectEntity.setBalcony(objectDto.getBalcony());
        objectEntity.setSubway(objectDto.getSubway());
        objectEntity.setCondition(objectDto.getCondition());
        objectEntity.setFloors(objectDto.getFloors());
        objectEntity.setLocation(objectDto.getLocation());
        objectEntity.setCurrentFloor(objectDto.getCurrentFloor());
        objectEntity.setCounter(objectDto.getCounter());
        objectEntity.setMarketPrice(objectDto.getMarketPrice());
        objectEntity.setEtalon(objectDto.getEtalon());
        objectEntity.setPrice(objectDto.getPrice());
        objectEntity.setAnalog(objectDto.getAnalog());
        objectEntity.setUserId(objectDto.getUserId());
        objectEntity.setMetroStation(objectDto.getMetroStation());

return objectEntity;
    }

    private RequestEntity requestDtoToEntity(RequestDto requestDto){
        List<ObjectEntity> objects = new ArrayList<>();
        requestDto.getObjectDtos().forEach(objectDto -> objects.add(objectDtoToEntity(objectDto)));
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setObjects(objects);
       return requestEntity;
    }





}
