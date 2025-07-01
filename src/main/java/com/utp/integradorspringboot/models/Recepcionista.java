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
@Table(name = "recepcionista")
public class Recepcionista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "horario_laboral")
    private String horario_laboral;

    @ManyToOne
    @JoinColumn(name = "id_clinica", nullable = false)
    private Clinica clinica;

    public Recepcionista() {
    }

    public Recepcionista(Long id, Usuario usuario, String horario_laboral, Clinica clinica) {
        this.id = id;
        this.usuario = usuario;
        this.horario_laboral = horario_laboral;
        this.clinica = clinica;
    }

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

    public String getHorario_laboral() {
        return horario_laboral;
    }

    public void setHorario_laboral(String horario_laboral) {
        this.horario_laboral = horario_laboral;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Recepcionista)) {
            return false;
        }
        Recepcionista other = (Recepcionista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recepcionista{" + "id=" + id + ", usuario=" + usuario + ", horario_laboral=" + horario_laboral + ", clinica=" + clinica + '}';
    }
}
