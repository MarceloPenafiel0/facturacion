package com.example.facturacion.modelo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="idFactura")
    private int id;

    @Column (name = "idPaciente")
    private int idPaciente;
    @Column (name="fechaEmision")
    private String fechaEmision;
    @Column (name = "numeroFactura")
    private String numeroFactura;
    @Column (name = "estado")
    private String estado;
    @Column (name="idAtencion")
    private int idAtencion;
    @ManyToOne
    @JoinColumn(name = "personaId")
    private Persona persona;
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cargo> cargos;

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
    public void addCargo(Cargo cargo){ //esto es lo que sospecho no va a valer
        cargos.add(cargo);
    }


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

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(int idAtencion) {
        this.idAtencion = idAtencion;
    }
}
