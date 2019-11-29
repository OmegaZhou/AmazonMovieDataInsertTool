package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Movie;
import com.tongji.zhou.Entity.MovieFact;
import com.tongji.zhou.Entity.MovieName;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.Map;

public interface MovieFactSqlMapper {
    @Select("select count(*) from movie where name=#{title}")
    Integer CheckMovieExist(Movie movie);

    @Insert("insert into movie(genre_id,date_id,ranking_id," +
            "actors_group_id,products_group_id,starrings_group_id,supportings_group_id,directors_group_id," +
            "name,num_of_count) " +
            "values(#{genre_id},#{date_id},#{ranking_id}," +
            "#{actors_group_id},#{products_group_id},#{starrings_group_id},#{supportings_group_id},#{directors_group_id}," +
            "#{name},1)")
    Integer InsertMovieFact(MovieFact movieFact);
}
