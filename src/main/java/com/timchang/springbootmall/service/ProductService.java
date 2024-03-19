package com.timchang.springbootmall.service;

import com.timchang.springbootmall.dto.ProductRequest;
import com.timchang.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
