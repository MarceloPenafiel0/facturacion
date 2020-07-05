package com.example.facturacion.controlador;


import com.example.facturacion.modelo.Cargo;
import com.example.facturacion.modelo.Factura;
import com.example.facturacion.repositorio.CargoRepositorio;
import com.example.facturacion.repositorio.FacturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/cargos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT, RequestMethod.POST})
public class ControladorCargos {
    //cuando llegue un primer cargo se debe crear una factura
    //vacia
    @Autowired
    CargoRepositorio cargoRepositorio;
    @Autowired
    FacturaRepositorio facturaRepositorio;
    @PostMapping
    public Cargo guardar(@RequestBody Cargo cargo){
        if(facturaRepositorio.findByIdAtencion(cargo.getIdAtencion())==null){

            //si no existe se agrega tambien una factura
            Factura factura= new Factura();
            List<Cargo> cargos=new ArrayList<Cargo>();
            factura.setIdPaciente(cargo.getIdPaciente());
            factura.setEstado("GENERADA");
            factura.setIdAtencion(cargo.getIdAtencion());
            //cargos.add(cargo);
            //factura.setCargos(cargos); // este par de huevadas da una excepcion porque json se comporta idiota
            cargo.setFactura(factura);
            facturaRepositorio.save(factura);

        }else{
            Factura factura=facturaRepositorio.findByIdAtencion(cargo.getIdAtencion());
            cargo.setFactura(factura);
            facturaRepositorio.save(factura); // se hace un verguero el json
        }
        //ya existe esa factura y se agrega sin mayor misterio
        //luego aqui mismo tenemos que tontear con la clave pendeja esa
        return cargoRepositorio.save(cargo);

    }
    @GetMapping("/{idAtencion}")
    public Iterable<Cargo> getByIdAtencion(@PathVariable(value ="idAtencion" )Integer idAtencion){
        return cargoRepositorio.findByIdAtencion(idAtencion);
    }


}
