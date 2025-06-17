/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "`Cita`") // usar singular y proteger con backticks si usas MySQL
public class Cita implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora")
    private Time hora;
    
    @Column(name = "estado")
    private String estado;
    
    
    @Column(name = "id_mascota")
    private Long id_mascota;
    
    @Column(name = "id_veterinario")
    private Long id_veterinario;
    
    public Cita() {
    }

    public Cita(Long id, Date fecha, Time hora, String estado, Long id_mascota, Long id_veterinario) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.id_mascota = id_mascota;
        this.id_veterinario = id_veterinario;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(Long id_mascota) {
        this.id_mascota = id_mascota;
    }

    public Long getId_veterinario() {
        return id_veterinario;
    }

    public void setId_veterinario(Long id_veterinario) {
        this.id_veterinario = id_veterinario;
    }
    
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Cita{" + "id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", estado=" + estado + ", id_mascota=" + id_mascota + ", id_veterinario=" + id_veterinario  +'}';
    }
}
