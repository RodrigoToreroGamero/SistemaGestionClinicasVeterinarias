/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author UTP
 */
public class empleado_clinica {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    //propiedades de la clase
    @Column(name = "id")
    private Long id;
    
    @Column(name = "id_usuario")
    private Long id_usuario;
    
    @Column(name = "id_clinica")
    private Long id_clinica;
    
    public empleado_clinica(){
    
    }
    
    public empleado_clinica(Long id, Long id_usuario, Long id_clinica){
        this.id = id;
        this.id_clinica = id_clinica;
        this.id_usuario = id_usuario;
    }
    
    
    //Get y Setters
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

    public Long getId_clinica() {
        return id_clinica;
    }

    public void setId_clinica(Long id_clinica) {
        this.id_clinica = id_clinica;
    }
    
    
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof empleado_clinica)) {
            return false;
        }
        empleado_clinica other = (empleado_clinica) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Cita{" + "id=" + getId() + ", ID de la Clinica=" + id_clinica + ", ID del Usuario=" + id_usuario +'}';
    }
}
