package com.tongji.zhou;

import com.tongji.zhou.Entity.Movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MovieFileReader movieFileReader=new MovieFileReader("a.json");
        SqlInsertTool sqlInsertTool=new SqlInsertTool(SqlInsertTool.DB_TYPE.MYSQL);
        while(movieFileReader.hasNext()){
            Movie movie=movieFileReader.next();
            sqlInsertTool.InsertMovie(movie);
        }
    }

}
