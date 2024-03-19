package com.timchang.springbootmall.dao;

import com.timchang.springbootmall.constant.ProductCategory;
import com.timchang.springbootmall.dto.ProductRequest;
import com.timchang.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(ProductCategory category, String search);
}
