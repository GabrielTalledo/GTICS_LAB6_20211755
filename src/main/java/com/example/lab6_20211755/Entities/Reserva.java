package com.example.lab6_20211755.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idReserva",nullable = false)
    private int idReserva;

    @JoinColumn(name = "idCliente")
    @ManyToOne
    private Usuario cliente;

    @JoinColumn(name = "idMesa")
    @ManyToOne
    private Mesa mesa;

    @Column(name = "fechaInicio")
    @FutureOrPresent(message = "La fecha debe ser futura o actual")
    private LocalDate fechaInicio;

    @Column(name = "fechaFin")
    @FutureOrPresent(message = "La fecha debe ser futura o actual")
    private LocalDate fechaFin;
}
