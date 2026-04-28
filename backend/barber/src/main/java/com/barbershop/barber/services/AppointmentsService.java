package com.barbershop.barber.services;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.barbershop.barber.dtos.CreateAppointmentRequest;
import com.barbershop.barber.models.Appointment;
import com.barbershop.barber.models.Barber;
import com.barbershop.barber.repositories.AppointmentsRepository;
import com.barbershop.barber.repositories.BarberRepository;
import com.barbershop.barber.repositories.ServiceRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class AppointmentsService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentsService.class);

    AppointmentsRepository appointmentsRepository;
    BarberRepository barberRepository;
    ServiceRepository serviceRepository;

    @Transactional
    public void createAppointment(@Valid CreateAppointmentRequest request) {
        Barber barber = barberRepository.getReferenceById(request.getBarberId());
        Boolean taken = isAppointmentTaken(request.getBarberId(), request.getDate(), request.getDateTime());
        logger.info("L'appuntamento è stato preso: " + taken);
        logger.info("Verifica appuntamento: BarberId: {}, Date: {}, DateTime: {}", request.getBarberId(),
                request.getDate(), request.getDateTime());

        if (taken)
            throw new RuntimeException("Date, time and barber already taken");
        Appointment appointment = new Appointment();
        appointment.setClientName(request.getClientName());
        appointment.setDateTime(request.getDateTime());
        appointment.setDate(request.getDate());
        appointment.setBarber(barber);
        appointment.setPhoneNumber(request.getPhoneNumber());
        appointment.setService(serviceRepository.getReferenceById(request.getServiceId()));
        appointmentsRepository.save(appointment);
    }

    public Page<Appointment> findAppointments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Ricerca appuntamenti");
        return appointmentsRepository.findAll(pageable);
    }

    public Appointment findAppointmentById(Long id) {
        logger.info("Ricerca appuntamento tramite id");
        return appointmentsRepository.findById(id).orElseThrow();
    }

    public List<Appointment> findAppointmentsByBarberId(Long id) {
        logger.info("Ricerca appuntamento tramite id del barbiere");
        return appointmentsRepository.findByBarberId(id);
    }

    public void deleteAppointment(Long id) {
        logger.info("Eliminazione appuntamento");
        Appointment appointment = appointmentsRepository.findById(id)
                .orElseThrow();

        appointment.setService(null);

        appointmentsRepository.save(appointment);

        appointmentsRepository.delete(appointment);
    }

    public boolean isAppointmentTaken(Long barberId, ZonedDateTime date, String dateTime) {

        LocalTime appointmentTime = LocalTime.parse(dateTime);
        List<Appointment> appointments = appointmentsRepository.findByBarberId(barberId);

        for (Appointment appointment : appointments) {

            if (appointment.getDate().toLocalDate().equals(date.toLocalDate()) &&
                    LocalTime.parse(appointment.getDateTime()).equals(appointmentTime)) {
                return true;
            }

        }
        return false;
    }
}
