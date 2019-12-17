package com.tongji.zhou.SqlBuilder;

import com.tongji.zhou.Entity.Person;
import com.tongji.zhou.Entity.Product;
import com.tongji.zhou.Entity.ProductGroup;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class ProductSqlBuilder {
    public static String InsertProducts(Map<Integer, List<Product>> product_map){
        List<Product> products=product_map.get("list");
        if(products==null ||products.isEmpty()){
            return null;
        }
        return new SQL(){{
            INSERT_INTO("`product`");
            INTO_COLUMNS("price,format,asin");
            for(int i=0;i<products.size();++i){
                INTO_VALUES("#{list["+i+"].price},#{list["+i+"].format},#{list["+i+"].productId}");
                ADD_ROW();
            }
        }}.toString();
    }

    public static String InsertProductGroups(Map<Integer, List<ProductGroup>> product_group_map){
        List<ProductGroup> productGroups=product_group_map.get("list");
        if(productGroups==null||productGroups.isEmpty()){
            return null;
        }
        return new SQL(){{
            INSERT_INTO("products_group");
            INTO_COLUMNS("product_id,product_group_id");
            for(int i=0;i<productGroups.size();++i){
                INTO_VALUES("#{list["+i+"].product_id},#{list["+i+"].product_group_id}");
                ADD_ROW();
            }
        }}.toString();
    }
}
