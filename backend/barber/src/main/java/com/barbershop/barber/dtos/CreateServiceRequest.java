package com.barbershop.barber.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor  
public class CreateServiceRequest {
    @NotBlank(message = "Il nome del servizio non può essere vuoto")
    private String name;

    @Min(1)
    private int duration;

    @Min(1)
    private BigDecimal price;

    private String imagePath;
}
