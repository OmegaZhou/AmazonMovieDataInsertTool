package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Corporation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CorporationSqlMapper {
    Integer InsertActorCorporations(List<Corporation> corporations);
    Integer InsertActorDirectorCorporations(List<Corporation> corporations);
}
