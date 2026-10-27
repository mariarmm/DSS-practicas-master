package com.practica1.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.practica1.repository.ProductRepo;
import com.practica1.model.Product;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//Servicio de productos

@Service
public class ProductService {
	
	//Repositorio de acceso a datos
	private final ProductRepo productRepo;

	public ProductService (ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
	

	//Devuelve todos los productos del catálogo
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	
	//Busca un producto por su id.
	public Product getProductById(Long id) {

		return productRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + id));
    }
	
	
	//Guarda un producto
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}
	
	//Elimina el producto con ese id
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}
	
	
	//Busqueda y filtro por precio
	// q es el texto que debe contener el nombre
	// min corresponde al precio minimo
	// max corresponde al precio máximo
	// Los filtros son opcionales
	public List<Product> search(String q, Double min, Double max){
		
		return productRepo.findAll().stream()
				.filter(p -> q == null || q.isBlank() || (p.getName() != null && p.getName().toLowerCase().contains(q.toLowerCase())))
				.filter(p -> min == null || p.getPrice() >= min)
				.filter(p -> max == null || p.getPrice() <= max)
				.toList();
	}
	
}
