package com.barbershop.barber.dtos;

public record AppointmentResponse(
        Long id,

        String clientName,

        String barber,

        String date,

        String phoneNumber,

        ServiceResponse service) {

}
