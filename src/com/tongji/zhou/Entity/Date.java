package com.tongji.zhou.Entity;

public class Date {
    private Integer date_id;
    private Integer year;
    private Integer month;
    private Integer weekday;
    private String movies;
    private String date_str;

    public Integer getDate_id() {
        return date_id;
    }

    public Integer getMonth() {
        return month;
    }

    public String getMovies() {
        return movies;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public Integer getYear() {
        return year;
    }

    public String getDate_str() {
        return date_str;
    }

    public void setMonth(Integer month) {
        this.month = month==null?-1:month;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday==null?-1:weekday;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public void setYear(Integer year) {
        this.year = year==null?-1:year;
    }

    public void setDate_str(String date_str) {
        this.date_str = date_str;
    }
}
