package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Product;
import com.tongji.zhou.Entity.ProductGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface ProductSqlMapper {
    @Insert("insert into products_group(product_id) values(#{product_id})")
    @Options(useGeneratedKeys = true, keyProperty = "product_group_id")
    Integer CreateNewProductGroup(ProductGroup productGroup);

    @Insert("insert into products_group(product_id,product_group_id) values(#{product_id},#{product_group_id})")
    Integer InsertProductGroup(ProductGroup productGroup);

    @Insert("insert into product(price,format,asin) values(#{price},#{format},#{productId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer InsertProduct(Product product);

}
