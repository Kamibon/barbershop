package com.barbershop.barber.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter 
@SuperBuilder 
@Table(name = "services")
public class Service extends BaseEntity{
  
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private BigDecimal price;

    private String imagePath;
}
