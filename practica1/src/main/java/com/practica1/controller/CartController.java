package com.practica1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practica1.service.CartService;
import com.practica1.service.ProductService;

@Controller
public class CartController {

	private final CartService cartService;
	private final ProductService productService;
	
	public CartController(CartService cartService, ProductService productService) {
		this.cartService = cartService;
		this.productService = productService;
	}
	
	
//	ver, añadir y eliminar artículos.
	
	@RequestMapping(value = "/cart")
	public String list(Model model) {
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("total", cartService.getTotal());
		return "cart";
	}
	
	@RequestMapping(value = "/cart/add/{id}")
	public String add(@PathVariable Long id) {
		cartService.addProduct(productService.getProductById(id));
		return "redirect:/cart";
	}
	
	@RequestMapping(value = "/cart/remove/{id}")
	public String remove(@PathVariable Long id){
		cartService.removeProduct(id);
		return "redirect:/cart";
	}
	
	
	
}
