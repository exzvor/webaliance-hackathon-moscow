package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectResponseDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BalconyAdjustmentServiceImpl implements BalconyAdjustmentService {
    private final Float ONE_HUNDRED_PERCENT = 100F;

    private static final Float OBJECT_HAVE_REFERENCE_NO = 5.3F;

    private static final Float OBJECT_NO_REFERENCE_HAVE = 5.0F;

    @Override
    public void makeAdjustmentsToThePresenceOfABalcony(List<ObjectResponseDto> objectResponseDtos, RequestDto requestDto) {
        objectResponseDtos.forEach(objectResponseDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            requestDto.getRequestReferenceObjectDtos().forEach(requestReferenceObjectDto -> {
                counter.getAndSet(counter.get() + 1);
                if(objectResponseDto.getBalcony().equals(true) && requestReferenceObjectDto.getBalcony().equals(false)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + (requestReferenceObjectDto.getMarketPrice() * OBJECT_HAVE_REFERENCE_NO) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseDto.getBalcony().equals(false) && requestReferenceObjectDto.getBalcony().equals(true)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() - (requestReferenceObjectDto.getMarketPrice() * OBJECT_NO_REFERENCE_HAVE) / ONE_HUNDRED_PERCENT));
                }
                if(objectResponseDto.getBalcony().equals(true) && requestReferenceObjectDto.getBalcony().equals(true)){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }
                if(objectResponseDto.getBalcony().equals(false) && requestReferenceObjectDto.getBalcony().equals(false)){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }
            });
            objectResponseDto.setMarketPrice(averageMarketPrice.get() / counter.get());
        });
    }
}
