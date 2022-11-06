package ru.sverchkov.hackathon.service;

import org.springframework.stereotype.Service;
import ru.sverchkov.hackathon.model.dto.ObjectDto;
import ru.sverchkov.hackathon.model.dto.RequestDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AreaCorrectionServiceImpl implements AreaCorrectionService {
    //<30
    private static final Float PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_OVER_THIRTY = 6.0F;
    private static final Float PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_OVER_THIRTY = 12.0F;
    private static final Float PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_OVER_THIRTY = 17.0F;
    private static final Float PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_OVER_THIRTY = 22.0F;
    private static final Float PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_OVER_THIRTY = 24.0F;
    //30-50
    private static final Float PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_FROM_THIRTY_TO_FIFTY = 6.0F;
    private static final Float PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_FROM_THIRTY_TO_FIFTY = 7.0F;
    private static final Float PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_FROM_THIRTY_TO_FIFTY = 12.0F;
    private static final Float PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_THIRTY_TO_FIFTY = 17.0F;
    private static final Float PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_THIRTY_TO_FIFTY = 19.0F;
    //50-65
    private static final Float PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE = 14.0F;
    private static final Float PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE = 7.0F;
    private static final Float PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE = 6.0F;
    private static final Float PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE = 11.0F;
    private static final Float PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE = 13.0F;
    //65-90
    private static final Float PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN = 21.0F;
    private static final Float PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN = 14.0F;
    private static final Float PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_SIXTY_FIVE_TO_NINETEEN = 6.0F;
    private static final Float PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN = 6.0F;
    private static final Float PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN = 8.0F;
    //90-120
    private static final Float PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY = 28.0F;
    private static final Float PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY = 21.0F;
    private static final Float PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY = 13.0F;
    private static final Float PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY = 6.0F;
    private static final Float PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY = 3.0F;
    //>120
    private static final Float PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY = 31.0F;
    private static final Float PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY = 24.0F;
    private static final Float PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY = 16.0F;
    private static final Float PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY = 9.0F;
    private static final Float PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY = 3.0F;

    private static final Float ONE_HUNDRED_PERCENT = 100F;

    private static final Integer THIRTY = 30;
    private static final Integer FIFTY = 50;
    private static final Integer SIXTY_FIVE = 65;
    private static final Integer NINETEEN = 90;
    private static final Integer ONE_HUNDRED_AND_TWENTY = 120;


    @Override
    public void makeAdjustmentsToTheArea(List<ObjectDto> objectEtalonDtos, RequestDto requestDto) {
        requestDto.getObjectDtos().forEach(objectDto -> {
            AtomicReference<Float> averageMarketPrice = new AtomicReference<>(0F);
            AtomicReference<Integer> counter = new AtomicReference<>(0);
            objectEtalonDtos.forEach(objectEtalonDto -> {
                counter.getAndSet(counter.get() + 1);
                Integer objectArea = objectDto.getArea();
                Integer referenceArea = objectEtalonDto.getArea();
                boolean objectLessThatThirty = objectArea < THIRTY;
                boolean objectFromThirtyToFifty = objectArea > THIRTY && objectArea < FIFTY;
                boolean objectFromFiftyToSixtyFive = objectArea > FIFTY && objectArea < SIXTY_FIVE;
                boolean objectFromSixtyFiveToNineteen = objectArea > SIXTY_FIVE && objectArea < NINETEEN;
                boolean objectFromNineteenToOneHundredAndTwenty = objectArea > NINETEEN && objectArea < ONE_HUNDRED_AND_TWENTY;
                boolean objectOverOneHundredAndTwenty = objectArea > ONE_HUNDRED_AND_TWENTY;

                boolean lessThatThirty = referenceArea < THIRTY;
                if(lessThatThirty && objectFromThirtyToFifty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_OVER_THIRTY) / ONE_HUNDRED_PERCENT));
                }
                if(lessThatThirty && objectFromFiftyToSixtyFive){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_OVER_THIRTY) / ONE_HUNDRED_PERCENT));
                }
                if(lessThatThirty && objectFromSixtyFiveToNineteen){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_OVER_THIRTY) / ONE_HUNDRED_PERCENT));
                }
                if(lessThatThirty && objectFromNineteenToOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_OVER_THIRTY) / ONE_HUNDRED_PERCENT));
                }
                if(lessThatThirty && objectOverOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_OVER_THIRTY) / ONE_HUNDRED_PERCENT));
                }
                if(lessThatThirty && objectLessThatThirty){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                //30-50
                boolean fromThirtyToFifty = referenceArea > THIRTY && referenceArea < FIFTY;
                if(fromThirtyToFifty && objectLessThatThirty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_FROM_THIRTY_TO_FIFTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromThirtyToFifty && objectFromFiftyToSixtyFive){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_FROM_THIRTY_TO_FIFTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromThirtyToFifty && objectFromSixtyFiveToNineteen){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_FROM_THIRTY_TO_FIFTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromThirtyToFifty && objectFromNineteenToOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_THIRTY_TO_FIFTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromThirtyToFifty && objectOverOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_THIRTY_TO_FIFTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromThirtyToFifty && objectFromThirtyToFifty){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                //50-65
                boolean fromFiftyToSixtyFive = referenceArea > FIFTY && referenceArea < SIXTY_FIVE;
                if(fromFiftyToSixtyFive && objectLessThatThirty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE) / ONE_HUNDRED_PERCENT));
                }
                if(fromFiftyToSixtyFive && objectFromThirtyToFifty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE) / ONE_HUNDRED_PERCENT));
                }
                if(fromFiftyToSixtyFive && objectFromSixtyFiveToNineteen){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE) / ONE_HUNDRED_PERCENT));
                }
                if(fromFiftyToSixtyFive && objectFromNineteenToOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE) / ONE_HUNDRED_PERCENT));
                }
                if(fromFiftyToSixtyFive && objectOverOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_FIFTY_TO_SIXTY_FIVE) / ONE_HUNDRED_PERCENT));
                }
                if(fromFiftyToSixtyFive && objectFromFiftyToSixtyFive){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                //65-90
                boolean fromSixtyFiveToNineteen = referenceArea > SIXTY_FIVE && referenceArea < NINETEEN;
                if(fromSixtyFiveToNineteen && objectLessThatThirty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice()  * PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN) / ONE_HUNDRED_PERCENT));
                }
                if(fromSixtyFiveToNineteen && objectFromThirtyToFifty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice()  * PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN) / ONE_HUNDRED_PERCENT));
                }
                if(fromSixtyFiveToNineteen && objectFromFiftyToSixtyFive ){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice()  * PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_SIXTY_FIVE_TO_NINETEEN) / ONE_HUNDRED_PERCENT));
                }
                if(fromSixtyFiveToNineteen && objectFromNineteenToOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice()  * PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN) / ONE_HUNDRED_PERCENT));
                }
                if(fromSixtyFiveToNineteen && objectOverOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice()  * PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_SIXTY_FIVE_TO_NINETEEN) / ONE_HUNDRED_PERCENT));
                }
                if(fromSixtyFiveToNineteen && objectFromSixtyFiveToNineteen){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                //90-120
                boolean fromNineteenToOneHundredAndTwenty = referenceArea > NINETEEN && referenceArea < ONE_HUNDRED_AND_TWENTY;
                if(fromNineteenToOneHundredAndTwenty && objectLessThatThirty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromNineteenToOneHundredAndTwenty && objectFromThirtyToFifty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromNineteenToOneHundredAndTwenty && objectFromFiftyToSixtyFive){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromNineteenToOneHundredAndTwenty && objectFromSixtyFiveToNineteen){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromNineteenToOneHundredAndTwenty && objectOverOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() - (objectDto.getMarketPrice() * PERCENT_OBJECT_OVER_ONE_HUNDRED_AND_TWENTY_REFERENCE_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(fromNineteenToOneHundredAndTwenty && objectFromNineteenToOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }
                //>120
                boolean overOneHundredAndTwenty = referenceArea > ONE_HUNDRED_AND_TWENTY;
                if(overOneHundredAndTwenty && objectLessThatThirty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_LESS_THEN_THIRTY_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(overOneHundredAndTwenty && objectFromThirtyToFifty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_THIRTY_TO_FIFTY_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(overOneHundredAndTwenty && objectFromFiftyToSixtyFive){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_FIFTY_TO_SIXTY_FIVE_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(overOneHundredAndTwenty && objectFromSixtyFiveToNineteen){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_SIXTY_FIVE_TO_NINETEEN_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(overOneHundredAndTwenty && objectFromNineteenToOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + (objectDto.getMarketPrice() + (objectDto.getMarketPrice() * PERCENT_OBJECT_FROM_NINETEEN_TO_ONE_HUNDRED_AND_TWENTY_REFERENCE_OVER_ONE_HUNDRED_AND_TWENTY) / ONE_HUNDRED_PERCENT));
                }
                if(overOneHundredAndTwenty && objectOverOneHundredAndTwenty){
                    averageMarketPrice.set(averageMarketPrice.get() + objectDto.getMarketPrice());
                }

            });
            objectDto.setMarketPrice((int) (averageMarketPrice.get() / counter.get()));
        });
    }
}
