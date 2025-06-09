package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "clinicas_veterinarias")
public class ClinicaVeterinaria implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_clinica")
    private String nombre_clinica;

    @Column(name = "ruc")
    private String ruc;
    
    @Column(name = "direccion_sede")
    private String direccion_sede;
    
    @Column(name = "link_web")
    private String link_web;
    
    @Column(name = "telefono_sede")
    private String telefono_sede;
    
    private Veterinario veterinario_responsable;

    public ClinicaVeterinaria() {
    }

    public ClinicaVeterinaria(String nombre_clinica, String ruc, String direccion_sede, String link_web, String telefono_sede, Veterinario veterinario_responsable) {        
        this.nombre_clinica = nombre_clinica;
        this.ruc = ruc;
        this.direccion_sede = direccion_sede;
        this.link_web = link_web;
        this.telefono_sede = telefono_sede;
        this.veterinario_responsable = veterinario_responsable;
    }
            
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_clinica() {
        return nombre_clinica;
    }

    public void setNombre_clinica(String nombre_clinica) {
        this.nombre_clinica = nombre_clinica;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion_sede() {
        return direccion_sede;
    }

    public void setDireccion_sede(String direccion_sede) {
        this.direccion_sede = direccion_sede;
    }

    public String getLink_web() {
        return link_web;
    }

    public void setLink_web(String link_web) {
        this.link_web = link_web;
    }

    public String getTelefono_sede() {
        return telefono_sede;
    }

    public void setTelefono_sede(String telefono_sede) {
        this.telefono_sede = telefono_sede;
    }

    public Veterinario getVeterinario_responsable() {
        return veterinario_responsable;
    }

    public void setVeterinario_responsable(Veterinario veterinario_responsable) {
        this.veterinario_responsable = veterinario_responsable;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClinicaVeterinaria)) {
            return false;
        }
        ClinicaVeterinaria other = (ClinicaVeterinaria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClinicaVeterinaria{" + "id=" + id + ", nombre_clinica=" + nombre_clinica + ", ruc=" + ruc + ", direccion_sede=" + direccion_sede + ", link_web=" + link_web + ", telefono_sede=" + telefono_sede + ", veterinario_responsable=" + veterinario_responsable + '}';
    }

   

}
