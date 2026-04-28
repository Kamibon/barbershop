package com.barbershop.barber.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.barbershop.barber.models.Service;
import com.barbershop.barber.repositories.AppointmentsRepository;
import com.barbershop.barber.repositories.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Service
@AllArgsConstructor
@Validated
public class ServiceService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceService.class);

    private ServiceRepository serviceRepository;
    private AppointmentsRepository appointmentRepository;

    public Page<Service> getAllServices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        logger.info("Ricerca servizi");
        return serviceRepository.findAll(pageable);
    }

    public Service getServiceById(Long id) {
        logger.info("Ricerca servizio tramite id");
        Service service = serviceRepository.findById(id).orElseThrow();
        return service;
    }

    public void addService(@Valid Service service) {
        logger.info("Creazione servizio");
        serviceRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
        logger.info("Eliminazione servizio");
        if (appointmentRepository.existsByServiceId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Impossibile eliminare: service in uso");
        }
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        serviceRepository.deleteById(id);
    }
}
