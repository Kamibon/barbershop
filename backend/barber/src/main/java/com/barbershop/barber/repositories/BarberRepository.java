package com.barbershop.barber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershop.barber.models.Barber;

public interface BarberRepository extends JpaRepository<Barber, Long>{
      public Barber findByName(String name);
}
