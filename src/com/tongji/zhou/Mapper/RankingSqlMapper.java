package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Movie;
import com.tongji.zhou.Entity.Ranking;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RankingSqlMapper {
    @Select("select ranking_id from ranking where score<#{score}+0.001 and score>#{score}-0.001")
    Integer GetRankingId(Ranking ranking);
    @Update("update ranking set count=count+1, movies=CONCAT(movies,#{movies},'|') where score<#{score}+0.001 and score>#{score}-0.001")
    Integer UpdateRankingId(Ranking ranking);
    @Insert("insert into ranking(count,movies,score) values(1,CONCAT('|',#{movies},'|'),#{score})")
    @Options(useGeneratedKeys = true,keyProperty = "ranking_id")
    Integer InsertRanking(Ranking ranking);
}
