package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "veterinarios")
public class Veterinario implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
     @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "numero_de_colegio_medicoc_veterinario")
    private String numero_de_colegio_medicoc_veterinario;

    @Column(name = "especialidad")
    private String apellidos;
    
    @Column(name = "horario_laboral")
    private String horario_laboral;

    public Veterinario() {
    }
    

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

    public String getNumero_de_colegio_medicoc_veterinario() {
        return numero_de_colegio_medicoc_veterinario;
    }

    public void setNumero_de_colegio_medicoc_veterinario(String numero_de_colegio_medicoc_veterinario) {
        this.numero_de_colegio_medicoc_veterinario = numero_de_colegio_medicoc_veterinario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getHorario_laboral() {
        return horario_laboral;
    }

    public void setHorario_laboral(String horario_laboral) {
        this.horario_laboral = horario_laboral;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Veterinario)) {
            return false;
        }
        Veterinario other = (Veterinario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Veterinario{" + "id=" + id + ", id_usuario=" + id_usuario + ", numero_de_colegio_medicoc_veterinario=" + numero_de_colegio_medicoc_veterinario + ", apellidos=" + apellidos + ", horario_laboral=" + horario_laboral + '}';
    }

    

}
