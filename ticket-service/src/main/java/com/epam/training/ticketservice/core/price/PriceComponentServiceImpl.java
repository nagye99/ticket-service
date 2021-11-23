package com.epam.training.ticketservice.core.price;

import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.price.persistence.entity.PriceComponent;
import com.epam.training.ticketservice.core.price.persistence.repository.PriceComponentRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Optional<PriceComponentDto> getComponentByName(String componentName) {
        return convertPriceComponentEntityToPriceComponentDto(priceComponentRepository.findByName(componentName));
    }

    private PriceComponentDto convertPriceComponentEntityToPriceComponentDto(PriceComponent priceComponent) {
        return PriceComponentDto.builder().name(priceComponent.getName()).price(priceComponent.getPrice()).build();
    }

    private  Optional<PriceComponentDto> convertPriceComponentEntityToPriceComponentDto(Optional<PriceComponent> priceComponent) {
        return priceComponent.isEmpty() ? Optional.empty() : Optional.of(convertPriceComponentEntityToPriceComponentDto(priceComponent.get()));
    }
}
