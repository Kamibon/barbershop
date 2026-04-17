package com.barbershop.barber.dtos;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CreateAppointmentRequest {
    private String clientName;
    private Long barberId; 
    private ZonedDateTime date;
    private String dateTime;
    private String phoneNumber;
    private Long serviceId;
}
