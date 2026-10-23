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

import com.barbershop.barber.dtos.CreateServiceRequest;
import com.barbershop.barber.dtos.ServiceResponse;
import com.barbershop.barber.mappers.ServiceMapper;
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

    private final ServiceRepository serviceRepository;
    private final AppointmentsRepository appointmentRepository;

    public Page<ServiceResponse> getAllServices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        logger.info("Ricerca servizi");
        return serviceRepository.findAll(pageable).map(ServiceMapper::toResponse);
    }

    public ServiceResponse getServiceById(Long id) {
        logger.info("Ricerca servizio con id: {}", id);

        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servizio non trovato"));

        return ServiceMapper.toResponse(service);
    }

    public ServiceResponse addService(@Valid CreateServiceRequest serviceRequest) {
        logger.info("Creazione servizio");
        Service service = Service.builder().name(serviceRequest.getName()).price(serviceRequest.getPrice())
                .duration(serviceRequest.getDuration()).imagePath(serviceRequest.getImagePath()).build();
        return ServiceMapper.toResponse(serviceRepository.save(service));
    }

    @Transactional
    public void deleteService(Long id) {
        logger.info("Eliminazione servizio con id: {}", id);

        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servizio non trovato"));

        if (appointmentRepository.existsByServiceId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Impossibile eliminare: il servizio è utilizzato da appuntamenti");
        }

        serviceRepository.delete(service);
    }
}
