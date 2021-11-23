package com.epam.training.ticketservice.core.price;

public interface PriceComponentService {

    void savePriceComponent(PriceComponentDto priceComponentDto);

    void updateBasePrice(Integer price);
}
