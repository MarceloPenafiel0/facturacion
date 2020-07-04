package com.example.facturacion.modelo;

import javax.persistence.*;

@Entity
@Table(name ="factura")
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
    @Column (name = "cantidad")
    private float valor;
    @Column (name="cantidad")
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "idfactura")
    private Factura factura;

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
