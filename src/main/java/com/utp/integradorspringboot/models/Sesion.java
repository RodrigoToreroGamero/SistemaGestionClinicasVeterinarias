package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sesiones")
public class Sesion implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
     @Column(name = "correo")
    private String correo;

    @Column(name = "contraseña")
    private String contraseña;
    

    public Sesion() {
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }
       
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sesion)) {
            return false;
        }
        Sesion other = (Sesion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sesion{" + "id=" + id + ", correo=" + correo + ", contrase\u00f1a=" + contraseña + '}';
    }

   
    

}
