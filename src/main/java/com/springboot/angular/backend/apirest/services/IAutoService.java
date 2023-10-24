package com.springboot.angular.backend.apirest.services;

import java.util.List;

import com.springboot.angular.backend.apirest.entity.Auto;

public interface IAutoService {

	public List<Auto> findAll();
	
	public Auto save(Auto auto);
	
	public Auto findById(Long id);
	
	public void delete(Long id);
	
	public List<Auto> findByNombre(String nombre);
}
