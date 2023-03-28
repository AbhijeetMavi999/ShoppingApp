package com.product.controller;

import com.product.entity.Product;
import com.product.service.ProductService;
import jakarta.ws.rs.HEAD;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        log.info("saveProduct() method of ProductController is called");

        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        log.info("getProductById() method of ProductController is called");

        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getAllProductAndSortByPrice(@PathVariable("name") String name) {
        log.info("getAllProductAndSortByPrice() method of ProductController is called");

        List<Product> products = productService.getByNameProductAndSortByPrice(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        log.info("updateProduct() method of ProductController is called");

        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id) {
        log.info("deleteProductById() method of ProductController is called");

        productService.deleteProductById(id);
        return new ResponseEntity<>("Product successfully deleted by id: "+id, HttpStatus.OK);
    }
}
