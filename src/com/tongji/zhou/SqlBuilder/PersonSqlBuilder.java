package com.tongji.zhou.SqlBuilder;

import com.tongji.zhou.Entity.Person;
import com.tongji.zhou.Entity.PersonGroup;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class PersonSqlBuilder {
    public static String GetPersonIds(Map<Integer,List<Person>> person_map){
        List<Person> persons=person_map.get("list");
        if(persons==null ||persons.isEmpty()){
            return null;
        }
        return new SQL(){{
            SELECT("`"+persons.get(0).getRole()+"_id` as id,`name`");
            FROM("`"+persons.get(0).getRole()+"`");
            for(int i=0;i<persons.size();++i){
                if(i!=0){
                    OR();
                }
                WHERE("name=#{list[+"+i+"].name}");
            }
        }}.toString();
    }

    public static String UpdatePersons(Map<Integer,List<Person>> person_map){
        List<Person> persons=person_map.get("list");
        if(persons==null ||persons.isEmpty()){
            return null;
        }
        return new SQL(){{
            Person tmp=persons.get(0);
            UPDATE("`"+tmp.getRole()+"`");
            SET("`${list[0].group}_count`=`${list[0].group}_count`+1");
            SET("`${list[0].group}_movies`=case " +
                    "when `${list[0].group}_movies` is null then CONCAT('|',#{list[0].movies},'|') " +
                    "else CONCAT(`${list[0].group}_movies`,#{list[0].movies},'|') end ");
            for(int i=0;i<persons.size();++i){
                if(i!=0){
                    OR();
                }
                WHERE("name=#{list[+"+i+"].name}");
            }
        }}.toString();
    }

    public static String InsertPersons(Map<Integer,List<Person>> person_map){
        List<Person> persons=person_map.get("list");
        if(persons==null ||persons.isEmpty()){
            return null;
        }
        return new SQL(){{
            Person tmp=persons.get(0);
            INSERT_INTO("`"+tmp.getRole()+"`");
            INTO_COLUMNS("name,`"+tmp.getGroup()+"_movies`,`"+tmp.getGroup()+"_count`");
            for(int i=0;i<persons.size();++i){
                INTO_VALUES("#{list["+i+"].name},CONCAT('|',#{list["+i+"].movies},'|'),1");
                ADD_ROW();
            }
        }}.toString();
    }

    public static String InsertPersonGroup(Map<Integer,List<PersonGroup>> group_map){
        List<PersonGroup> groups=group_map.get("list");
        if(groups==null ||groups.isEmpty()){
            return null;
        }
        return new SQL(){{
            INSERT_INTO("`${list[0].group}s_group`");
            INTO_COLUMNS("`${list[0].group}s_group_id`,`${list[0].group}_id`,names,count");
            for(int i=0;i<groups.size();++i){
                INTO_VALUES("#{list["+i+"].id},#{list["+i+"].person_id},#{list["+i+"].names},#{list["+i+"].count}");
                ADD_ROW();
            }
        }}.toString();

    }
}
