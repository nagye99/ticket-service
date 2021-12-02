package com.epam.training.ticketservice.core.price.impl;

import com.epam.training.ticketservice.core.price.PriceComponentService;
import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.price.persistence.entity.PriceComponent;
import com.epam.training.ticketservice.core.price.persistence.repository.PriceComponentRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PriceComponentServiceImpl implements PriceComponentService {
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
        PriceComponent priceComponent = priceComponentRepository.findByName("base price").get();
        priceComponent.setPrice(price);
        priceComponentRepository.save(priceComponent);
    }

    @Override
    public Integer getBasePrice() {
        return priceComponentRepository.findByName("base price").get().getPrice();
    }

    @Override
    public PriceComponentDto getComponentByName(String componentName) {
        Optional<PriceComponentDto> priceComponentDto;
        priceComponentDto = convertPriceEntityToPriceDto(priceComponentRepository.findByName(componentName));
        if(priceComponentDto.isPresent()) {
            return priceComponentDto.get();
        } else {
            throw new IllegalArgumentException("The priceComponent doesn't exist");
        }
    }

    @Override
    public Integer getPriceByComponentName(String componentName) {
        return priceComponentRepository.findByName(componentName).get().getPrice();
    }

    private PriceComponentDto convertPriceEntityToPriceDto(PriceComponent priceComponent) {
        return PriceComponentDto.builder().name(priceComponent.getName()).price(priceComponent.getPrice()).build();
    }

    private  Optional<PriceComponentDto> convertPriceEntityToPriceDto(Optional<PriceComponent> priceComponent) {
        return priceComponent.isEmpty()
                ? Optional.empty()
                : Optional.of(convertPriceEntityToPriceDto(priceComponent.get()));
    }
}
