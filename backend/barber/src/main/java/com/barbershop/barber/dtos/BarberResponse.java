package com.barbershop.barber.dtos;

public record BarberResponse(
        Long id,

        String name,
        String surname,
        String phoneNumber) {
}
