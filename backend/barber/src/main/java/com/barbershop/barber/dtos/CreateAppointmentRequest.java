package com.barbershop.barber.dtos;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CreateAppointmentRequest {
    @NotBlank(message = "Il nome del cliente non può essere vuoto")
    private String clientName;
    @NotNull
    private Long barberId;
    @NotNull
    private ZonedDateTime date;
    @NotBlank
    private String dateTime;
    @NotBlank
    private String phoneNumber;
    @NotNull @Positive
    private Long serviceId;
}
