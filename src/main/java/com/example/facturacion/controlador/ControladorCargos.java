package com.example.facturacion.controlador;


import com.example.facturacion.modelo.Cargo;
import com.example.facturacion.modelo.Factura;
import com.example.facturacion.repositorio.CargoRepositorio;
import com.example.facturacion.repositorio.FacturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
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
            factura.setEstado("GENERADA");
            factura.setIdAtencion(cargo.getIdAtencion());
            //factura.addCargo(cargo);
            facturaRepositorio.save(factura);

        }
        //ya existe esa factura y se agrega sin mayor misterio
        //luego aqui mismo tenemos que tontear con la clave pendeja esa
        return cargoRepositorio.save(cargo);

    }
    @GetMapping("{idAtencion}")
    public Cargo getByIdAtencion(@PathVariable(value ="idAtencion" )Integer idAtencion){
        return cargoRepositorio.findByIdAtencion(idAtencion);
    }


}
