package com.utp.integradorspringboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "veterinario")
public class Veterinario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "numero_colegio_medico")
    private String numero_colegio_medico;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "horario_laboral")
    private String horario_laboral;

    // Constructors
    public Veterinario() {
    }

    public Veterinario(String numero_colegio_medico, String especialidad, String horario_laboral, Usuario usuario) {
        this.numero_colegio_medico = numero_colegio_medico;
        this.especialidad = especialidad;
        this.horario_laboral = horario_laboral;
        this.usuario = usuario;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNumero_colegio_medico() {
        return numero_colegio_medico;
    }

    public void setNumero_colegio_medico(String numero_colegio_medico) {
        this.numero_colegio_medico = numero_colegio_medico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorario_laboral() {
        return horario_laboral;
    }

    public void setHorario_laboral(String horario_laboral) {
        this.horario_laboral = horario_laboral;
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
        return "Veterinario{" + "id=" + id + ", usuario=" + usuario + ", numero_colegio_medico=" + numero_colegio_medico + ", especialidad=" + especialidad + ", horario_laboral=" + horario_laboral + '}';
    }
}
