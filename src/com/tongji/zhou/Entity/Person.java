package com.tongji.zhou.Entity;

public class Person {
    private Integer id;
    private PersonType type;
    private String name;
    private String movies;

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
}
