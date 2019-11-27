package com.tongji.zhou.Entity;

public class MovieFact {
    private int name_id;
    private int genre_id;
    private int actors_in_movie_id;
    private int starrings_group_id;
    private int supporting_actors_group_id;
    private int date_id;
    private int directors_group_id;
    private int ranking_id;
    private int product_group_id;
    private int num_of_count=1;

    public int getActors_in_movie_id() {
        return actors_in_movie_id;
    }

    public int getDate_id() {
        return date_id;
    }

    public int getDirectors_group_id() {
        return directors_group_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public int getName_id() {
        return name_id;
    }

    public int getNum_of_count() {
        return num_of_count;
    }

    public int getProduct_group_id() {
        return product_group_id;
    }

    public int getRanking_id() {
        return ranking_id;
    }

    public int getStarrings_group_id() {
        return starrings_group_id;
    }

    public int getSupporting_actors_group_id() {
        return supporting_actors_group_id;
    }

    public void setActors_in_movie_id(int actors_in_movie_id) {
        this.actors_in_movie_id = actors_in_movie_id;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }

    public void setDirectors_group_id(int directors_group_id) {
        this.directors_group_id = directors_group_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public void setName_id(int name_id) {
        this.name_id = name_id;
    }

    public void setProduct_group_id(int product_group_id) {
        this.product_group_id = product_group_id;
    }

    public void setRanking_id(int ranking_id) {
        this.ranking_id = ranking_id;
    }

    public void setStarrings_group_id(int starrings_group_id) {
        this.starrings_group_id = starrings_group_id;
    }

    public void setSupporting_actors_group_id(int supporting_actors_group_id) {
        this.supporting_actors_group_id = supporting_actors_group_id;
    }
}
