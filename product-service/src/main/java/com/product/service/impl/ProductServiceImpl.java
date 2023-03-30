package com.product.service.impl;

import com.product.entity.Product;
import com.product.exception.ProductAlreadyExistException;
import com.product.exception.ProductNotFoundException;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Product saveProduct(Product product) {
        log.info("saveProduct() method of ProductServiceImpl is called");

//        Product checkProduct = productRepository.findByName(product.getName());
//
//        if(checkProduct != null) {
//            log.warn("This product name is already present in our database");
//            throw new ProductAlreadyExistException("This product name is already present in our database");
//        }

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Cacheable(cacheNames = "product", key = "#id")
    @Override
    public Product getProductById(Long id) {
        log.info("getProductById() method of ProductServiceImpl is called");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found by id: "+id));
        return product;
    }

    @Override
    public List<Product> getByNameProductAndSortByPrice(String name) {
        log.info("getByNameProductAndSortByPrice() method of ProductServiceImpl is called");

        List<Product> product = productRepository.findByName(name);
        if(product == null) {
            log.info("Product not found by name: "+name);
            throw new ProductNotFoundException("Product not found by name: "+name);
        }

        List<Product> products = productRepository.findByNameContainingOrderByPrice(name);
        return products;
    }

    @CachePut(cacheNames = "product", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Product updateProduct(Long id, Product product) {
        log.info("updateProduct() method of ProductServiceImpl is called");

        Product update = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found by id: "+id));

        update.setName(product.getName());
        update.setDescription(product.getDescription());
        update.setPrice(product.getPrice());
        update.setUnitsInStock(product.getUnitsInStock());

        Product updatedProduct = productRepository.save(update);
        return updatedProduct;
    }

    @CacheEvict(cacheNames = "product", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteProductById(Long id) {
        log.info("deleteProductById() method of ProductServiceImpl is called");

        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found by id: "+id));

        productRepository.deleteById(id);
    }
}
