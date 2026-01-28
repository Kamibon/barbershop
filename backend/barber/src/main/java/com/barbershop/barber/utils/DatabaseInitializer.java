package com.barbershop.barber.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.barbershop.barber.models.Barber;
import com.barbershop.barber.models.Service;
import com.barbershop.barber.repositories.BarberRepository;
import com.barbershop.barber.repositories.ServiceRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final BarberRepository barberRepository;
    private final ServiceRepository serviceRepository;

    public DatabaseInitializer(BarberRepository barberRepository, ServiceRepository serviceRepository) {
        this.barberRepository = barberRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(String... args) {

        serviceRepository.save(new Service(null, "Taglio capelli", 30, 25.0, null));
        serviceRepository.save(new Service(null, "Barba", 20, 15.0, null));
        serviceRepository.save(new Service(null, "Colore", 60, 45.0, null));

        if (barberRepository.count() == 0) {
            barberRepository.save(
                    new Barber(null, "Mario", "Rossi", "3331234567"));
            barberRepository.save(
                    new Barber(null, "Luigi", "Verdi", "3339876543"));
            barberRepository.save(
                    new Barber(null, "Paolo", "Bianchi", "3205554444"));
        }
    }
}
