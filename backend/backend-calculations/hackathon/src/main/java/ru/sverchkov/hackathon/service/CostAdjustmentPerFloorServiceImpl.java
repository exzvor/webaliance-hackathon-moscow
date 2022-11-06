package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CostAdjustmentPerFloorServiceImpl implements CostAdjustmentPerFloorService {
    private static final Float PERCENTAGE_FOR_OBJECT_FIRST_AND_REFERENCE_MIDDLE = 7.0F;
    private static final Float PERCENTAGE_FOR_OBJECT_FIRST_AND_REFERENCE_HIGH = 3.1F;

    private static final Float PERCENTAGE_FOR_OBJECT_MIDDLE_REFERENCE_FIRST = 7.5F;
    private static final Float PERCENTAGE_FOR_OBJECT_MIDDLE_REFERENCE_HIGH = 4.2F;

    private static final Float PERCENTAGE_FOR_OBJECT_HIGH_REFERENCE_FIRST = 3.2F;
    private static final Float PERCENTAGE_FOR_OBJECT_HIGH_REFERENCE_MIDDLE = 4.0F;

    private static final Float ONE_HUNDRED_PERCENT = 100F;
    @Override
    public void makePriceAdjustmentsByFloor(List<ObjectDto> objectEtalonDtos, RequestDto requestDto) {
        requestDto.getObjectDtos().forEach(objectDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            objectEtalonDtos.forEach(objectEtalonDto -> {
                counter.getAndSet(counter.get() + 1);
                Integer objectResponseCurrentFloor = objectDto.getCurrentFloor();
                Integer objectResponseFloors = objectDto.getFloors();
                Integer referenceObjectCurrentFloor = objectEtalonDto.getCurrentFloor();
                Integer referenceObjectFloors = objectEtalonDto.getFloors();
                if(objectResponseCurrentFloor == 1 && (referenceObjectCurrentFloor > 1 && referenceObjectCurrentFloor < referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_FIRST_AND_REFERENCE_MIDDLE) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor == 1 && referenceObjectCurrentFloor.equals(referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_FIRST_AND_REFERENCE_HIGH) / ONE_HUNDRED_PERCENT));
                }
                if((objectResponseCurrentFloor > 1 && objectResponseCurrentFloor < objectResponseFloors) && referenceObjectCurrentFloor == 1){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_MIDDLE_REFERENCE_FIRST) / ONE_HUNDRED_PERCENT));
                }
                if((objectResponseCurrentFloor > 1 && objectResponseCurrentFloor < objectResponseFloors) && referenceObjectCurrentFloor.equals(referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_MIDDLE_REFERENCE_HIGH) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor.equals(objectResponseFloors) && referenceObjectCurrentFloor == 1){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_HIGH_REFERENCE_FIRST) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor.equals(objectResponseFloors) && (referenceObjectCurrentFloor > 1 && referenceObjectCurrentFloor < referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_HIGH_REFERENCE_MIDDLE) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor == 1 && referenceObjectCurrentFloor == 1){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                if((objectResponseCurrentFloor > 1 && objectResponseCurrentFloor < objectResponseFloors) && (referenceObjectCurrentFloor > 1 && referenceObjectCurrentFloor < referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                if(objectResponseCurrentFloor.equals(objectResponseFloors) && referenceObjectCurrentFloor.equals(referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
            });
            objectDto.setMarketPrice((int) (averageMarketPrice.get() / counter.get()));
        });
    }
}
