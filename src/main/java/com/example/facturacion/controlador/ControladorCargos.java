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
            factura.setIdPaciente(cargo.getIdPaciente());
            factura.setEstado("ABIERTA");   //En la presentacion de interciclo el inge pidio cambio en el nombre de los estados
            factura.setIdAtencion(cargo.getIdAtencion());
            factura.addCargo(cargo);
            //cargos.add(cargo);
            //factura.setCargos(cargos); // este par de huevadas da una excepcion porque json se comporta idiota
            //cargo.setFactura(factura);
            facturaRepositorio.save(factura);

        }else{
            Factura factura=facturaRepositorio.findByIdAtencion(cargo.getIdAtencion());
            factura.addCargo(cargo);
            facturaRepositorio.save(factura); // se hace un verguero el json -> Update 07/05/20: Se arreglo el json pero no devuelve la informacion dela factura
        }
        //ya existe esa factura y se agrega sin mayor misterio
        //luego aqui mismo tenemos que tontear con la clave pendeja esa

        //Solucion temporal, aqui deberiamos buscar por el ID codificado
        Cargo dummy = cargoRepositorio.findById(7).orElse(new Cargo());
        System.out.println("Saved " + dummy.getFactura().getId());
        return dummy;

    }
    @GetMapping("/{idAtencion}")
    public Iterable<Cargo> getByIdAtencion(@PathVariable(value ="idAtencion" )Integer idAtencion){
        return cargoRepositorio.findByIdAtencion(idAtencion);
    }


}
