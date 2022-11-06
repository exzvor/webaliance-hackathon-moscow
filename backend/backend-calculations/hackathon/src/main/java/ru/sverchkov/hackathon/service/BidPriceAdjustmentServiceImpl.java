package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BidPriceAdjustmentServiceImpl implements BidPriceAdjustmentService {

    private static final Float PERCENTAGE_TRADING_ADJUSTMENT = 4.5F;

    private static final Float ONE_HUNDRED_PERCENT = 100F;

    @Override
    public void makeABargainingOrientation(List<ObjectDto> objectEtalonDtos,RequestDto requestDto) {

        requestDto.getObjectDtos().forEach(objectDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            objectEtalonDtos.forEach(objectEtalonDto -> {
                counter.getAndSet(counter.get() + 1);
                averageMarketPrice.set(averageMarketPrice.get() + objectEtalonDto.getMarketPrice());
            });
            float marketPrice  = averageMarketPrice.get() / counter.get();
            objectDto.setMarketPrice((int) (marketPrice - ((marketPrice * PERCENTAGE_TRADING_ADJUSTMENT) / ONE_HUNDRED_PERCENT)));
        });
    }
}
