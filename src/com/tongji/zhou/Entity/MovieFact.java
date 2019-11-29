package com.tongji.zhou.Entity;

public class MovieFact {
    private Integer id;
    private Integer genre_id;
    private Integer actors_group_id;
    private Integer starrings_group_id;
    private Integer supportings_group_id;
    private Integer date_id;
    private Integer directors_group_id;
    private Integer ranking_id;
    private Integer products_group_id;
    private Integer num_of_count=1;
    private String name;

    public Integer getActors_group_id() {
        return actors_group_id;
    }

    public Integer getDate_id() {
        return date_id;
    }

    public Integer getDirectors_group_id() {
        return directors_group_id;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNum_of_count() {
        return num_of_count;
    }

    public Integer getProducts_group_id() {
        return products_group_id;
    }

    public Integer getRanking_id() {
        return ranking_id;
    }

    public Integer getStarrings_group_id() {
        return starrings_group_id;
    }

    public Integer getSupportings_group_id() {
        return supportings_group_id;
    }

    public void setActors_group_id(Integer actors_group_id) {
        this.actors_group_id = actors_group_id;
    }

    public void setDate_id(Integer date_id) {
        this.date_id = date_id;
    }

    public void setDirectors_group_id(Integer directors_group_id) {
        this.directors_group_id = directors_group_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts_group_id(Integer products_group_id) {
        this.products_group_id = products_group_id;
    }

    public void setRanking_id(Integer ranking_id) {
        this.ranking_id = ranking_id;
    }

    public void setStarrings_group_id(Integer starrings_group_id) {
        this.starrings_group_id = starrings_group_id;
    }

    public void setSupportings_group_id(Integer supportings_group_id) {
        this.supportings_group_id = supportings_group_id;
    }
}
