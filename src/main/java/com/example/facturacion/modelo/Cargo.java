package com.example.facturacion.modelo;

import com.example.facturacion.exceptions.BadJsonException;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name ="cargo", indexes = { @Index(name = "indiceAtencion", columnList = "idAtencion") })
public class Cargo {
    @Id
    @Column (name="idCargo")
    private String id;
    @Column (name = "idAtencion")
    private int idAtencion;
    @Column (name = "idPaciente")
    private int idPaciente;
    @Column (name = "descripcion")
    private String descripcion;
    @Column (name = "valor")
    private double valor;
    @Column (name="cantidad")
    private int cantidad;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "idFactura")
    @JsonBackReference
    private Factura factura;

    public Cargo(){} // Constructor usado por hibernate.

    public Cargo(Map<String, Object> mappedJson) throws BadJsonException {
        try {
            this.idAtencion = (int) mappedJson.get("idAtencion");
            this.idPaciente = (int) mappedJson.get("idPaciente");
            this.descripcion = mappedJson.get("descripcion").toString();
            this.valor =  (Double)mappedJson.get("valor");
            this.cantidad = (int)mappedJson.get("cantidad");
            StringBuilder builderId= new StringBuilder();
            builderId.append(mappedJson.get("modulo").toString());
            builderId.append(mappedJson.get("tabla").toString());
            int number = (int)mappedJson.get("idExterno")+(666%(this.idAtencion+idPaciente)); //666 -> Numero dado por Lucho.
            builderId.append(number);
            this.id = builderId.toString();
        }catch (Exception e){
            e.printStackTrace();
            throw new BadJsonException();
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cargo )) return false;
        Cargo other = (Cargo) obj;
        return this.id.equals(other.getId())&& this.idAtencion == other.getIdAtencion() && this.idPaciente == other.getIdPaciente()
                && this.descripcion.equals(other.getDescripcion()) && this.valor==other.getValor()
                && this.cantidad == other.getCantidad();
    }

    @Override
    public int hashCode() {
        int base = 7;   //Suma de 1 y 6 (Numeros dados por Seaman).
        return base+this.id.hashCode()+this.idAtencion+this.idPaciente+this.descripcion.hashCode()+(int)this.valor+this.cantidad;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
