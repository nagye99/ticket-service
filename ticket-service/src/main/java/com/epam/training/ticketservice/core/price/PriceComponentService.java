package com.epam.training.ticketservice.core.price;

import com.epam.training.ticketservice.core.price.model.PriceComponentDto;

import java.util.Optional;

public interface PriceComponentService {

    void savePriceComponent(PriceComponentDto priceComponentDto);

    void updateBasePrice(Integer price);

    Integer getBasePrice();

    Optional<PriceComponentDto> getComponentByName(String componentName);

    Integer getPriceByComponentName(String componentName);
}
