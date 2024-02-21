package com.abi.agro_back.service;

import com.abi.agro_back.collection.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    Product getProductById(String productId);

    List<Product> getAllProducts();

    Product updateProduct(String productId, Product updatedProduct);

    void deleteProduct(String  productId);

    List<Product> getProductsByImagePageId(String id);
}
