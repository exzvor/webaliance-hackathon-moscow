package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectResponseDto;
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
    public void makePriceAdjustmentsByFloor(List<ObjectResponseDto> objectResponseDtos, RequestDto requestDto) {
        objectResponseDtos.forEach(objectResponseDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            requestDto.getRequestReferenceObjectDtos().forEach(requestReferenceObjectDto -> {
                counter.getAndSet(counter.get() + 1);
                Integer objectResponseCurrentFloor = objectResponseDto.getCurrentFloor();
                Integer objectResponseFloors = objectResponseDto.getFloors();
                Integer referenceObjectCurrentFloor = requestReferenceObjectDto.getCurrentFloor();
                Integer referenceObjectFloors = requestReferenceObjectDto.getFloors();
                if(objectResponseCurrentFloor == 1 && (referenceObjectCurrentFloor > 1 && referenceObjectCurrentFloor < referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() - (requestReferenceObjectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_FIRST_AND_REFERENCE_MIDDLE) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor == 1 && referenceObjectCurrentFloor.equals(referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() - (requestReferenceObjectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_FIRST_AND_REFERENCE_HIGH) / ONE_HUNDRED_PERCENT));
                }
                if((objectResponseCurrentFloor > 1 && objectResponseCurrentFloor < objectResponseFloors) && referenceObjectCurrentFloor == 1){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + (requestReferenceObjectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_MIDDLE_REFERENCE_FIRST) / ONE_HUNDRED_PERCENT));
                }
                if((objectResponseCurrentFloor > 1 && objectResponseCurrentFloor < objectResponseFloors) && referenceObjectCurrentFloor.equals(referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + (requestReferenceObjectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_MIDDLE_REFERENCE_HIGH) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor.equals(objectResponseFloors) && referenceObjectCurrentFloor == 1){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + (requestReferenceObjectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_HIGH_REFERENCE_FIRST) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor.equals(objectResponseFloors) && (referenceObjectCurrentFloor > 1 && referenceObjectCurrentFloor < referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + (requestReferenceObjectDto.getMarketPrice() * PERCENTAGE_FOR_OBJECT_HIGH_REFERENCE_MIDDLE) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseCurrentFloor == 1 && referenceObjectCurrentFloor == 1){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }
                if((objectResponseCurrentFloor > 1 && objectResponseCurrentFloor < objectResponseFloors) && (referenceObjectCurrentFloor > 1 && referenceObjectCurrentFloor < referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }
                if(objectResponseCurrentFloor.equals(objectResponseFloors) && referenceObjectCurrentFloor.equals(referenceObjectFloors)){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }
            });
            objectResponseDto.setMarketPrice(averageMarketPrice.get() / counter.get());
        });
    }
}
