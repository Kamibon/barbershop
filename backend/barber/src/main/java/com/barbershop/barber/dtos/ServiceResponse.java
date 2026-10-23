package com.barbershop.barber.dtos;

import java.math.BigDecimal;

public record ServiceResponse(
        Long id,
        
        String name,

        int duration,

        BigDecimal price,

        String imagePath) {

}
