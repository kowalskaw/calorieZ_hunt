package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer productID;
    String productsName;
    Integer caloriesForProduct;

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Product(String productsName, Integer caloriesForProduct) {
        this.productsName = productsName;
        this.caloriesForProduct = caloriesForProduct;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public Integer getCaloriesForProduct() {
        return caloriesForProduct;
    }

    public void setCaloriesForProduct(Integer caloriesForProduct) {
        this.caloriesForProduct = caloriesForProduct;
    }
}
