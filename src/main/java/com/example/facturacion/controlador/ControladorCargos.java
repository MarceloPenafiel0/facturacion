package com.example.facturacion.controlador;


import com.example.facturacion.modelo.Cargo;
import com.example.facturacion.modelo.Factura;
import com.example.facturacion.repositorio.CargoRepositorio;
import com.example.facturacion.repositorio.FacturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;


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
    public Cargo guardar(@RequestBody Map<String,Object> mapedJson){
        Cargo cargo =  new Cargo(mapedJson); // Lanza excepcion en el caso que el json tenga algo mal
        Factura factura =facturaRepositorio.findByIdAtencion(cargo.getIdAtencion());
        if(factura==null){
            //si no existe se agrega tambien una factura
            factura= new Factura();
            factura.setIdPaciente(cargo.getIdPaciente());
            factura.setEstado("ABIERTA");   //En la presentacion de interciclo el inge pidio cambio en el nombre de los estados
            factura.setIdAtencion(cargo.getIdAtencion());
            factura.addCargo(cargo);
        }else{
            factura.addCargo(cargo);
            //facturaRepositorio.save(factura); // se hace un verguero el json -> Update 07/05/20: Se arreglo el json pero no devuelve la informacion dela factura
        }
        //ya existe esa factura y se agrega sin mayor misterio
        facturaRepositorio.save(factura);
        System.out.println("****** "+cargo.getId());
        return cargo;
    }
    @GetMapping("/{idAtencion}")
    public Iterable<Cargo> getByIdAtencion(@PathVariable(value ="idAtencion" )Integer idAtencion){
        return cargoRepositorio.findByIdAtencion(idAtencion);
    }

    @PostMapping("/test")
    public Map<String,Object> testCustomJson(@RequestBody Map<String,Object> payload){
        for (String key: payload.keySet()) {
            System.out.println("---- "+payload.get(key).getClass());
            System.out.println("**** "+"Key= "+key+"    Value= "+payload.get(key).toString());
        }
        Map<String,Object> dummy = new LinkedHashMap<>();
        dummy.put("food","lasagna");
        dummy.put("animal","ostrish");
        return dummy;
    }



}
