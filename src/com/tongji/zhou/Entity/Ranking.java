package com.tongji.zhou.Entity;

public class Ranking {
    private Integer ranking_id;
    private Integer count;
    private String movies;
    private Double score;

    public Integer getRanking_id() {
        return ranking_id;
    }

    public Double getScore() {
        return score;
    }

    public Integer getCount() {
        return count;
    }

    public String getMovies() {
        return movies;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public void setScore(Double score) {
        this.score = score==null?-1:score;
    }
}
