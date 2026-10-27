
package com.practica1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practica1.model.Product;
import com.practica1.service.ProductService;

@RestController	// Indica que esta clase es un controlador REST.

@RequestMapping("/api/products")	// Define la ruta base 
public class ProductRestController {

    // Servicio encargado de gestionar los productos.
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // Gestiona las peticiones para mostrar los productos.
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> list() {
        return productService.getAllProducts();
    }

    // Gestiona las peticiones para obtener un producto.
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Gestiona las peticiones para crear un nuevo producto.
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED) // Devuelve el estado HTTP 201
    public Product create(@RequestBody Product product) {

        product.setId(null);
        return productService.saveProduct(product);
    }

    // Gestiona las peticiones para actualizar los datos de un producto existente.
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product update(@PathVariable Long id, @RequestBody Product product) {

        productService.getProductById(id);
        product.setId(id);
        return productService.saveProduct(product);
    }

    // Gestiona las peticiones para eliminar un producto.
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) // Devuelve el estado HTTP 204.
    public void delete(@PathVariable Long id) {

        productService.getProductById(id);
        productService.deleteProduct(id);
    }
}
