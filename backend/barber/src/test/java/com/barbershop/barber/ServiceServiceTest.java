package com.barbershop.barber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.barbershop.barber.models.Service;
import com.barbershop.barber.repositories.AppointmentsRepository;
import com.barbershop.barber.repositories.ServiceRepository;
import com.barbershop.barber.services.ServiceService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing appointments service")
public class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private AppointmentsRepository appointmentRepository;

    @InjectMocks
    private ServiceService serviceService;

    private Service service;

    @BeforeEach
    private void setup() {
        service = Service.builder().id(1L).name("Capelli").price(10).build();
    }

    @Test
    void addService_shouldSaveService() {
        serviceService.addService(service);

        verify(serviceRepository).save(service);
    }

    @Test
    void getServiceById_shouldReturnService_whenExists() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        Service result = serviceService.getServiceById(1L);

        assertEquals(service, result);
    }

    @Test
    void getServiceById_shouldThrow_whenNotFound() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> serviceService.getServiceById(1L));
    }

    @Test
    void deleteService_shouldThrowException_whenServiceIsUsed() {
        when(appointmentRepository.existsByServiceId(1L)).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> serviceService.deleteService(1L));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(serviceRepository, never()).deleteById(any());
    }

    @Test
    void deleteService_shouldDelete_whenServiceNotUsed() {
        when(appointmentRepository.existsByServiceId(1L)).thenReturn(false);
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        serviceService.deleteService(1L);

        verify(serviceRepository).deleteById(1L);
    }

    @Test
    void deleteService_shouldThrow_whenServiceNotFound() {
        when(appointmentRepository.existsByServiceId(1L)).thenReturn(false);
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> serviceService.deleteService(1L));

        verify(serviceRepository, never()).deleteById(any());
    }

}
