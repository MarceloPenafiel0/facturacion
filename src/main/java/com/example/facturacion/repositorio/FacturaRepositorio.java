package com.example.facturacion.repositorio;

import com.example.facturacion.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaRepositorio extends JpaRepository<Factura,Integer> {
    @Query(value="select * from factura  where factura.idAtencion=?1",nativeQuery=true)
    Factura findByIdAtencion(Integer idAtencion);
}
