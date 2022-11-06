package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BalconyAdjustmentServiceImpl implements BalconyAdjustmentService {
    private final Float ONE_HUNDRED_PERCENT = 100F;

    private static final Float OBJECT_HAVE_REFERENCE_NO = 5.3F;

    private static final Float OBJECT_NO_REFERENCE_HAVE = 5.0F;

    @Override
    public void makeAdjustmentsToThePresenceOfABalcony(List<ObjectDto> objectEtalonDtos, RequestDto requestDto) {
        requestDto.getObjectDtos().forEach(objectDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            objectEtalonDtos.forEach(objectEtalonDto -> {
                counter.getAndSet(counter.get() + 1);
                if(objectDto.getBalcony().equals(true) && objectDto.getBalcony().equals(false)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * OBJECT_HAVE_REFERENCE_NO) / ONE_HUNDRED_PERCENT));
                }
                if(objectDto.getBalcony().equals(false) && objectDto.getBalcony().equals(true)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * OBJECT_NO_REFERENCE_HAVE) / ONE_HUNDRED_PERCENT));
                }
                if(objectDto.getBalcony().equals(true) && objectDto.getBalcony().equals(true)){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                if(objectDto.getBalcony().equals(false) && objectDto.getBalcony().equals(false)){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
            });
            objectDto.setMarketPrice((int) (averageMarketPrice.get() / counter.get()));
        });
    }
}
