package com.springboot.angular.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.angular.backend.apirest.entity.Marca;
import com.springboot.angular.backend.apirest.repository.IMarcaRepository;

@Service
public class MarcaServiceImpl implements IMarcaService{
	
	@Autowired
	IMarcaRepository iMarcaRepository;
	
	@Override
	public List<Marca> findAll(){
		
		return (List<Marca>)iMarcaRepository.findAll();
	}

	@Override
	public Marca save(Marca marca) {
		// TODO Auto-generated method stub
		return iMarcaRepository.save(marca);
	}

	@Override
	public Marca findById(Long id) {
		// TODO Auto-generated method stub
		return iMarcaRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		iMarcaRepository.deleteById(id);
		
	}

	@Override
	public List<Marca> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return iMarcaRepository.findByNombre(nombre);
	}

}
