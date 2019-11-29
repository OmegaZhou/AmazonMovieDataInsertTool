package com.tongji.zhou.Entity;

public class Product {
    private Integer id;
    private String format;
    private String price;
    private String productId;

    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getFormat() {
        return format;
    }

    public String getPrice() {
        return price;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
