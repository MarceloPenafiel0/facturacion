package com.example.facturacion.repositorio;

import com.example.facturacion.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface FacturaRepositorio extends JpaRepository<Factura,Integer> {

    @Query(value="select * from factura  where factura.id_Atencion=?1",nativeQuery=true)
    Factura findByIdAtencion(Integer idAtencion);

    @Query(value="select * from factura  where factura.estado=?1",nativeQuery=true)
    Iterable<Factura> findByEstado(String estado);

    @Query(value="select * from factura  where factura.persona_id=?1",nativeQuery=true)
    Iterable<Factura> findByPersona(Integer persona_id);

    @Query(value="select * from factura  where factura.numero_factura=?1",nativeQuery=true)
    Factura findByNumeroFactura(Integer numeroFactura);

    @Query(value="select * from factura  where factura.id_paciente=?1",nativeQuery=true)
    Iterable<Factura> findByIdPaciente(Integer idpaciente);

    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="-2")})
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value="select f from factura f where f.id_Atencion=?1",nativeQuery = true)
    Factura lockFindByIdAtencion(Integer idAtencion);

    @Query(value = "select count (*) from factura where factura.estado!=?1 ",nativeQuery = true)
    Integer countDistinctByEstado(String estado);

}
