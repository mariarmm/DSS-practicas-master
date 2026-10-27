package com.practica1.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Genera automaticamente constructor sin parámetros y métodos getter y setter 
@NoArgsConstructor
@Getter
@Setter

@Entity	//Indica que Product es una entidad persistente
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	
	public Product(String name, double price) {
	    this.name = name;
	    this.price = price;
	}
}
