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
import jakarta.persistence.Table;

/**
 *
 * @author UTP
 */
@Entity
@Table(name = "`Dueno`") // usar singular y proteger con backticks si usas MySQL
public class Dueno {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "id_mascota")
    private Long id_mascota;
    
    public Dueno(){
        
    }
    
    public Dueno(Long id_usuario, Long id_mascota){
        this.id_usuario = id_usuario;
        this.id_mascota = id_mascota;
    }
    
    //Setters y Getters

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(Long id_mascota) {
        this.id_mascota = id_mascota;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dueno)) {
            return false;
        }
        Dueno other = (Dueno) object;
        if ((this.getId_usuario() == null && other.id_usuario != null) || (this.getId_usuario() != null && !this.id_usuario.equals(other.id_usuario))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Dueno{" + "id_usuario=" + id_usuario + ", id_mascota=" + id_mascota + '}';
    }
    
}
