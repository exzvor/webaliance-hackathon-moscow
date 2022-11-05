package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectResponseDto;
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
    public void makeAdjustmentsToTheFinish(List<ObjectResponseDto> objectResponseDtos, RequestDto requestDto) {
        objectResponseDtos.forEach(objectResponseDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            requestDto.getRequestReferenceObjectDtos().forEach(requestReferenceObjectDto -> {
                counter.getAndSet(counter.get() + 1);
                String conditionObject = objectResponseDto.getCondition();
                String conditionReference = requestReferenceObjectDto.getCondition();
                if(conditionObject.equals(NO) && conditionReference.equals(ECONOMY)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() - OBJECT_NO_REFERENCE_ECONOMY));
                }
                if(conditionObject.equals(NO) && conditionReference.equals(UPGRADE)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() - OBJECT_NO_REFERENCE_UPGRADE));
                }
                if(conditionObject.equals(NO) && conditionReference.equals(NO)){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }

                if(conditionObject.equals(ECONOMY) && conditionReference.equals(NO)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + OBJECT_ECONOMY_REFERENCE_NO));
                }
                if(conditionObject.equals(ECONOMY) && conditionReference.equals(UPGRADE)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() - OBJECT_ECONOMY_REFERENCE_UPGRADE));
                }
                if(conditionObject.equals(ECONOMY) && conditionReference.equals(ECONOMY)){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }

                if(conditionObject.equals(UPGRADE) && conditionReference.equals(NO)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + OBJECT_UPGRADE_REFERENCE_NO));
                }
                if(conditionObject.equals(UPGRADE) && conditionReference.equals(ECONOMY)){
                    averageMarketPrice.set(averageMarketPrice.get() + (requestReferenceObjectDto.getMarketPrice() + OBJECT_UPGRADE_REFERENCE_ECONOMY));
                }
                if(conditionObject.equals(UPGRADE) && conditionReference.equals(UPGRADE)){
                    averageMarketPrice.set(averageMarketPrice.get() + requestReferenceObjectDto.getMarketPrice());
                }

            });
            objectResponseDto.setMarketPrice(averageMarketPrice.get() / counter.get());
        });
    }
}
