package com.example.facturacion.modelo;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="id")
    private int id;
    @Column (name="nombre")
    private String nombre;
    @Column (name="cedula")
    private String cedula;
    @Column (name="telefono")
    private String telefono;
    @Column (name = "correo")
    private String correo;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas;//no deja arraylist no se porque

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
