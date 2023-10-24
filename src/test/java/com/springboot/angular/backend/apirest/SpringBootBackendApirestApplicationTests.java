package com.springboot.angular.backend.apirest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.angular.backend.apirest.entity.Auto;
import com.springboot.angular.backend.apirest.entity.Marca;
import com.springboot.angular.backend.apirest.repository.IAutoRepository;
import com.springboot.angular.backend.apirest.repository.IMarcaRepository;
import com.springboot.angular.backend.apirest.services.AutoServiceImpl;
import com.springboot.angular.backend.apirest.services.MarcaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import java.util.*;

@SpringBootTest
class SpringBootBackendApirestApplicationTests {


	@Mock
	IAutoRepository autoRepository;

	@Mock
	IMarcaRepository marcaRepository;

	@InjectMocks
	AutoServiceImpl autoService;

	@InjectMocks
	MarcaServiceImpl marcaService;

	Marca marca1;
	Marca marca2;

	Auto auto1;
	Auto auto2;
	Auto auto3;


	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		marca1 = new Marca(Long.valueOf(1), "Mercedes", "Marca de autos de lujo", new HashSet<>());
		marca2 = new Marca(Long.valueOf(2), "Toyota", "Marca de autos japonesa", new HashSet<>());
		auto1 = new Auto();
		auto1.setId(Long.valueOf(1));
		auto1.setNombre("FordAdventure");
		auto1.setPrecio(20000);
		auto1.setMarca(marca1);
		auto2 = new Auto();
		auto2.setId(Long.valueOf(2));
		auto2.setNombre("Adventure");
		auto2.setPrecio(3000);
		auto2.setMarca(marca2);
		auto3 = new Auto();
		auto3.setId(Long.valueOf(3));
		auto3.setNombre("Adventure2");
		auto3.setPrecio(40000);
		auto3.setMarca(marca1);
	}

	@Test
	void findAutoByIdTest() {
		when(autoRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(auto1));

		Auto buscado = autoService.findById(Long.valueOf(1));

		assertEquals("FordAdventure", buscado.getNombre()); // Cambiado a "FordAdventure"
		assertEquals(20000, buscado.getPrecio()); // Cambiado a 20000
		assertEquals(Long.valueOf(1), buscado.getId());
		assertEquals("Mercedes", buscado.getMarca().getNombre()); // Cambiado a "Mercedes"

		verify(autoRepository).findById(Long.valueOf(1));
	}

	@Test
	void saveAutoTest() {
		// Given
		Auto fordAdventure = new Auto();
		fordAdventure.setId(Long.valueOf(1));
		fordAdventure.setNombre("FordAdventure");
		fordAdventure.setPrecio(20000);
		fordAdventure.setMarca(marca1);

		when(autoRepository.save(any())).then(invocation -> {
			Auto auto = invocation.getArgument(0);
			auto.setId(Long.valueOf(3)); // Simulamos la asignación del ID
			return auto;
		});

		// When
		Auto autoPrueba = autoService.save(fordAdventure);

		// Then
		assertEquals(Long.valueOf(3), autoPrueba.getId()); // Verificamos que el ID sea 3
		assertEquals("FordAdventure", autoPrueba.getNombre()); // Verificamos el nombre
		assertEquals(20000, autoPrueba.getPrecio()); // Verificamos el precio
		assertEquals("Mercedes", autoPrueba.getMarca().getNombre()); // Verificamos la marca

		verify(autoRepository).save(any()); // Verificamos que se haya llamado a save en el repositorio
	}



	@Test
	void findAllAutoTest() {
		// Given
		List<Auto> autos = new ArrayList<Auto>();
		autos.add(auto1);
		autos.add(auto2);

		when(autoRepository.findAll()).thenReturn(autos);

		// When
		List<Auto> autosPrueba = autoService.findAll();

		// Then
		assertFalse(autosPrueba.isEmpty());
		assertEquals(2, autosPrueba.size());

		assertEquals(Long.valueOf(1), autosPrueba.get(0).getId());
		assertEquals("FordAdventure", autosPrueba.get(0).getNombre());
		assertEquals(20000, autosPrueba.get(0).getPrecio());
		assertEquals("Mercedes", autosPrueba.get(0).getMarca().getNombre());

		assertEquals(Long.valueOf(2), autosPrueba.get(1).getId());
		assertEquals("Adventure", autosPrueba.get(1).getNombre());
		assertEquals(3000, autosPrueba.get(1).getPrecio());
		assertEquals("Toyota", autosPrueba.get(1).getMarca().getNombre());

		verify(autoRepository).findAll();
	}

	@Test
	void findAutoByNombreTest() {
		when(autoRepository.findByNombre(any())).thenReturn(Arrays.asList(auto1));

		List<Auto> autosBuscados = autoService.findByNombre("FordAdventure");

		assertFalse(autosBuscados.isEmpty());
		assertEquals(1, autosBuscados.size());
		assertEquals("FordAdventure", autosBuscados.get(0).getNombre());
		assertEquals(20000, autosBuscados.get(0).getPrecio());
		assertEquals(Long.valueOf(1), autosBuscados.get(0).getId());
		assertEquals("Mercedes", autosBuscados.get(0).getMarca().getNombre());

		verify(autoRepository).findByNombre(any());
	}


	@Test
	void DeleleMarcaTest() {
		doNothing().when(marcaRepository).deleteById(any());

		marcaService.delete(Long.valueOf(1));

		verify(marcaRepository).deleteById(any());
	}


	//_-------------------------------_________

	@Test
	void findMarcaByIdTest() {
		when(marcaRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(marca1));

		Marca buscada = marcaService.findById(Long.valueOf(1));

		assertEquals("Mercedes", buscada.getNombre());
		assertEquals("Marca de autos de lujo", buscada.getDescripcion());
		assertEquals(Long.valueOf(1), buscada.getId());
		assertEquals(0, buscada.getAutos().size());

		verify(marcaRepository).findById(Long.valueOf(1));
	}

	@Test
	void saveMarcaTest() {
		// Given
		Marca mercedes = new Marca(null, "Mercedes", "Marca de autos de lujo", new HashSet<Auto>());
		when(marcaRepository.save(any())).then(invocation -> {
			Marca m = invocation.getArgument(0);
			// Se setea el id de la marca nueva que venga, en este caso "Mercedes"
			m.setId(Long.valueOf(3));
			return m;
		});

		// When
		Marca marcaPrueba = marcaService.save(mercedes);

		// Then
		assertEquals(Long.valueOf(3), marcaPrueba.getId());
		assertEquals("Mercedes", marcaPrueba.getNombre());
		assertEquals("Marca de autos de lujo", marcaPrueba.getDescripcion());

		verify(marcaRepository).save(any());
	}

	@Test
	void findAllMarcaTest() {
		// Given
		List<Marca> marcas = new ArrayList<Marca>();
		marcas.add(marca1);
		marcas.add(marca2);
		when(marcaRepository.findAll()).thenReturn(marcas);

		// When
		List<Marca> marcasPrueba = marcaService.findAll();

		// Then
		assertFalse(marcasPrueba.isEmpty());
		assertEquals(2, marcasPrueba.size());
		verify(marcaRepository).findAll();
	}

	@Test
	void findMarcaByNombreTest() {
		List<Marca> marcas = new ArrayList<Marca>();
		marcas.add(marca1);
		when(marcaRepository.findByNombre(any())).thenReturn(marcas);

		List<Marca> marcasBuscadas = marcaService.findByNombre("Mercedes");

		assertEquals(1, marcasBuscadas.size());

		verify(marcaRepository).findByNombre(any());
	}

	@Test
	void deleteMarcaTest() {
		doNothing().when(marcaRepository).deleteById(any());

		marcaService.delete(Long.valueOf(1));

		verify(marcaRepository).deleteById(any());
	}

	@Test
	void contextLoads() {
	}

}
