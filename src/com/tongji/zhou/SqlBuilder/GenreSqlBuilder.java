package com.tongji.zhou.SqlBuilder;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class GenreSqlBuilder {
    public static String GenreSelectSqlBuilder(Map<String,String> para){
        return new SQL(){{
            SELECT("genre_id");
            FROM("genre");
            for(Map.Entry<String,String> item:para.entrySet()){
                WHERE("`"+item.getKey()+"` = '"+item.getValue()+"'");
            }
        }}.toString();
    }
    public static String GenreInsertSqlBuilder(Map<String,String> para){
        return new SQL(){{
           INSERT_INTO("genre");
           if(para.isEmpty()){
               VALUES("","");
           }
           for(Map.Entry<String,String> item:para.entrySet()){
               VALUES("`"+item.getKey()+"`","'"+item.getValue()+"'");
           }
        }}.toString();
    }
}
