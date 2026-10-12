package com.practica1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practica1.model.Product;

@Service
public class CartService {

//	Almacenar una lista de objetos Product y añadir, eliminar y recuperar artículos.
	
	public final List<Product> cartItems = new ArrayList<>();
	
	public void addProduct(Product product) {
		cartItems.add(product);
	}
	
	public void removeProduct(Long id) {
		cartItems.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(cartItems::remove);
	}
	
	public List<Product> getItems(){
		return cartItems;
	}
	
	public double getTotal() {
		return cartItems.stream().mapToDouble(Product::getPrice).sum();
	}
	
}
