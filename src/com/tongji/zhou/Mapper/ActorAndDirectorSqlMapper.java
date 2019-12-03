package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Corporation;
import com.tongji.zhou.Entity.Person;
import com.tongji.zhou.Entity.PersonGroup;
import com.tongji.zhou.SqlBuilder.PersonSqlBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ActorAndDirectorSqlMapper {
    @SelectProvider(value = PersonSqlBuilder.class,method="GetPersonIds")
    List<Person> GetPersonIds(List<Person> personList);

    @UpdateProvider(value = PersonSqlBuilder.class,method = "UpdatePersons")
    Integer UpdatePersons(List<Person> personList);

    @InsertProvider(value = PersonSqlBuilder.class,method = "InsertPersons")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer InsertPersons(List<Person> personList);


    @Insert("insert into `${group}s_group`(`${group}_id`,names,count) values(#{person_id},#{names},#{count})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer CreateNewGroup(PersonGroup group);

    @Insert("insert into `${group}s_group`(`${group}s_group_id`,`${group}_id`,names,count) values(#{id},#{person_id},#{names},#{count})")
    Integer InsertGroup(PersonGroup group);

    @InsertProvider(value = PersonSqlBuilder.class,method = "InsertPersonGroup")
    Integer InsertGroups(List<PersonGroup> personGroups);

}
