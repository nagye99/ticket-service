package com.epam.training.ticketservice.core.attachPrice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachPriceDto {
    private final Integer price;
}
