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

import com.springboot.angular.backend.apirest.entity.Auto;
import com.springboot.angular.backend.apirest.services.IAutoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/azurian")
public class AutoController {

	@Autowired
	private IAutoService autoService;
	
	@GetMapping("/autos")
	public List<Auto> get(){
		return autoService.findAll();
	}
	
	@GetMapping("/autos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Auto auto = null;
		
		Map<String, Object>  response = new HashMap<>();
		try {
			auto = autoService.findById(id);
			
		}catch(RuntimeException e) {
			response.put("mensaje", "error al realizar la consulta en la base de datos");
			response.put("ok", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			
		}
		
		if(auto == null) {
			response.put("ok", false);
			response.put("mensaje", "El auto con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("auto", auto);
		response.put("ok", true);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/autos")
	public ResponseEntity<?> create(@Valid @RequestBody Auto auto, BindingResult result ) {
		
		Auto autoNew = null;
		Map<String, Object>  response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
									.stream()
									.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())
									.collect(Collectors.toList());
			response.put("errors",errors );
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		try {
			autoNew = autoService.save(auto);
			 
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("mensaje", "El auto ha sido creado con éxito!");
		response.put("auto",autoNew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/autos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Auto auto, BindingResult result, @PathVariable Long id) {
		Auto autoActual = autoService.findById(id);
		
		Auto autoActualizado = null;
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
		
		if(autoActual == null) {
			response.put("ok", false);
			response.put("mensaje", "Error: no se pudo editar el libro con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			autoActual.setNombre(auto.getNombre());
			autoActual.setMarca(auto.getMarca());
			autoActual.setPrecio(auto.getPrecio());

			
			autoActualizado = autoService.save(autoActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		response.put("mensaje", "El auto ha sido actualizado");
		response.put("auto",autoActualizado );
		response.put("ok", true);
		 return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/autos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object>  response = new HashMap<>();
		
		try {
			
			autoService.delete(id);
			
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al eliminar el auto de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		response.put("mensaje", "Auto eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
