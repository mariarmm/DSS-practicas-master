package com.practica1.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.practica1.model.Product;
import com.practica1.repository.ProductRepo;


//Servicio del carrito de compra
//SessionScope permite crear una instancia distinta por cada sesión HTTP, de esta forma cada navegador tiene su propio carrito

@Service
@SessionScope
public class CartService {

	//Repositorio de productos, inyectado por constructor
	private final ProductRepo productRepo;
	
	//Lista con los identificadores de los productos del carrito
	private final List<Long> cartItems = new ArrayList<>();
	
	public CartService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
	
	//Añade un producto al carrito
	public void addProduct(Product product) {
		cartItems.add(product.getId());
	}
	
	//Elimina un producto del carrito
	public void removeProduct(Long id) {
		cartItems.remove(id);
	}
	
	
	//Devuelve los productos del carrito con sus datos actualizados
	public List<Product> getItems(){
		
		//Si se ha borrado el producto, desaparece del carro
		cartItems.removeIf(id -> !productRepo.existsById(id));
		
		//Convierte cada id en su Product leyéndolo del repositorio
		return cartItems.stream().map(id -> productRepo.findById(id).orElseThrow()).toList();
	}
	
	//Suma de los precios de los productos del carrito
	public double getTotal() {
		return getItems().stream().mapToDouble(Product::getPrice).sum();
	}
	
}
