package com.epam.training.ticketservice.core.price;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PriceComponentServiceImpl implements PriceComponentService{
    private final PriceComponentRepository priceComponentRepository;

    public PriceComponentServiceImpl(PriceComponentRepository priceComponentRepository) {
        this.priceComponentRepository = priceComponentRepository;
    }

    @Override
    public void savePriceComponent(PriceComponentDto priceComponentDto) {
        Objects.requireNonNull(priceComponentDto.getName(), "Name of price component cannot be null during saving!");
        Objects.requireNonNull(priceComponentDto.getPrice(), "Amount of price component cannot be null during saving!");
        PriceComponent priceComponent = new PriceComponent(priceComponentDto.getName(), priceComponentDto.getPrice());
        priceComponentRepository.save(priceComponent);
    }

    @Override
    public void updateBasePrice(Integer price) {

    }
}
