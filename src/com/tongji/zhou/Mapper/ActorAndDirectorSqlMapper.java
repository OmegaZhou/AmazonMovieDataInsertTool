package com.tongji.zhou.Mapper;

import com.tongji.zhou.Entity.Corporation;
import com.tongji.zhou.Entity.Person;
import com.tongji.zhou.Entity.PersonGroup;
import org.apache.ibatis.annotations.*;

public interface ActorAndDirectorSqlMapper {
    @Select("select `${role}_id` from ${role} where name=#{name}")
    Integer GetPersonId(Person person);

    @Insert("insert ${role}(name,`${group}_movies`,`${group}_count`) values(#{name},CONCAT('|',#{movies},'|'),1)")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer InsertPerson(Person person);

    @Update("update ${role} set `${group}_count`=`${group}_count`+1," +
            "`${group}_movies`=case " +
            "when `${group}_movies` is null then CONCAT('|',#{movies},'|') " +
            "else CONCAT(`${group}_movies`,#{movies},'|') end " +
            "where name=#{name}")
    Integer UpdatePerson(Person person);


    @Insert("insert into `${group}s_group`(`${group}_id`,names,count) values(#{person_id},#{names},#{count})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer CreateNewGroup(PersonGroup group);

    @Insert("insert into `${group}s_group`(`${group}s_group_id`,`${group}_id`,names,count) values(#{id},#{person_id},#{names},#{count})")
    Integer InsertGroup(PersonGroup group);

    @Insert("insert into actor_corporation(actor_name1,actor_name2,actor_id1,actor_id2,count,movies) " +
            "values(#{name1},#{name2},#{id1},#{id2},1,CONCAT('|',#{movies},'|'))")
    Integer InsertActorCorporation(Corporation corporation);

    @Update("update actor_corporation set count=count+1,movies=CONCAT(movies,#{movies},'|') " +
            "where actor_name1=#{name1} and actor_name2=#{name2}")
    Integer UpdateActorCorporation(Corporation corporation);

    @Select("select count from actor_corporation where actor_name1=#{name1} and actor_name2=#{name2}")
    Integer CheckActorCorporation(Corporation corporation);

    @Insert("insert into director_actor_corporation(actor_name,director_name,actor_id,director_id,count,movies) " +
            "values(#{name1},#{name2},#{id1},#{id2},1,CONCAT('|',#{movies},'|'))")
    Integer InsertDirectorActorCorporation(Corporation corporation);

    @Update("update director_actor_corporation set count=count+1,movies=CONCAT(movies,#{movies},'|') " +
            "where actor_name=#{name1} and director_name=#{name2}")
    Integer UpdateDirectorActorCorporation(Corporation corporation);

    @Select("select count from director_actor_corporation where actor_name=#{name1} and director_name=#{name2}")
    Integer CheckDirectorActorCorporation(Corporation corporation);
}
