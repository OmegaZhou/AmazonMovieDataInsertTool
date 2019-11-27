package com.tongji.zhou.Entity;

import java.util.ArrayList;

public class Movie {
    private ArrayList<String> actors;
    private ArrayList<String> directors;
    private ArrayList<String> genres;
    private ArrayList<Product> products;
    private Double ranking;
    private ArrayList<String> starrings;
    private ArrayList<String> supportingActors;
    private String title;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getStarrings() {
        return starrings;
    }

    public ArrayList<String> getSupportingActors() {
        return supportingActors;
    }

    public Double getRanking() {
        return ranking;
    }

    public String getTitle() {
        return title;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public void setStarrings(ArrayList<String> starrings) {
        this.starrings = starrings;
    }

    public void setSupportingActors(ArrayList<String> supportingActors) {
        this.supportingActors = supportingActors;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
