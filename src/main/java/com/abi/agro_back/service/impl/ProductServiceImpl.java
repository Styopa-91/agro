package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.Product;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.ProductRepository;
import com.abi.agro_back.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(String productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not exists with given id : " + productId));
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(String productId, Product updatedProduct) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product is not exists with given id: " + productId)
        );
        updatedProduct.setId(product.getId());

        return productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product is not exists with given id : " + productId));

        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getProductsByImagePageId(String id) {
        return productRepository.findProductsByImagePageId(id);
    }
}
