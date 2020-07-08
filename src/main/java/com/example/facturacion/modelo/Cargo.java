package com.example.facturacion.modelo;

import com.fasterxml.jackson.annotation.*;
import org.glassfish.jersey.internal.util.collection.Views;

import javax.persistence.*;
import javax.swing.text.View;

@Entity
@Table(name ="cargo", indexes = { @Index(name = "indiceAtencion", columnList = "idAtencion") })
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="idCargo")
    private int id;
    @Column (name = "idAtencion")
    private int idAtencion;
    @Column (name = "idPaciente")
    private int idPaciente;
    @Column (name = "descripcion")
    private String descripcion;
    @Column (name = "valor")
    private float valor;
    @Column (name="cantidad")
    private int cantidad;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "idFactura")
    @JsonBackReference
    private Factura factura;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cargo )) return false;
        Cargo other = (Cargo) obj;
        return this.id == other.getId() && this.idAtencion == other.getIdAtencion() && this.idPaciente == other.getIdPaciente()
                && this.descripcion.equals(other.getDescripcion()) && this.valor==other.getValor()
                && this.cantidad ==  other.getCantidad();
    }

    @Override
    public int hashCode() {
        int base = 7;   //Suma de 1 y 6 (Numeros dados por Seaman).
        return base+this.id+this.idAtencion+this.idPaciente+this.descripcion.hashCode()+(int)this.valor+this.cantidad;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(int idAtencion) {
        this.idAtencion = idAtencion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
