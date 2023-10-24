package com.springboot.angular.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.angular.backend.apirest.entity.Marca;
import com.springboot.angular.backend.apirest.services.IMarcaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/azurian")
public class MarcaController {

	@Autowired
	private IMarcaService marcaService;
	
	@GetMapping("/marcas")
	public List<Marca> getMarcas(){
		return marcaService.findAll();
	}
	
	@GetMapping("/marcas/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		Marca marca = null;
		
		Map<String, Object> reponse = new HashMap<>();
		
		try {
			marca = marcaService.findById(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			reponse.put("mensaje", "Error al realizar la consulta a la BDD");
		}
		
		if(marca == null) {
			reponse.put("mensaje", "La marca ID: ".concat(id.toString().concat(" No existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(reponse, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Marca>(marca, HttpStatus.OK);
	}
	
	
	@PostMapping("/postmarcas")
	public ResponseEntity<?> create(@Valid @RequestBody Marca marca, BindingResult result ) {
		
		Marca marcaNueva = null;
		Map<String, Object>  response = new HashMap<>();
		
		if(result.hasErrors()) {
			/*
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()){
                errors.add("El campo '" + err.getField()  +"' "+ err.getDefaultMessage());
            }

             */
			List<String> errors = result.getFieldErrors()
									.stream()
									.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())
									.collect(Collectors.toList());
			response.put("errors",errors );
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		try {
			marcaNueva = marcaService.save(marca);
			 
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al insertar dato en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		response.put("mensaje", "La marca ha sido creada con éxito!");
		response.put("marca",marcaNueva);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/marcas/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Marca marca, BindingResult result, @PathVariable Long id) {
		Marca marcaActual = marcaService.findById(id);
		
		Marca marcaActualizado = null;
		Map<String, Object>  response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
									.stream()
									.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())
									.collect(Collectors.toList());
			response.put("errors",errors );
			response.put("ok", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		if(marcaActual == null) {
			response.put("mensaje", "Error: no se pudo editar la marca con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			marcaActual.setDescripcion(marca.getDescripcion());
			marcaActual.setAutos(marca.getAutos());
			marcaActual.setNombre(marca.getNombre());
		

			
			marcaActualizado = marcaService.save(marcaActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		response.put("mensaje", "La marca ha sido actualizada con éxito!");
		response.put("marca",marcaActualizado );
		response.put("ok", true);
		 return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/marcas/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try{
            
            marcaService.delete(id);
            
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar la marca");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La marca ha sido eliminada con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);


    }
	
}
