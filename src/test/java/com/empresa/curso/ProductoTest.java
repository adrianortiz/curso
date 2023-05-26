package com.empresa.curso;

import com.empresa.curso.entity.Producto;
import com.empresa.curso.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Sql({"/squema.sql", "/data.sql"})
class ProductoTest {

	@Autowired
	ProductoRepository repository;

	@Test
	void buscarTodosTest() {
		Iterable<Producto> iterable = repository.findAll();
		List<Producto> productos = new ArrayList<Producto>();

		iterable.forEach(System.out::println);
		iterable.forEach(productos::add);

		assertThat(productos.size(), greaterThan(4));
	}

	@Test
	void buscarUnoTest() {

		Optional<Producto> optional = repository.findById("P0003");

		if (optional.isPresent()) {
			assertThat(optional.get().getNombre(), equalTo("Laptop"));
		}
	}

	@Test
	void buscarVariosTest() {
		Iterable<Producto> iterable = repository.findAllById(List.of("P0001", "P0002"));
		List<Producto> productos = new ArrayList<Producto>();
		iterable.forEach(productos::add);

		assertThat(productos.get(0).getNombre(), equalTo("iPhone"));
		assertThat(productos.get(1).getNombre(), equalTo("iPad"));

	}

	@Test
	void buscarPorPrecio() {
		List<Producto> productos = repository.findByPrecio(1000);
		assertThat(productos, hasItem(new Producto("P0001")));
		assertThat(productos, hasItem(new Producto("P0005")));
	}

	@Test
	void totalProductos() {
		int total = repository.findTotalProductos();
		assertThat(total, equalTo(5));
	}

	@Test
	void buscarPorFecha() {

		SimpleDateFormat f = new SimpleDateFormat("dd-MM-YYYY");
		Date fecha = null;

		try {
			fecha = f.parse("24-04-2023");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Producto> productos = repository.findConFecha(fecha);
		productos.forEach(System.out::println);

		assertThat(productos, hasItem(new Producto("P0001")));
		assertThat(productos, hasItem(new Producto("P0002")));
		assertThat(productos, hasItem(new Producto("P0003")));
	}

	@Test
	void insertarUno() {
		Producto producto = new Producto("P0006", "Monitor", "Sony", 2500.50, 20, new Date());
		repository.save(producto);

		Optional<Producto> producto1 = repository.findById("P0006");
		if (producto1.isPresent()) {
			assertThat(producto1.get().getNombre(), equalTo("Monitor"));
		}
	}

	@Test
	void actualizarUnoTest() {

		Optional<Producto> optional = repository.findById("P0004");

		if (optional.isPresent()) {
			Producto pActualizar = optional.get();
			assertThat(pActualizar.getNombre(), equalTo("Smart watch"));
			assertThat(pActualizar.getMarca(), equalTo("Samsung"));

			pActualizar.setNombre("Smart watch Pro");
			pActualizar.setMarca("Samsung Computer");
			pActualizar.setPrecio(550.50);

			repository.save(pActualizar);

			Optional<Producto> optional2 = repository.findById("P0004");
			if (optional2.isPresent()) {
				Producto pActualizado = optional2.get();
				assertThat(pActualizado.getNombre(), equalTo("Smart watch Pro"));
				assertThat(pActualizado.getMarca(), equalTo("Samsung Computer"));
				assertThat(pActualizado.getPrecio(), equalTo(550.5));
			}
		}
	}

	@Test
	void borrarUnoTest() {
		Producto producto = new Producto("P0002");
		repository.delete(producto);

		Optional<Producto> optional = repository.findById("P0002");
		assertFalse(optional.isPresent());
	}

}
