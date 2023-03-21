package com.product.service;

import com.product.entity.Product;

import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);

    public Product getProductById(Long id);

    public List<Product> getByNameProductAndSortByPrice(String name);

    public Product updateProduct(Long id, Product product);

    public void deleteProductById(Long id);

}
