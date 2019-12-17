package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Product;
import com.tongji.zhou.Entity.ProductGroup;
import com.tongji.zhou.SqlBuilder.ProductSqlBuilder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ProductSqlMapper {
    @Insert("insert into products_group(product_id) values(#{product_id})")
    @Options(useGeneratedKeys = true, keyProperty = "product_group_id")
    Integer CreateNewProductGroup(ProductGroup productGroup);

    @InsertProvider(value = ProductSqlBuilder.class,method = "InsertProductGroups")
    Integer InsertProductGroups(List<ProductGroup> productGroup);

    @InsertProvider(value = ProductSqlBuilder.class,method = "InsertProducts")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer InsertProducts(List<Product> product);

}
