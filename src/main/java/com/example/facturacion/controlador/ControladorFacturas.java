package com.example.facturacion.controlador;

import com.example.facturacion.modelo.Factura;
import com.example.facturacion.repositorio.FacturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT, RequestMethod.POST})
public class ControladorFacturas {
    @Autowired
    FacturaRepositorio facturaRepositorio;

    @GetMapping("/idatencion/{idAtencion}")//vale para consultar el estado también
    public Factura buscarIdatencion(@PathVariable(value = "idAtencion")Integer idAtencion){
        return facturaRepositorio.findByIdAtencion(idAtencion);
    }
    @GetMapping("/idpaciente/{idPaciente}")//vale para consultar el estado también
    public Iterable<Factura> buscarIdpaciente(@PathVariable(value = "idPaciente")Integer idPaciente){
        return facturaRepositorio.findByIdPaciente(idPaciente);
    }
    @GetMapping("/estado/{estado}")
    public Iterable<Factura> getByEstado(@PathVariable(value = "estado")String estado){
        return facturaRepositorio.findByEstado(estado);
    }
    @GetMapping("/persona/{persona_id}")
    public Iterable<Factura> getByEstado(@PathVariable(value = "persona_id")Integer persona_id){
        return facturaRepositorio.findByPersona(persona_id);
    }
    @GetMapping("/numeroFactura/{numeroFactura}")
    public Factura getBynumFactura(@PathVariable(value = "numeroFactura")Integer numeroFactura){
        return facturaRepositorio.findByNumeroFactura(numeroFactura);
    }
    //actualizar factura por la razon que sea
    @PutMapping("/actualizar") //actualizar por la razón que sea
    public Factura actualizar(@RequestBody Factura factura){
        return facturaRepositorio.save(factura);//si es con la misma clave solo actualiza
    }
}
