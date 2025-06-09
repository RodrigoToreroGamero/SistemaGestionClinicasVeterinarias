package com.utp.integradorspringboot.models;

import javax.persistence.*;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dueno dueno;

    @ManyToOne
    private Mascota mascota;

    // Getters y setters
}