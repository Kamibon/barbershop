package com.barbershop.barber.dtos;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.Getter;

@Getter
public class CreateAppointmentRequest {
    private String clientName;
    private Long barberId; 
    private ZonedDateTime date;
    private String dateTime;
    private String phoneNumber;
    private Long serviceId;
}
