package com.epam.training.ticketservice.core.price.impl;

import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.price.persistence.entity.PriceComponent;
import com.epam.training.ticketservice.core.price.persistence.repository.PriceComponentRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PriceComponentServiceImplTest {
    private static final PriceComponent PRICE_COMPONENT_ENTITY = new PriceComponent("a", 1000);
    private static final PriceComponentDto PRICE_COMPONENT_DTO = PriceComponentDto.builder()
            .name("a")
            .price(1000)
            .build();

    private static final PriceComponent BASE_PRICE_ENTITY = new PriceComponent("base price", 1500);
    private static final PriceComponentDto BASE_PRICE_DTO = PriceComponentDto.builder()
            .name("base price")
            .price(1500)
            .build();

    private final PriceComponentRepository priceComponentRepository = mock(PriceComponentRepository.class);
    private final PriceComponentServiceImpl underTest = new PriceComponentServiceImpl(priceComponentRepository);

    @Test
    void testSavePriceComponentShouldThrowNullPointerExceptionWhenNameIsNull(){
        //Given
        PriceComponentDto priceComponentDto = PriceComponentDto.builder()
                .name(null)
                .price(1000)
                .build();

        //When - Then
        assertThrows(NullPointerException.class, () -> underTest.savePriceComponent(priceComponentDto));
    }

    @Test
    void testSavePriceComponentShouldThrowNullPointerExceptionWhenPriceIsNull(){
        //Given
        PriceComponentDto priceComponentDto = PriceComponentDto.builder()
                .name("a")
                .price(null)
                .build();

        //When - Then
        assertThrows(NullPointerException.class, () -> underTest.savePriceComponent(priceComponentDto));
    }

    @Test
    void testSavePriceComponentShouldCallPriceComponentRepository(){
        //Given
        when(priceComponentRepository.save(PRICE_COMPONENT_ENTITY)).thenReturn(PRICE_COMPONENT_ENTITY);

        //When
        underTest.savePriceComponent(PRICE_COMPONENT_DTO);

        //Then
        verify(priceComponentRepository).save(PRICE_COMPONENT_ENTITY);
    }

    @Test
    void testUpdateBasePriceShouldCallPriceComponentRepository(){
        //Given
        when(priceComponentRepository.findByName("base price")).thenReturn(java.util.Optional.of(BASE_PRICE_ENTITY));
        when(priceComponentRepository.save(new PriceComponent("base price", 1000))).thenReturn(new PriceComponent("base price", 1000));

        //When
        underTest.updateBasePrice(1000);

        //Then
        verify(priceComponentRepository).save(new PriceComponent("base price", 1000));
    }

    @Test
    void testUpdateBasePriceShouldThrowNullPointerExceptionWhenBasePriceNotExisist(){
        //Given
        when(priceComponentRepository.findByName("base price")).thenReturn(Optional.empty());
        when(priceComponentRepository.save(new PriceComponent("base price", 1000))).thenReturn(new PriceComponent("base price", 1000));

        //When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateBasePrice(1000));
    }

    @Test
    void testGetBasePriceShouldReturnPrice(){
        //Given
        when(priceComponentRepository.findByName("base price")).thenReturn(java.util.Optional.of(BASE_PRICE_ENTITY));
        Integer expected = 1500;

        //When
        Integer actual = underTest.getBasePrice();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetBasePriceShouldThrowNullPointerExceptionWhenBasePriceNotExisist(){
        //Given
        when(priceComponentRepository.findByName("base price")).thenReturn(Optional.empty());

        //When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateBasePrice(1000));
    }
}