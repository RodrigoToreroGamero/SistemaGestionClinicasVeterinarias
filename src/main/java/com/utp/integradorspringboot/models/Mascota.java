package com.utp.integradorspringboot.models;

import javax.persistence.*;

@Entity
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private String edadAproximada;
    private String microchip;

    // Getters y setters
}