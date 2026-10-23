package com.barbershop.barber.mappers;

import org.springframework.stereotype.Component;

import com.barbershop.barber.dtos.AppointmentResponse;
import com.barbershop.barber.models.Appointment;

@Component
public class AppointmentMapper {
    public static AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse(appointment.getId(), appointment.getClientName(),
                appointment.getBarber().getName(),
                appointment.getDate().toString().split("T")[0] + "T" + appointment.getDateTime(), appointment.getPhoneNumber(),
                ServiceMapper.toResponse(appointment.getService()));

        return response;
    }
}
