package com.tongji.zhou.Entity;

public class PersonGroup {
    private Integer id;
    private Integer person_id;
    private String names;
    private Integer count;
    private PersonType type;


    public PersonGroup(PersonType type){
        this.type=type;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public String getGroup() {
        return type.getGroup();
    }

    public String getRole(){
        return type.getRole();
    }

    public Integer getId() {
        return id;
    }

    public Integer getCount() {
        return count;
    }

    public String getNames() {
        return names;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
