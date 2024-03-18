package com.timchang.springbootmall.dto;

import com.timchang.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

/**
 * 負責驗證前端傳來資料
 */
public class ProductRequest {
    //    private Integer productId; // 不需要 由資料庫產生
    //    private Date createdDate; // 由程式設定
    //    private Date lastModifiedDate; // 由程式設定
    @NotNull // 若無此提示 需要添加dependency spring-boot-starter-validation
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    private String description; // 選填

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
