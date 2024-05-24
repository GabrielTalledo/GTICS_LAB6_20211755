package com.example.lab6_20211755.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idMesa",nullable = false)
    private int idMesa;

    @Column(name="nombre",nullable = false)
    @Size(max=45,message="Debe tener menos de 45 caracteres")
    private String nombre;

    @Column(name="capacidad",nullable = false)
    @Digits(integer = 2,fraction = 0, message = "Debe ser un número entero")
    @Positive(message = "El número debe ser mayor que cero")
    @Max(value = 20, message = "Las mesas solo pueden soportar un cantidad de 20 personas")
    private int capacidad;

    @Column(name="ubicacion",nullable = false)
    @Size(max=45,message="Debe tener menos de 45 caracteres")
    private String ubicacion;

    @Column(name="disponibles",nullable = false)
    @Digits(integer = 3,fraction = 0, message = "Debe ser un número entero")
    @Positive(message = "El número debe ser mayor que cero")
    @Max(value = 100, message = "La cantidad de mesas solo puede ser máximo 100")
    private int disponibles;
}
