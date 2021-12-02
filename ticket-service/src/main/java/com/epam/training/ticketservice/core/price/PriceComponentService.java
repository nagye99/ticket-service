package com.epam.training.ticketservice.core.price;

import com.epam.training.ticketservice.core.price.model.PriceComponentDto;

public interface PriceComponentService {

    void savePriceComponent(PriceComponentDto priceComponentDto);

    void updateBasePrice(Integer price);

    Integer getBasePrice();

    PriceComponentDto getComponentByName(String componentName);

    Integer getPriceByComponentName(String componentName);
}
