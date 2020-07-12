package com.example.facturacion.controlador;

import com.example.facturacion.exceptions.DataNotFoundException;
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
import java.util.ArrayList;
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
    public Map<String,Object> buscarIdatencion(@PathVariable(value = "idAtencion")Integer idAtencion){
        Factura factura = facturaRepositorio.findByIdAtencion(idAtencion);
        return factura.toMap();
    }
    @GetMapping("/idpaciente/{idPaciente}")//vale para consultar el estado también
    public Iterable<Map<String,Object>> buscarIdpaciente(@PathVariable(value = "idPaciente")Integer idPaciente){
        Iterable<Factura> facturaList = facturaRepositorio.findByIdPaciente(idPaciente);
        Iterable<Map<String,Object>> mapList = mapIterable(facturaList);
        return mapList;
    }
    @GetMapping("/estado/{estado}")
    public Iterable<Map<String,Object>>  getByEstado(@PathVariable(value = "estado")String estado){
        Iterable<Factura> facturaList = facturaRepositorio.findByEstado(estado);
        Iterable<Map<String,Object>> mapList = mapIterable(facturaList);
        return mapList;
    }
    @GetMapping("/persona/{persona_id}")
    public Iterable<Map<String,Object>> getByEstado(@PathVariable(value = "persona_id")Integer persona_id){
        Iterable<Factura> facturaList = facturaRepositorio.findByPersona(persona_id);
        Iterable<Map<String,Object>> mapList = mapIterable(facturaList);
        return mapList;
    }
    @GetMapping("/numeroFactura/{numeroFactura}")
    public Map<String,Object> getBynumFactura(@PathVariable(value = "numeroFactura")Integer numeroFactura){
        Factura factura = facturaRepositorio.findByNumeroFactura(numeroFactura);
        return factura.toMap();
    }
    //actualizar factura por la razon que sea
    @PutMapping("/actualizar") //actualizar por la razón que sea
    public Map<String,Object> actualizar(@RequestBody Factura factura){
        Factura factura1 = facturaRepositorio.save(factura);
        return factura1.toMap();//si es con la misma clave solo actualiza
    }

    @PutMapping("/actualizar/responsable")
    public Map<String,Object> actualizarResponsable(@RequestBody Map<String,Object> mapedJason){
        System.out.println(mapedJason);
        Factura factura = facturaRepositorio.findByIdAtencion((int)mapedJason.get("idAtencion"));
        Persona persona = personaRepositorio.findByCedula(mapedJason.get("cedula").toString());
        if (persona == null){
            throw new DataNotFoundException();
        }
        factura.setPersona(persona);
        factura = facturaRepositorio.save(factura);
        return factura.toMap();
    }

    //Actualizacion de estado
    @PutMapping("/actualizar/estado/{idFactura}")
    public Map<String,Object> actualizarEstado(@PathVariable (value = "idFactura") Integer idFactura,
                                               @RequestBody Map<String,Object> mapedJson){
        System.out.println(idFactura);
        System.out.println(mapedJson.get("estado"));
        Factura factura = facturaRepositorio.findById(idFactura).get();
        factura.setEstado(mapedJson.get("estado").toString());
        factura = facturaRepositorio.save(factura);
        return factura.toMap();
    }

    @Transactional
    @PutMapping("/emitir")
    public Map<String,Object> emitirFactura(@RequestBody Map<String,Object> mapedJson){
        System.out.println("**** "+mapedJson.get("idAtencion"));
        System.out.println("---- "+mapedJson.get("idPersona"));
        Factura factura = facturaRepositorio.lockFindByIdAtencion((int)mapedJson.get("idAtencion"));
        //String factura = facturaRepositorio.lockFindByIdAtencion((int)mapedJson.get("idAtencion"));
        System.out.println("-*-*-*-"+factura);
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
        return factura.toMap();
    }

    @PutMapping("/anular/{numeroFactura}")
    public Map<String,Object> anularFactura(@PathVariable (value = "numeroFactura") Integer numeroFactura){
        Factura factura = facturaRepositorio.findByNumeroFactura(numeroFactura);
        factura.setEstado("ANULADA");
        factura = facturaRepositorio.save(factura);
        return factura.toMap();
    }

    private Iterable<Map<String,Object>> mapIterable (Iterable<Factura> iterable){
        ArrayList<Map<String, Object>> ite = new ArrayList<>();
        for (Factura fac: iterable) {
            ite.add(fac.toMap());
        }
        return ite;
    }
}
