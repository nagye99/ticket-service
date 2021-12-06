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
        Optional<PriceComponent> priceComponentOpt = priceComponentRepository.findByName("base price");
        if (priceComponentOpt.isPresent()) {
            PriceComponent priceComponent = priceComponentOpt.get();
            priceComponent.setPrice(price);
            priceComponentRepository.save(priceComponent);
        } else {
            throw new NullPointerException("Base price doesn't set");
        }
    }

    @Override
    public Integer getBasePrice() {
        Optional<PriceComponent> priceComponentOptional = priceComponentRepository.findByName("base price");
        if (priceComponentOptional.isPresent()) {
            return priceComponentOptional.get().getPrice();
        } else {
            throw new NullPointerException("Base price doesn't set");
        }
    }

    @Override
    public PriceComponentDto getComponentByName(String componentName) {
        Optional<PriceComponentDto> priceComponentDto;
        priceComponentDto = convertPriceEntityToPriceDto(priceComponentRepository.findByName(componentName));
        if (priceComponentDto.isPresent()) {
            return priceComponentDto.get();
        } else {
            throw new IllegalArgumentException("The priceComponent doesn't exist");
        }
    }

    @Override
    public Integer getPriceByComponentName(String componentName) {
        Optional<PriceComponent> priceComponent = priceComponentRepository.findByName(componentName);
        if(priceComponent.isPresent()){
            return priceComponent.get().getPrice();
        } else {
            throw new NullPointerException("Base price doesn't set");
        }
    }

    private PriceComponentDto convertPriceEntityToPriceDto(PriceComponent priceComponent) {
        return PriceComponentDto.builder().name(priceComponent.getName()).price(priceComponent.getPrice()).build();
    }

    private Optional<PriceComponentDto> convertPriceEntityToPriceDto(Optional<PriceComponent> priceComponent) {
        return priceComponent.isEmpty()
                ? Optional.empty()
                : Optional.of(convertPriceEntityToPriceDto(priceComponent.get()));
    }
}
