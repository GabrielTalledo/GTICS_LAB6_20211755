package com.example.lab6_20211755.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="Rol")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRol",nullable = false)
    private int idRol;

    @Column(name="nombre",nullable = false)
    @Size(max=45,message="Debe tener menos de 45 caracteres")
    private String nombre;

}
