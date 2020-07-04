package com.example.facturacion.controlador;

import com.example.facturacion.modelo.Persona;
import com.example.facturacion.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/persona")
public class ControladorPersonas {
    @Autowired
    PersonaRepositorio personaRepositorio;

    @PostMapping
    public void guardar(@RequestBody Persona persona){
        personaRepositorio.save(persona);
    }
    @GetMapping("/{cedula}")
    public Persona getByCedula(@PathVariable(value="cedula")String cedula){
        return personaRepositorio.findByCedula(cedula);
    }
}
