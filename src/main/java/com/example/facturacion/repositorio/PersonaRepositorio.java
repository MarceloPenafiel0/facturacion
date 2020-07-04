package com.example.facturacion.repositorio;

import com.example.facturacion.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonaRepositorio extends JpaRepository<Persona,Integer> {
    @Query(value="select * from persona  where persona.cedula=?1",nativeQuery=true)
    Persona findByCedula(String cedula);

}
