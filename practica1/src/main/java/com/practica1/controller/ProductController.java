package com.practica1.controller;

import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practica1.model.Product;
import com.practica1.service.ProductService;

//Controlador de producto

@Controller
public class ProductController {

	//Servicio que utiliza el controlador
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	//Gestiona la petición para mostrar los productos, aplicando filtros opcionales
	// q es el texto que debe contener el nombre
	// min corresponde al precio minimo
	// max corresponde al precio máximo
	@RequestMapping(value = "/products")
	public String list(@RequestParam (required=false) String q,
						@RequestParam (required=false) Double max,
						@RequestParam (required=false) Double min,
						Model model) {
		
		//Busca los productos que cumplen con los filtros indicados
		model.addAttribute("products", productService.search(q, min, max));
		model.addAttribute("q", q);
		model.addAttribute("min", min);
		model.addAttribute("max", max);
		
		return "products";
	}
	
	//Gestiona la petición para añadir un nuevo producto
	@RequestMapping(value = "/products/add")
	public String add(Model model) {
		model.addAttribute("product", new Product());
		return "admin"; 
	}
	
	//Gestiona la petición para editar un producto
	@RequestMapping(value = "/products/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "product_form";
	}
	
	//Gestiona la petición para guardar un producto nuevo o actualizar uno existente
	@RequestMapping(value = "/products/save")
	public String save(Product product) {
		productService.saveProduct(product);
		return "redirect:/admin";
	}
	
	//Gestiona la petición para eliminar un producto
	@RequestMapping(value = "/products/delete/{id}")
	public String delete(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/products";
	}
	
	
	
	
}
