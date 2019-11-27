package com.tongji.zhou.Entity;

public class Product {
    private String format;
    private String price;
    private String asin;

    public String getAsin() {
        return asin;
    }

    public String getFormat() {
        return format;
    }

    public String getPrice() {
        return price;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
