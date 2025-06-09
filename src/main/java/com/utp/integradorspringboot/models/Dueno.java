package com.utp.integradorspringboot.models;

import javax.persistence.*;

@Entity
public class Dueno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;
    private String dniRuc;
    private String telefono;
    private String correo;
    private String direccion;
    private String relacionConMascota;

    // Getters y setters
}