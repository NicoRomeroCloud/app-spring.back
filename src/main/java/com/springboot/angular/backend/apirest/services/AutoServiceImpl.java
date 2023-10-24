package com.springboot.angular.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.angular.backend.apirest.entity.Auto;
import com.springboot.angular.backend.apirest.repository.IAutoRepository;

@Service
public class AutoServiceImpl implements IAutoService{

	
	@Autowired
	IAutoRepository autoService;
	
	@Override
	public List<Auto> findAll() {
		// TODO Auto-generated method stub
		return (List<Auto>) autoService.findAll();
	}

	@Override
	public Auto save(Auto auto) {
		// TODO Auto-generated method stub
		return autoService.save(auto);
	}

	@Override
	public Auto findById(Long id) {
		// TODO Auto-generated method stub
		return autoService.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
		autoService.deleteById(id);
		
	}

	@Override
	public List<Auto> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return autoService.findByNombre(nombre);
	}

}
