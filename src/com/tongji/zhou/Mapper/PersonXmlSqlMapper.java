package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Corporation;
import com.tongji.zhou.Entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PersonXmlSqlMapper {
    Integer InsertActorCorporations(List<Corporation> corporations);
    Integer InsertActorDirectorCorporations(List<Corporation> corporations);
    void InsertPersonsNotExists(List<Person> personList);
}
