package com.tongji.zhou;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorHandler {
    public static void error(Exception e){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String message=format.format(date)+" Error: "+ e.getMessage()+"\n";
        System.out.print(message);
        File error_file=new File("error.log");
        try{
            if(!error_file.exists()){
                error_file.createNewFile();
            }
        }catch (IOException err){
            return;
        }
        try(FileOutputStream out =new FileOutputStream(error_file)){
            out.write(message.getBytes());
        }catch (Exception err){
            return;
        }

    }
}
