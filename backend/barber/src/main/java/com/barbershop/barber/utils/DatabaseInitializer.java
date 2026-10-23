package com.barbershop.barber.utils;

import java.math.BigDecimal;

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

        serviceRepository.save(new Service("Colore", 30, new BigDecimal(10), null));
        serviceRepository.save(new Service("Shampoo", 20, new BigDecimal(15), null));
        serviceRepository.save(new Service("Taglio", 60, new BigDecimal(45), null));

        if (barberRepository.count() == 0) {
            barberRepository.save(
                    new Barber("Mario", "Rossi", "3102345678"));
            barberRepository.save(
                    new Barber("Luigi", "Verdi", "377766888"));
            barberRepository.save(
                    new Barber("Paolo", "Bianchi", "322444555"));
        }
    }
}
