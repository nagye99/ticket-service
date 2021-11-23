package com.epam.training.ticketservice.core.price;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceComponentDto {
    private final String name;
    private final Integer price;
}
