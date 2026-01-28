package com.barbershop.barber.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateServiceRequest {
    private String name;

    private int duration;

    private double price;
}
