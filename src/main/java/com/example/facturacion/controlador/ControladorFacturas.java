package com.example.facturacion.controlador;

import com.example.facturacion.modelo.Cargo;
import com.example.facturacion.modelo.Factura;
import com.example.facturacion.modelo.Persona;
import com.example.facturacion.repositorio.CargoRepositorio;
import com.example.facturacion.repositorio.FacturaRepositorio;
import com.example.facturacion.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT, RequestMethod.POST})
public class ControladorFacturas {
    @Autowired
    FacturaRepositorio facturaRepositorio;
    @Autowired
    CargoRepositorio cargoRepositorio;
    @Autowired
    PersonaRepositorio personaRepositorio;

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

    @Transactional
    @PutMapping("/emitir/")
    public Factura emitirFactura(@RequestBody Map<String,Object> mapedJson){
        System.out.println("**** "+mapedJson.get("idAtencion"));
        System.out.println("---- "+mapedJson.get("idPersona"));
        Factura factura = facturaRepositorio.lockFindByIdAtencion((int)mapedJson.get("idAtencion"));
        int numeroFactura = facturaRepositorio.countDistinctByEstado("ABIERTA");
        factura.setNumeroFactura(numeroFactura+1);
        Iterable<Cargo> cargos = cargoRepositorio.findByIdAtencion(factura.getIdAtencion());
        double total=0.0;
        for (Cargo cargo: cargos) {
            total+=cargo.getValor()*cargo.getCantidad();
        }
        factura.setTotal(total);
        Persona persona = personaRepositorio.findById((int)mapedJson.get("idPersona")).get();
        factura.setPersona(persona);
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        factura.setFechaEmision(dft.format(now));
        factura.setEstado("EMITIDA");
        facturaRepositorio.save(factura);
        return factura;
    }

    @PutMapping("/anular/{numeroFactura}")
    public Factura anularFactura(@PathVariable (value = "numeroFactura") Integer numeroFactura){
        Factura factura = facturaRepositorio.findByNumeroFactura(numeroFactura);
        factura.setEstado("ANULADA");
        return facturaRepositorio.save(factura);
    }

}
