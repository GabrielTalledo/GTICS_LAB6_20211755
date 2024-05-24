package com.example.lab6_20211755.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="Usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUsuario",nullable = false)
    private int idUsuario;

    @Column(name="nombre",nullable = false)
    @Size(max=45,message="Debe tener menos de 45 caracteres")
    private String nombre;

    @Column(name="correo")
    @Size(max=45,message="Debe tener menos de 45 caracteres")
    private String correo;

    @Column(name="password",nullable = false)
    @Size(max=100,message="Debe tener menos de 100 caracteres")
    private String password;

    @JoinColumn(name="idRol")
    @ManyToOne
    private Rol rol;

    @Column(name="estado",nullable = false)
    int estado;
}
