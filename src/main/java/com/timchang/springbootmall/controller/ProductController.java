package com.timchang.springbootmall.controller;

import com.timchang.springbootmall.constant.ProductCategory;
import com.timchang.springbootmall.dao.ProductQueryParams;
import com.timchang.springbootmall.dto.ProductRequest;
import com.timchang.springbootmall.model.Product;
import com.timchang.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            // 查詢條件Filtering
            @RequestParam(required = false) ProductCategory category, // (required = false) 標記為非必要
            @RequestParam(required = false) String search,

            // 排序Sorting
            @RequestParam(defaultValue = "created_date") String orderBy, // 排序依據
            @RequestParam(defaultValue = "desc") String sort // 升冪降冪
    ) {
        // 改用ProductQueryParams 傳遞參數 避免新增變數就需要修改
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);

        List<Product> productList = productService.getProducts(productQueryParams);

        // 因為RESTFUL風格 查全部查無也OK 與查單個不同
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {
        // 檢查有無product
        Product product = productService.getProductById(productId);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改商品數據
        productService.updateProduct(productId, productRequest);

        // 改完重查
        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);

        // 只要商品消失就算是刪除成功，不用特別判斷
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
