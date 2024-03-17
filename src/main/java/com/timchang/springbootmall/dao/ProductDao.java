package com.timchang.springbootmall.dao;

import com.timchang.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
