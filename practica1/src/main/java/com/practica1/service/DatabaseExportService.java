package com.practica1.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica1.model.Product;
import com.practica1.repository.ProductRepo;

@Service
public class DatabaseExportService {

	@Autowired
	private ProductRepo productRepo;
	
	
	public byte[] exportDatabaseToSql() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("CREATE TABLE IF NOT EXISTS PRODUCT (id BIGINT PRIMARY KEY, name VARCHAR(255), price DOUBLE PRECISION NOT NULL);\n");
		
		for (Product p: productRepo.findAll()) {
			String name = (p.getName() == null)
					? "NULL"
					: "'" + p.getName().replace("'", "''") + "'";
						
			sql.append("INSERT INTO PRODUCT (id, name, price) VALUES (")
				.append(p.getId()).append(", ").append(name).append(", ")
				.append(BigDecimal.valueOf(p.getPrice()).toPlainString())
				.append(");\n");
			
		}
		
		return sql.toString().getBytes(StandardCharsets.UTF_8);
	
		
		//sentencias SQL INSERT de los productos
		//implementar get/Admin/export para descargar products.sql
		
	}
}
