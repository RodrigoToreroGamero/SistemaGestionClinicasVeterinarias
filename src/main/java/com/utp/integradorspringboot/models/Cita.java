/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import java.io.Serializable;
import jakarta.persistence.JoinColumn;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.cglib.core.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;
    
    @Column(name = "estado")
    private String estado;
    

    @ManyToOne
    @JoinColumn(name = "id_dueno", nullable = false)
    private Dueno dueno;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    


    public Cita() {
    }

    public Cita(Long id, LocalDate fecha, LocalTime hora, String estado, Dueno dueno, Mascota mascota) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.dueno = dueno;
        this.mascota = mascota;
    }
    

    
    // Getters y Setters
    public void setEstado(String estado){
        this.estado = estado;
    }
    public String getEstado(){
        return estado;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
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
        return "Cita{" + "id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", estado=" + estado + ", dueno=" + dueno + ", mascota=" + mascota + '}';
    }
    
    
}
