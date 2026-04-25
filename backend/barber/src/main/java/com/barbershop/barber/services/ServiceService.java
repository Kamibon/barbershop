package com.barbershop.barber.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.barbershop.barber.models.Service;
import com.barbershop.barber.repositories.AppointmentsRepository;
import com.barbershop.barber.repositories.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceService.class);

    private ServiceRepository serviceRepository;
    private AppointmentsRepository appointmentRepository;

    public List<Service> getAllServices() {
        logger.info("Ricerca servizi");
        return serviceRepository.findAll();
    }

    public Service getServiceById(Long id) {
        logger.info("Ricerca servizio tramite id");
        Service service = serviceRepository.findById(id).orElseThrow();
        return service;
    }

    public void addService(Service service) {
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
