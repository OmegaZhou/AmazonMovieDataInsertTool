package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Movie;
import org.apache.ibatis.annotations.Select;

public interface SqlMapper {
    @Select("select count(*) from movie_name where name=#{title}")
    Integer CheckMovieExist(Movie movie);
}
