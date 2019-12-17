package com.tongji.zhou;

import com.tongji.zhou.Entity.Movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String path=args.length==0?"a.json":args[0];
        try(MovieFileReader movieFileReader=new MovieFileReader(path)){
            SqlInsertTool sqlInsertTool=new SqlInsertTool(SqlInsertTool.DB_TYPE.MYSQL);
            while(movieFileReader.hasNext()){
                Movie movie=movieFileReader.next();
                sqlInsertTool.InsertMovie(movie);
            }
        }catch (Exception e){
            ErrorHandler.error(e);
        }

    }

}
