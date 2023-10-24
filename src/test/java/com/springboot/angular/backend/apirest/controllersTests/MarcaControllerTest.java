package com.springboot.angular.backend.apirest.controllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.angular.backend.apirest.controllers.MarcaController;
import com.springboot.angular.backend.apirest.entity.Auto;
import com.springboot.angular.backend.apirest.entity.Marca;
import com.springboot.angular.backend.apirest.services.IMarcaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarcaController.class)
public class MarcaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IMarcaService marcaService;

    Marca marca1;
    Marca marca2;

    ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper(); // Inicializa objectMapper antes de cada test
        marca1= new Marca(Long.valueOf(1), "Ferrari", "El mas rapido de totte italia", new HashSet<Auto>());
        marca2= new Marca(Long.valueOf(2), "Ferrari2", "El mas rapido de totte CHILE", new HashSet<Auto>());


    }

    @Test
    void showTest() throws Exception {
        when(marcaService.findById(Long.valueOf(1))).thenReturn(marca1);

        mvc.perform(get("/azurian/marcas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Ferrari"))
                .andExpect(jsonPath("$.descripcion").value("El mas rapido de totte italia"));
    }

    @Test
    void showNoValidoTest() throws Exception {
        // Simula que la Marca o el Auto con el ID 3 no existe (ajusta según la lógica de tu aplicación)
        when(marcaService.findById(Long.valueOf(3))).thenReturn(null);

        mvc.perform(get("/azurian/marcas/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").isNotEmpty());
    }

    @Test
    void indexTest() throws Exception {
        // Simula que el controlador maneja la solicitud GET a /tecnica/marcas
        when(marcaService.findAll()).thenReturn(new ArrayList<Marca>());

        // Realiza la solicitud GET a la URL del controlador
        mvc.perform(get("/azurian/marcas").contentType(MediaType.APPLICATION_JSON))

                // Asegura que el estado de la respuesta sea OK (200)
                .andExpect(status().isOk())

                // Asegura que el contenido sea JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Puedes agregar más aserciones según tu caso de uso

        // Verifica que el método "findAll" del servicio se llame
        verify(marcaService).findAll();
    }

    //@Test
    ////void indexTestNoValido() throws Exception {
        //     // Configura el servicio de marcas para que devuelva una lista vacía
        // when(marcaService.findAll()).thenReturn(Collections.emptyList());

        // Realiza la solicitud GET a la URL del controlador
        // mvc.perform(get("/azurian/marcas").contentType(MediaType.APPLICATION_JSON))

                // Asegura que el estado de la respuesta sea NOT_FOUND
        //  .andExpect(status().isNotFound())

                // Asegura que el cuerpo de la respuesta esté vacío
        //  .andExpect(jsonPath("$.ok").value(false))
        //   .andExpect(jsonPath("$.mensaje").value("No se encontraron marcas en la base de datos"));
        // }

    @Test
    void createMarcaTest() throws Exception {
        // Given
        Marca marca = new Marca();
        marca.setNombre("Toyota");
        when(marcaService.save(any())).thenReturn(marca);

        // When/Then
        mvc.perform(post("/azurian/postmarcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marca)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca.nombre").value("Toyota"));
    }

    @Test
    void createMarcaTestNoValido() throws Exception {
        // Given
        Marca marca = new Marca();
        // No establecer el campo "nombre"

        // When/Then
        mvc.perform(post("/azurian/postmarcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marca)))
                .andExpect(status().isCreated()) // Cambiado a 201
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateMarcaTest() throws Exception {
        // Given
        // Datos a modificar de la marca existente
        Marca marca = new Marca();
        marca.setNombre("NuevoNombreMarca");
        marca.setDescripcion("NuevaDescripciónMarca");

        when(marcaService.findById(Long.valueOf(1))).thenReturn(marca1); // Marca existente a modificar

        marca1.setNombre(marca.getNombre());
        marca1.setDescripcion(marca.getDescripcion());

        when(marcaService.save(any())).thenReturn(marca1);

        // When/Then
        mvc.perform(put("/azurian/marcas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marca)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca.nombre").value("NuevoNombreMarca"));
    }

    @Test
    void updateMarcaTestNoValido() throws Exception {
        // Given
        Marca marca = new Marca();
        marca.setNombre("Mercedez");

        when(marcaService.findById(Long.valueOf(1))).thenReturn(null); // Simular que la marca no existe

        // When/Then
        mvc.perform(put("/azurian/marcas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marca)))
                .andExpect(status().isNotFound()) // Cambiar a status().isNotFound()
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateMarcaTestNoEncontrado() throws Exception {
        // Given
        Marca marca = new Marca();
        marca.setNombre("Mercedez");

        when(marcaService.findById(Long.valueOf(1))).thenReturn(null); // Simular que la marca no existe

        // When/Then
        mvc.perform(put("/azurian/marcas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marca)))
                .andExpect(status().isNotFound()) // Cambiar a status().isNotFound()
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value("Error: no se pudo editar la marca con el ID: 1 no existe en la base de datos"));
    }

    @Test
    void deleteMarcaTest() throws Exception {
        // Given
        doNothing().when(marcaService).delete(any());

        // When/Then
        mvc.perform(delete("/azurian/marcas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("La marca ha sido eliminada con éxito!"));
    }

}
