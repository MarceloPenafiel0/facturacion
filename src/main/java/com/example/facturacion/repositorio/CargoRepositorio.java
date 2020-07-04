package com.example.facturacion.repositorio;

import com.example.facturacion.modelo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CargoRepositorio extends JpaRepository<Cargo,Integer> { //integer puede ser string si tomamos la clave mezclada
    @Query(value="select * from cargo  where cargo.id_Atencion=?1",nativeQuery=true)
    Iterable<Cargo> findByIdAtencion(Integer idAtencion);

}
