package com.barbershop.barber.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershop.barber.models.Appointment;

public interface AppointmentsRepository extends JpaRepository<Appointment, Long>{
     List<Appointment> findByBarberId(Long barberId);

     boolean existsByServiceId(Long id);
}
