package com.springboot.angular.backend.apirest.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "autos")
public class Auto implements Serializable{

	private static final long serialVersionUID= 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = false)
	@NotEmpty
	@Size(min = 5 , message = "El nombre debe tener como mínimo 6 caracteres")
	private String nombre;
	
	@Column(nullable = false, unique = false)
	@NotNull
	@Positive(message = "¡Debe ingresar un valor positivo!")
	private int precio;
	
	@JsonIgnoreProperties(value = {"autos", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@ManyToOne()
	@JoinColumn(name = "marca_id", referencedColumnName = "id")
	private Marca marca;

	public Auto(Long id, @NotNull @Positive(message = "¡Debe ingresar un valor positivo!") int precio, Marca marca) {
		super();
		this.id = id;
		this.precio = precio;
		this.marca = marca;
	}

	public Auto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPrecio() {
		return precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	
	
	
	
	
	
	
}
