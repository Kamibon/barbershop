package com.barbershop.barber.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbershop.barber.dtos.CreateAppointmentRequest;
import com.barbershop.barber.models.Appointment;
import com.barbershop.barber.models.Barber;
import com.barbershop.barber.repositories.BarberRepository;
import com.barbershop.barber.services.AppointmentsService;
import com.barbershop.barber.services.ServiceService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    AppointmentsService appointmentsService;

    BarberRepository barberRepository;

    @GetMapping
    public List<Appointment> findAppointments() {
        return appointmentsService.findAppointments();
    }

    @GetMapping("/{id}")
    public Appointment findAppointmentById(@PathVariable Long id) {
        return appointmentsService.findAppointmentById(id);
    }

    @GetMapping("/barber/{id}")
    public List<Appointment> getAppointmentsByBarberId(@PathVariable Long id) {
        return appointmentsService.findAppointmentsByBarberId(id);
    }

    @GetMapping("/barber/apps")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Appointment> getAppointmentsByBarberName(Authentication auth) {
        Barber barber = barberRepository.findByName(auth.getName());
        return appointmentsService.findAppointmentsByBarberId(barber.getId());
    }

    @PostMapping
    public ResponseEntity<String> createAppointment(@RequestBody CreateAppointmentRequest appointment) {
        try {
             appointmentsService.createAppointment(appointment);
             return ResponseEntity.status(HttpStatus.CREATED).body("Appointment created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
       
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentsService.deleteAppointment(id);
    }

}
