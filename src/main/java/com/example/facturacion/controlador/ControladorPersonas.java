package com.example.facturacion.controlador;

import com.example.facturacion.modelo.Persona;
import com.example.facturacion.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/persona")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT, RequestMethod.POST})
public class ControladorPersonas {
    @Autowired
    PersonaRepositorio personaRepositorio;

    @PostMapping
    public Persona guardar(@RequestBody Persona persona){
        System.out.println(persona.getNombre());
        personaRepositorio.save(persona);
        return persona;
    }

    @GetMapping //para listar en la tablita
    public Iterable<Persona> getTodos(){
        return personaRepositorio.findAll();
    }

    @GetMapping("/{cedula}")
    public Persona getByCedula(@PathVariable(value="cedula")String cedula){

        if (personaRepositorio.findByCedula(cedula)!=null){
            return personaRepositorio.findByCedula(cedula);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Persona no Encontrada");
        }
    }

    @DeleteMapping("/{cedula}")
    public void borrarPersona(@PathVariable(value = "cedula")String cedula){
        if (personaRepositorio.findByCedula(cedula)!=null){
            personaRepositorio.delete(personaRepositorio.findByCedula(cedula));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Persona no Encontrada");
        }

    }
}
