package com.empresa.curso.repository;

import com.empresa.curso.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto, String> {

    List<Producto> findByNombre(String nombre);
    List<Producto> findByMarca(String marcar);
    List<Producto> findByPrecio(double precio);
    List<Producto> findByCantidad(int cantidad);

    @Query(value = "select count(*) from productos", nativeQuery = true)
    int findTotalProductos();

    @Query("select p from Producto p where p.fecha > :fecha")
    List<Producto> findConFecha(Date fecha);

}
