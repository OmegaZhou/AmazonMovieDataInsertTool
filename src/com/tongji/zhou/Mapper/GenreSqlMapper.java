package com.tongji.zhou.Mapper;

import com.tongji.zhou.SqlBuilder.GenreSqlBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface GenreSqlMapper {

    @Select("select genre_name from genre_names")
    List<String> GetTableFields();

    @Insert("insert into genre_names values(#{name})")
    Integer InsertGenreName(@Param("name") String name);

    @SelectProvider(value = GenreSqlBuilder.class,method = "GenreSelectSqlBuilder")
    Integer GetGenreId(Map<String,Object> para);

    @InsertProvider(value = GenreSqlBuilder.class,method = "GenreInsertSqlBuilder")
    @Options(useGeneratedKeys = true,keyProperty = "genre_id")
    Integer InsertGenre( Map<String,Object> para);

    @Update("alter table genre add `${genre}` VARCHAR(1) default 'N'")
    void AddNewGenre(String genre);

    @Update("create index `${genre}_index` on genre(`${genre}`)")
    void CreateNewIndex(String genre);

}
