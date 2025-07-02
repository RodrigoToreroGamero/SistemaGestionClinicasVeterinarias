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
@Table(name = "detalle_cita")
public class Detalle_cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @Column(name = "estado")
    private String estado = "pendiente";

    @Column(name = "motivo_consulta")
    private String motivo_consulta;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "tratamiento")
    private String tratamiento;

    @Column(name = "receta")
    private String receta;

    @Column(name = "costo", columnDefinition = "DECIMAL(10,2)")
    private Double costo;

    @Column(name = "metodo_pago")
    private String metodo_pago;

    @Column(name = "duracion_aproximada")
    private Integer duracion_aproximada;

    public Detalle_cita() {}

    public Detalle_cita(Long id, Cita cita, String estado, String motivo_consulta, String diagnostico, String tratamiento, String receta, Double costo, String metodo_pago, Integer duracion_aproximada) {
        this.id = id;
        this.cita = cita;
        this.estado = estado;
        this.motivo_consulta = motivo_consulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.receta = receta;
        this.costo = costo;
        this.metodo_pago = metodo_pago;
        this.duracion_aproximada = duracion_aproximada;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMotivo_consulta() { return motivo_consulta; }
    public void setMotivo_consulta(String motivo_consulta) { this.motivo_consulta = motivo_consulta; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    public String getReceta() { return receta; }
    public void setReceta(String receta) { this.receta = receta; }
    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }
    public String getMetodo_pago() { return metodo_pago; }
    public void setMetodo_pago(String metodo_pago) { this.metodo_pago = metodo_pago; }
    public Integer getDuracion_aproximada() { return duracion_aproximada; }
    public void setDuracion_aproximada(Integer duracion_aproximada) { this.duracion_aproximada = duracion_aproximada; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Detalle_cita)) {
            return false;
        }
        Detalle_cita other = (Detalle_cita) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Detalle_cita{" + "id=" + id + ", cita=" + cita + ", estado=" + estado + ", motivo_consulta=" + motivo_consulta + ", diagnostico=" + diagnostico + ", tratamiento=" + tratamiento + ", receta=" + receta + ", costo=" + costo + ", metodo_pago=" + metodo_pago + ", duracion_aproximada=" + duracion_aproximada + '}';
    }
}
