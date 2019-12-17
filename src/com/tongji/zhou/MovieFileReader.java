package com.tongji.zhou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.tongji.zhou.Entity.Movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class MovieFileReader implements Iterator<Movie>,AutoCloseable {
    private JSONReader reader;
    private List<Movie> movies;
    private int count=0;
    MovieFileReader(String str){
        try{
            reader=new JSONReader(new FileReader(new File(str)));
            reader.startArray();
        }catch (Exception e){
            ErrorHandler.error(e);
        }
    }

    @Override
    public boolean hasNext() {
        /*if(temp!=null){
            return true;
        }
        temp=next();
        if(temp!=null){
            return true;
        }
        return false;*/
        return reader.hasNext();
    }

    @Override
    public Movie next() {
        return reader.readObject(Movie.class);
    }

    @Override
    public void remove() {

    }

    @Override
    public void close() throws Exception {
        try{
            reader.endArray();
            reader.close();
        }catch (Exception e){
            ErrorHandler.error(e);
        }
    }
}
