package com.tongji.zhou.Entity;

public class Product implements Comparable<Product>{
    private Integer id;
    private String format;
    private String price;
    private String productId;

    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId==null?"":productId;
    }

    public String getFormat() {
        return format==null?"":format;
    }

    public String getPrice() {
        return price==null?"":price;
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

    @Override
    public int compareTo(Product o) {
        if(this.getFormat().equals(o.getFormat())){
            if(this.getPrice().equals(o.getPrice())){
                return this.getProductId().compareTo(o.getProductId());
            }else{
                return this.getPrice().compareTo(o.getPrice());
            }
        }else{
            return this.getFormat().compareTo(o.getFormat());
        }
    }
}
