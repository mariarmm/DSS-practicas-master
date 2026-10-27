package com.practica1.controller;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practica1.service.DatabaseExportService;
import com.practica1.service.ProductService;

//Controlador de administración

@Controller
public class AdminController {

	//Servicios que usa el controlador, inyectados por el constructor
	private final ProductService productService;
	private final DatabaseExportService exportService;
	
	public AdminController(ProductService productService, DatabaseExportService exportService) {
		this.productService = productService;
		this.exportService = exportService;
	}
	
	//Gestiona la petición para mostrar el panel de administración
	@RequestMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "admin";
	}
	
	//Gestiona la petición para exportar la base de datos como un script SQL
	@RequestMapping("/admin/export")
	public ResponseEntity<byte[]> exportDatabase(){
	
		byte[] sql = exportService.exportDatabaseToSql();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						"attachment; filename=\"products.sql\"")
				.contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
				.body(sql);
	}
}
