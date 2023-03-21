package com.product.service.impl;

import com.product.entity.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).get();
        return product;
    }

    @Override
    public List<Product> getByNameProductAndSortByPrice(String name) {
        List<Product> products = productRepository.findByNameContainingOrderByPrice(name);
        return products;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product update = productRepository.findById(id).get();

        update.setName(product.getName());
        update.setDescription(product.getDescription());
        update.setPrice(product.getPrice());
        update.setUnitsInStock(product.getUnitsInStock());

        Product updatedProduct = productRepository.save(update);
        return updatedProduct;
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
