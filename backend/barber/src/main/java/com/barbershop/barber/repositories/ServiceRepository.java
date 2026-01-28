package com.barbershop.barber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershop.barber.models.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>{

}
