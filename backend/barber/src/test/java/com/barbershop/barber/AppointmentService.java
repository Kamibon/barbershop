package com.barbershop.barber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.barbershop.barber.dtos.CreateAppointmentRequest;
import com.barbershop.barber.models.Appointment;
import com.barbershop.barber.models.Barber;
import com.barbershop.barber.models.Service;
import com.barbershop.barber.repositories.AppointmentsRepository;
import com.barbershop.barber.repositories.BarberRepository;
import com.barbershop.barber.repositories.ServiceRepository;
import com.barbershop.barber.services.AppointmentsService;
@ExtendWith(MockitoExtension.class)
@DisplayName("Testing appointments service")
@EnableScheduling
class AppointmentsServiceTest {

    @Mock
    private AppointmentsRepository appointmentsRepository;

    @Mock
    private BarberRepository barberRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private AppointmentsService appointmentsService;

    private Barber barber;
    private Service service;
    private List<Appointment> appointments;

    @BeforeEach
    void setUp() {
        barber = Barber.builder().name("Giorgio").id(1L).build();

        service = new Service();
        service.setId(1L);
    }

    @Test
    void createAppointment_shouldSaveAppointment_whenSlotIsFree() {

        CreateAppointmentRequest request = new CreateAppointmentRequest("Mario", 1L, ZonedDateTime.now(), "00:00", "333333333", 1L);
      
        when(barberRepository.getReferenceById(1L)).thenReturn(barber);
        when(serviceRepository.getReferenceById(1L)).thenReturn(service);
        when(appointmentsRepository.findByBarberId(1L)).thenReturn(List.of());

        appointmentsService.createAppointment(request);

        verify(appointmentsRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void createAppointment_shouldThrowException_whenSlotIsTaken() {

        CreateAppointmentRequest request = new CreateAppointmentRequest("Mario", 1L, ZonedDateTime.now(), "10:00", "333333333", 1L);

        Appointment existing = new Appointment();
        existing.setBarber(barber);
        existing.setDate(request.getDate());
        existing.setDateTime("10:00");

        when(barberRepository.getReferenceById(1L)).thenReturn(barber);
        when(appointmentsRepository.findByBarberId(1L)).thenReturn(List.of(existing));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> appointmentsService.createAppointment(request));

        assertEquals("Date, time and barber already taken", exception.getMessage());
        verify(appointmentsRepository, never()).save(any());
    }

    @Test
    void findAppointmentById_shouldReturnAppointment() {

        Appointment appointment = new Appointment();
        appointment.setId(1L);

        when(appointmentsRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentsService.findAppointmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteAppointment_shouldDeleteAppointment() {

        Appointment appointment = new Appointment();
        appointment.setId(1L);

        when(appointmentsRepository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentsService.deleteAppointment(1L);

        verify(appointmentsRepository).save(appointment);
        verify(appointmentsRepository).delete(appointment);
    }

    @Test
    void isAppointmentTaken_shouldReturnTrue_whenMatchExists() {

        ZonedDateTime date = ZonedDateTime.now();

        Appointment appointment = new Appointment();
        appointment.setBarber(barber);
        appointment.setDate(date);
        appointment.setDateTime("10:00");
        appointment.setClientName("Mario");
        appointment.setService(service);

        when(appointmentsRepository.findByBarberId(1L)).thenReturn(List.of(appointment));

        boolean result = appointmentsService.isAppointmentTaken(1L, date, "10:00");

        assertTrue(result);
    }

    @Test
    void isAppointmentTaken_shouldReturnFalse_whenNoMatch() {

        when(appointmentsRepository.findByBarberId(1L)).thenReturn(List.of());

        boolean result = appointmentsService.isAppointmentTaken(
                1L,
                ZonedDateTime.now(),
                "10:00");

        assertFalse(result);
    }
}