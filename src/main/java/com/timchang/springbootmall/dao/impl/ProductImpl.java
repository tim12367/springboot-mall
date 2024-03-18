package com.timchang.springbootmall.dao.impl;

import com.timchang.springbootmall.dao.ProductDao;
import com.timchang.springbootmall.dto.ProductRequest;
import com.timchang.springbootmall.model.Product;
import com.timchang.springbootmall.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name, category, " +
                "image_url, price, stock, description, created_date, last_modified_date " +
                "FROM product WHERE product_id = :product_id";
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        // SQL
        String sql = "INSERT INTO \n" +
                "    product (product_name, category, image_url, price, \n" +
                "             stock, description, created_date, \n" +
                "             last_modified_date)\n" +
                "    VALUES (:product_name, :category, :image_url, :price,\n" +
                "            :stock, :description, :created_date,\n" +
                "            :last_modified_date)";

        // SQL參數
        Map<String, Object> param = new HashMap<>();
        param.put("product_name", productRequest.getProductName());
        param.put("category", productRequest.getCategory().toString()); // enum -> string
        param.put("image_url", productRequest.getImageUrl());
        param.put("price", productRequest.getPrice());
        param.put("stock", productRequest.getStock());
        param.put("description", productRequest.getDescription());

        Date now = new Date();
        param.put("created_date", now);
        param.put("last_modified_date", now);

        // 接SQL產生流水號
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 執行
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(param), keyHolder);

        // 倒出流水號
        int productId = keyHolder.getKey().intValue();

        return productId;
    }
}
