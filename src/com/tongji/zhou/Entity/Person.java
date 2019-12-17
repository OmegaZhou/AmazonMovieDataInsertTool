package com.tongji.zhou.Entity;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.*;

public class Person implements Comparable<Person> {

    private Integer id;
    private PersonType type;
    private String name;
    private String movies;

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public String getMovies() {
        return movies;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return type.getGroup();
    }

    public String getRole() {
        return type.getRole();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person(PersonType type){
        this.type=type;
    }
    public Person(){
    }

    @Override
    public int compareTo(Person obj) {
        Collator collator=Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(this.getName(),obj.getName());
    }

}
