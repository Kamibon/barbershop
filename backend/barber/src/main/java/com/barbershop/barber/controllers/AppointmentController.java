package com.barbershop.barber.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barbershop.barber.dtos.AppointmentResponse;
import com.barbershop.barber.dtos.CreateAppointmentRequest;
import com.barbershop.barber.models.Barber;
import com.barbershop.barber.repositories.BarberRepository;
import com.barbershop.barber.services.AppointmentsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor 
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentsService appointmentsService;

    private final BarberRepository barberRepository;

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> findAppointments(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(appointmentsService.findAppointments(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findAppointmentById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(appointmentsService.findAppointmentById(id));
    }

    @GetMapping("/barber/{id}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByBarberId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(appointmentsService.findAppointmentsByBarberId(id));
    }

    @GetMapping("/barber/apps")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByBarberName(Authentication auth) {
        Barber barber = barberRepository.findByName(auth.getName());
        return ResponseEntity.ok(appointmentsService.findAppointmentsByBarberId(barber.getId()));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @RequestBody @Valid CreateAppointmentRequest appointment) {

        AppointmentResponse created = appointmentsService.createAppointment(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable @Positive Long id) {
        appointmentsService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

}
