package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FinishingCorrectionServiceImpl implements FinishingCorrectionService {
    private static final String NO = "Без отделки";
    private static final String ECONOMY = "Эконом";
    private static final String UPGRADE = "Улучшенный";

    private static final Float OBJECT_NO_REFERENCE_ECONOMY = 13400F;
    private static final Float OBJECT_NO_REFERENCE_UPGRADE = 20100F;

    private static final Float OBJECT_ECONOMY_REFERENCE_NO = 13400F;
    private static final Float OBJECT_ECONOMY_REFERENCE_UPGRADE = 6700F;

    private static final Float OBJECT_UPGRADE_REFERENCE_NO = 20100F;
    private static final Float OBJECT_UPGRADE_REFERENCE_ECONOMY = 6700F;

    @Override
    public void makeAdjustmentsToTheFinish(List<ObjectDto> objectEtalonDtos, RequestDto requestDto) {
        requestDto.getObjectDtos().forEach(objectDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            objectEtalonDtos.forEach(objectEtalonDto -> {
                counter.getAndSet(counter.get() + 1);
                String conditionObject = objectDto.getCondition();
                String conditionReference = objectEtalonDto.getCondition();
                if(conditionObject.equals(NO) && conditionReference.equals(ECONOMY)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - OBJECT_NO_REFERENCE_ECONOMY));
                }
                if(conditionObject.equals(NO) && conditionReference.equals(UPGRADE)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - OBJECT_NO_REFERENCE_UPGRADE));
                }
                if(conditionObject.equals(NO) && conditionReference.equals(NO)){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }

                if(conditionObject.equals(ECONOMY) && conditionReference.equals(NO)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + OBJECT_ECONOMY_REFERENCE_NO));
                }
                if(conditionObject.equals(ECONOMY) && conditionReference.equals(UPGRADE)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - OBJECT_ECONOMY_REFERENCE_UPGRADE));
                }
                if(conditionObject.equals(ECONOMY) && conditionReference.equals(ECONOMY)){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }

                if(conditionObject.equals(UPGRADE) && conditionReference.equals(NO)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + OBJECT_UPGRADE_REFERENCE_NO));
                }
                if(conditionObject.equals(UPGRADE) && conditionReference.equals(ECONOMY)){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + OBJECT_UPGRADE_REFERENCE_ECONOMY));
                }
                if(conditionObject.equals(UPGRADE) && conditionReference.equals(UPGRADE)){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
            });
            objectDto.setMarketPrice((int) (averageMarketPrice.get() / counter.get()));
        });
    }
}
