package com.practica1.service;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practica1.repository.ProductRepo;
import com.practica1.model.Product;

@Service
public class ProductService {
	
	private final ProductRepo productRepo;

	public ProductService (ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
	
//	getAllProducts, getProductById, saveProduct y deleteProduct
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
//	public Optional<Product> getProductById(Long id) {
//		return productRepo.findById(id);
//	}
	
	public Product getProductById(Long id) {

		return productRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
    }
	
	public void saveProduct(Product product) {
		productRepo.save(product);
	}
	
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}
	
	
}
