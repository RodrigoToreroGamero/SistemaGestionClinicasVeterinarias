/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "Citas")
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
    
    @Column(name = "id_duenio")
    private Long id_duenio;
    
    @Column(name = "id_notificacion")
    private Long id_notificacion;
    
    @Column(name = "id_mascota")
    private Long id_mascota;
    
    @Column(name = "id_veterinario")
    private Long id_veterinario;
    
    @Column(name = "id_historial")
    private Long id_historial;

    
    @ManyToOne
    @JoinColumn(name = "id")
    private Usuario usuario;
    
    public Cita() {
    }

    public Cita(Long id, Date fecha, Time hora, String estado, Long id_duenio, Long id_notificacion, Long id_mascota, Long id_veterinario, Long id_historial) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.id_duenio = id_duenio;
        this.id_notificacion = id_notificacion;
        this.id_mascota = id_mascota;
        this.id_veterinario = id_veterinario;
        this.id_historial = id_historial;
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

    public Long getId_duenio() {
        return id_duenio;
    }

    public void setId_duenio(Long id_duenio) {
        this.id_duenio = id_duenio;
    }

    public Long getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(Long id_notificacion) {
        this.id_notificacion = id_notificacion;
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

    public Long getId_historial() {
        return id_historial;
    }

    public void setId_historial(Long id_historial) {
        this.id_historial = id_historial;
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
        return "Cita{" + "id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", estado=" + estado + ", id_duenio=" + id_duenio + ", id_mascota=" + id_mascota + ", id_historial=" + id_historial +'}';
    }
}
