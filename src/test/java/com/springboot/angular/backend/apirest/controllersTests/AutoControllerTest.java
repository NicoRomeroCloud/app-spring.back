package com.springboot.angular.backend.apirest.controllersTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.angular.backend.apirest.controllers.AutoController;
import com.springboot.angular.backend.apirest.entity.Auto;
import com.springboot.angular.backend.apirest.entity.Marca;
import com.springboot.angular.backend.apirest.services.IAutoService;

@WebMvcTest(AutoController.class)
public class AutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IAutoService autoService;

    @InjectMocks
    private AutoController autoController;

    Marca marca1;
    Marca marca2;
    Auto auto1;
    Auto auto2;
    Auto auto3;

    ObjectMapper objectMapper;

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
        objectMapper = new ObjectMapper();
    }

    @Test
    void showTest() throws Exception {
        // given
        when(autoService.findById(Long.valueOf(1))).thenReturn(auto1);

        // when
        mvc.perform(get("/azurian/autos/1").contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.auto.nombre").value("FordAdventure"));
    }

    @Test
    void createAutoTest() throws Exception {
        // Given
        Auto auto = new Auto();
        auto.setNombre("Ford");
        auto.setPrecio(25000);
        when(autoService.save(any(Auto.class))).thenReturn(auto);

        // When
        mvc.perform(post("/azurian/autos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(auto)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.auto.nombre").value("Ford"));
    }

    //@Test
    // void createAutoTestNoValido() throws Exception {
        // Given
        // Auto auto = new Auto();
        // auto.setPrecio(2000);

        // When
        // mvc.perform(post("/azurian/autos")
                //          .contentType(MediaType.APPLICATION_JSON)
                //       .content(objectMapper.writeValueAsString(auto)))

                // Then
        //   .andExpect(status().isBadRequest()) // Cambiamos la expectativa a 201 (Created)
        //  .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        //}
    // Resto de tus pruebas

    @Test
    void updateAutoTest() throws Exception {
        // Given
        // Datos a modificar del auto1
        Auto auto = new Auto();
        auto.setNombre("Modelo 2023");
        auto.setPrecio(25000);
        auto.setMarca(marca1);

        when(autoService.findById(1L)).thenReturn(auto1);

        auto1.setNombre(auto.getNombre());
        auto1.setPrecio(auto.getPrecio());
        auto1.setMarca(auto.getMarca());

        when(autoService.save(any())).thenReturn(auto1);

        // When
        mvc.perform(put("/azurian/autos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(auto)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.auto.nombre").value("Modelo 2023"));
    }

    // @Test
    // void updateAutoTestNoValido() throws Exception {
        // Given
        // Datos a modificar del auto1
        //  Auto auto = new Auto();
        // auto.setPrecio(2000);

        // when(autoService.findById(1L)).thenReturn(auto1);

        // When
        // mvc.perform(put("/azurian/autos/1")
                //            .contentType(MediaType.APPLICATION_JSON)
                //          .content(objectMapper.writeValueAsString(auto)))

                // Then
        //     .andExpect(status().isBadRequest())
        //    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // }

    @Test
    void updateAutoTestNoEncontrado() throws Exception {
        // Given
        // Datos a modificar del auto1
        Auto auto = new Auto();
        auto.setPrecio(20000);
        auto.setNombre("nombre de prueba");
        when(autoService.findById(1L)).thenReturn(null);

        // When
        mvc.perform(put("/azurian/autos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(auto)))

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ok").value(false));
    }


    @Test
    void deleteAutoTest() throws Exception {
        // Given
        doNothing().when(autoService).delete(any());

        // When
        mvc.perform(delete("/azurian/autos/1")
                        .contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Auto eliminado con exito!"));  // Corregir aquí
    }
}