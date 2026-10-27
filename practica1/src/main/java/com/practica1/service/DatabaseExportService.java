package com.practica1.service;

import java.math.BigDecimal;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica1.model.Product;
import com.practica1.repository.ProductRepo;


//Servicio de exportación de la base de datos

@Service
public class DatabaseExportService {

	//Repositorio de productos, inyectado por Spring con @Autowired
	@Autowired
	private ProductRepo productRepo;
	
	
	//Crea el script SQL y lo devuelve como bytes en UTF-8
	public byte[] exportDatabaseToSql() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("CREATE TABLE IF NOT EXISTS PRODUCT (id BIGINT PRIMARY KEY, name VARCHAR(255), price DOUBLE PRECISION NOT NULL);\n");
		
		//Se genera un INSERT para cada producto 
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
		
	}
}
