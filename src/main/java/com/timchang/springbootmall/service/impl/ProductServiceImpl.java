package com.timchang.springbootmall.service.impl;

import com.timchang.springbootmall.dao.ProductDao;
import com.timchang.springbootmall.dto.ProductRequest;
import com.timchang.springbootmall.model.Product;
import com.timchang.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
