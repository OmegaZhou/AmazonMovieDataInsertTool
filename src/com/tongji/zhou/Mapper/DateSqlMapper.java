package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Date;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DateSqlMapper {
    @Select("select date_id from date where year=#{year} and month=#{month} and weekday=#{weekday}")
    Integer GetDateId(Date date);

    @Insert("insert into date(year,month,weekday,day,count,movies,date_str) values(#{year},#{month},#{weekday},#{day},1,concat('|',#{movies},'|'),#{date_str})")
    @Options(useGeneratedKeys = true,keyProperty = "date_id")
    Integer InsertDate(Date date);

    @Update("update date set count=count+1,movies=concat(movies,#{movies},'|') where year=#{year} and month=#{month} and weekday=#{weekday} and day=#{day}")
    Integer UpdateDate(Date date);

}
