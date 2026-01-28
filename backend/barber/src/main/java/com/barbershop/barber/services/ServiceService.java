package com.barbershop.barber.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.barbershop.barber.models.Service;
import com.barbershop.barber.repositories.AppointmentsRepository;
import com.barbershop.barber.repositories.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AppointmentsRepository appointmentRepository;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service getServiceById(Long id) {
        Service service = serviceRepository.findById(id).orElseThrow();
        return service;
    }

    public void addService(Service service) {
        serviceRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
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
