package com.springboot.angular.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.angular.backend.apirest.entity.Marca;
import org.springframework.data.jpa.repository.Query;

public interface IMarcaRepository  extends JpaRepository<Marca, Long>{

	@Query("select a from Marca a where ((a.nombre like %?1%) or (a.descripcion like %?1%))")
	public List<Marca> findByNombre(String nombre);
	
}
