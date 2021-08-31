package com.myntra.hackathon.persist;

import com.myntra.hackathon.models.ecom.Product;

import java.util.List;

public interface ProductDao {
    Product createProduct(Product product);
    Product getProductById(String id);
    List<Product> getProductsByIds(List<String> ids);
}
