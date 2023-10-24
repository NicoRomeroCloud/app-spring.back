package com.springboot.angular.backend.apirest.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "marcas")
public class Marca {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = false)
	@NotEmpty
	@Size(min = 6, message = "El nombre debe tener como mínimo 6 caracteres")
	private String nombre;
	
	@NotEmpty
	@Size(min = 6, message = "¡Error! La descripción es muy breve")
	@Column(nullable = false, unique = false)
	private String descripcion;
	
	@JsonIgnoreProperties(value = {"marca", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@OneToMany(mappedBy = "marca")
	private Set<Auto> autos = new HashSet<Auto>();

	public Marca(Long id,
			@NotEmpty @Size(min = 6, message = "El nombre debe tener como mínimo 6 caracteres") String nombre,
			@NotEmpty @Size(min = 6, message = "¡Error! La descripción es muy breve") String descripcion,
			Set<Auto> autos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.autos = autos;
	}

	public Marca() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Auto> getAutos() {
		return autos;
	}

	public void setAutos(Set<Auto> autos) {
		this.autos = autos;
	}
	
	
}
