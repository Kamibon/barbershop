package com.barbershop.barber.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appointments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    String clientName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id", nullable = false)
    Barber barber;
    @NotNull
    ZonedDateTime date;
    @NotNull
    String dateTime;
    @NotNull
    String phoneNumber;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "service_id")
    Service service;
}
