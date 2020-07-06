package com.example.facturacion.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column (name = "total")
    private  float total;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "personaId")
    private Persona persona;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cargo> cargos;

    @Override
    public int hashCode() {
        int base=73;    //Numero de Sheldon
        return base+this.id+this.idPaciente+this.fechaEmision.hashCode()+this.numeroFactura.hashCode()+this.estado.hashCode()
                +this.idAtencion+(int)this.total;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Factura )) return false;
        Factura other = (Factura) obj;
        return this.id == other.getId() && this.idAtencion == other.getIdAtencion() && this.idPaciente == other.getIdPaciente()
                && this.numeroFactura.equals(other.getNumeroFactura()) && this.estado.equals(other.getEstado())
                && this.total ==  other.getTotal() && this.fechaEmision.equals(other.getFechaEmision());
    }

    public void addCargo(Cargo cargo){
        this.cargos.add(cargo);
        cargo.setFactura(this);
    }

    public Factura (){
        this.cargos = new ArrayList<>();
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
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
