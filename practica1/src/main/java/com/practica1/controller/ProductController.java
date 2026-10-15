package com.practica1.controller;

import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practica1.model.Product;
import com.practica1.service.ProductService;

@Controller
public class ProductController {

	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	//listar, añadir, editar y eliminar productos
	
	@RequestMapping(value = "/products")
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@RequestMapping(value = "/products/add")
	public String add(Model model) {
		model.addAttribute("product", new Product());
		return "admin"; 
	}
	
	@RequestMapping(value = "/products/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "product_form";
	}
	
	@RequestMapping(value = "/products/save")
	public String save(Product product) {
		productService.saveProduct(product);
		return "redirect:/admin";
	}
	
	
	@RequestMapping(value = "/products/delete/{id}")
	public String delete(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/products";
	}
	
	
	
	
}
