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

@Controller
public class AdminController {

	private final ProductService productService;
	private final DatabaseExportService exportService;
	
	public AdminController(ProductService productService, DatabaseExportService exportService) {
		this.productService = productService;
		this.exportService = exportService;
	}
	
	
	@RequestMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "admin";
	}
	
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
