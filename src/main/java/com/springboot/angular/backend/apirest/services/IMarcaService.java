package com.springboot.angular.backend.apirest.services;

import java.util.List;

import com.springboot.angular.backend.apirest.entity.Marca;

public interface IMarcaService {

	public List<Marca> findAll();
	
	public Marca save(Marca marca);
	
	public Marca findById(Long id);
	
	public void delete(Long id);
	
	public List<Marca> findByNombre(String nombre);
	
}
