package com.timchang.springbootmall.dao;

import com.timchang.springbootmall.dto.ProductRequest;
import com.timchang.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
