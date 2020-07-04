package com.example.facturacion.modelo;

import javax.persistence.*;

@Entity
@Table(name ="factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="idFactura")
    private int id;
    @Column (name="fechaEmision")
    private String fechaEmision;
    @Column (name = "numeroFactura")
    private String numeroFactura;
    @Column (name = "estado")
    private String estado;
    @ManyToOne
    @JoinColumn(name = "personaId")
    private Persona persona;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
