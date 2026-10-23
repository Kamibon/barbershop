package com.barbershop.barber.services;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.barbershop.barber.dtos.AppointmentResponse;
import com.barbershop.barber.dtos.CreateAppointmentRequest;
import com.barbershop.barber.mappers.AppointmentMapper;
import com.barbershop.barber.models.Appointment;
import com.barbershop.barber.models.Barber;
import com.barbershop.barber.models.Service;
import com.barbershop.barber.repositories.AppointmentsRepository;
import com.barbershop.barber.repositories.BarberRepository;
import com.barbershop.barber.repositories.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Service
@AllArgsConstructor 
@Validated
public class AppointmentsService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentsService.class);

    private final AppointmentsRepository appointmentsRepository;
    private final BarberRepository barberRepository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public AppointmentResponse createAppointment(@Valid CreateAppointmentRequest request) {
        Barber barber = barberRepository.findById(request.getBarberId())
                .orElseThrow(() -> new EntityNotFoundException("Barbiere non trovato"));
        Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Servizio non trovato"));
        Boolean taken = isAppointmentTaken(request.getBarberId(), request.getDate(), request.getDateTime());
        logger.info("L'appuntamento è stato preso: " + taken);
        logger.info("Verifica appuntamento: BarberId: {}, Date: {}, DateTime: {}", request.getBarberId(),
                request.getDate(), request.getDateTime());

        if (taken)
            throw new RuntimeException("Data, ora e barbiere già occupati");
        Appointment appointment = new Appointment();
        appointment.setClientName(request.getClientName());
        appointment.setDateTime(request.getDateTime());
        appointment.setDate(request.getDate());
        appointment.setBarber(barber);
        appointment.setPhoneNumber(request.getPhoneNumber());

        appointment.setService(service);
        Appointment savedAppointment = appointmentsRepository.save(appointment);
        return AppointmentMapper.toResponse(savedAppointment);
    }

    public Page<AppointmentResponse> findAppointments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Ricerca appuntamenti");

        return appointmentsRepository.findAll(pageable).map(AppointmentMapper::toResponse);

    }

    public AppointmentResponse findAppointmentById(Long id) {
        logger.info("Ricerca appuntamento tramite id");
        return AppointmentMapper.toResponse(appointmentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Questo appuntamento non esiste")));
    }

    public List<AppointmentResponse> findAppointmentsByBarberId(Long id) {
        logger.info("Ricerca appuntamento tramite id del barbiere");
        barberRepository.findById(id).orElseThrow();
        return appointmentsRepository.findByBarberId(id).stream().map(AppointmentMapper::toResponse).toList();
    }

    @Transactional
    public void deleteAppointment(Long id) {
        logger.info("Eliminazione appuntamento");
        Appointment appointment = appointmentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Questo appuntamento non esiste"));

        appointment.setService(null);

        appointmentsRepository.save(appointment);

        appointmentsRepository.delete(appointment);
    }

    public boolean isAppointmentTaken(Long barberId, ZonedDateTime date, String dateTime) {

        return appointmentsRepository.existsByBarberIdAndDateAndDateTime(barberId, date,
                dateTime);

    }
}
