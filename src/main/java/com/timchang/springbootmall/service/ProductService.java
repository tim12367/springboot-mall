package com.timchang.springbootmall.service;

import com.timchang.springbootmall.dao.ProductQueryParams;
import com.timchang.springbootmall.dto.ProductRequest;
import com.timchang.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);
}
