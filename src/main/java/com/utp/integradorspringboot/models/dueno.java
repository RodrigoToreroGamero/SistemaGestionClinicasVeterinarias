/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 *
 * @author UTP
 */
public class dueno {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    //Propiedades de la clase
    @Column(name = "id")
    private Long id;
    
    @Column(name = "id_usuario")
    private Long id_usuario;
    
    public dueno(){
        
    }
    
    public dueno(Long id, Long id_usuario){
        this.id = id;
        this.id_usuario = id_usuario;
    }
    
    //Setters y Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof dueno)) {
            return false;
        }
        dueno other = (dueno) object;
        if ((this.getId() == null && other.id != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Cita{" + "id=" + getId() + ", ID Usuario=" + id_usuario + '}';
    }
    
}
