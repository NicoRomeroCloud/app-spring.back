package com.springboot.angular.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.angular.backend.apirest.entity.Auto;
import org.springframework.data.jpa.repository.Query;

public interface IAutoRepository extends JpaRepository<Auto, Long>{

	@Query("select l from Auto l where (l.nombre like %?1%)")
	public List<Auto>findByNombre(String nombre);

}
