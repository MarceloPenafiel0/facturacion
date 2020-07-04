package com.example.facturacion.repositorio;

import com.example.facturacion.modelo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepositorio extends JpaRepository<Cargo,Integer> { //integer puede ser string si tomamos la clave mezclada

}
