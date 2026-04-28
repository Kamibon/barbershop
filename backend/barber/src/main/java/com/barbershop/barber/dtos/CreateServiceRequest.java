package com.barbershop.barber.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateServiceRequest {
    @NotBlank(message = "Il nome del servizio non può essere vuoto")
    private String name;

    @Min(1)
    private int duration;

    @Min(1)
    private double price;
}
