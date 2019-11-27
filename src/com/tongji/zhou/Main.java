package com.tongji.zhou;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /*MovieFileReader movieFileReader=new MovieFileReader("a.json");
        while(movieFileReader.hasNext()){
            Movie movie=movieFileReader.next();
            movie.getActors();
        }*/
        SqlInsertTool sqlInsertTool=new SqlInsertTool(SqlInsertTool.DB_TYPE.MYSQL);
        sqlInsertTool.Test();
    }

    public static void print(Object obj){
        System.out.println(obj.toString());
    }
}
