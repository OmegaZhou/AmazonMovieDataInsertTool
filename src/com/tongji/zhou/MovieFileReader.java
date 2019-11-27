package com.tongji.zhou;

import com.alibaba.fastjson.JSONObject;
import com.tongji.zhou.Entity.Movie;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class MovieFileReader implements Iterator<Movie>,AutoCloseable {
    private FileInputStream is;
    private Movie temp;
    MovieFileReader(String str){
        try{
            is=new FileInputStream(new File(str));
        }catch (Exception e){
            ErrorHandler.error(e);
            is=null;
        }
    }

    @Override
    public boolean hasNext() {
        if(temp!=null){
            return true;
        }
        temp=next();
        if(temp!=null){
            return true;
        }
        return false;
    }

    @Override
    public Movie next() {
        if(temp!=null){
            Movie re=temp;
            temp=null;
            return re;
        }else{
            if(is!=null){
                int count=0;
                int read_re;
                char ch;
                try{
                    StringBuilder stringBuilder=new StringBuilder();
                    do{
                        read_re=is.read();
                    }while(read_re!=-1&&Character.isSpaceChar(read_re));
                    if(read_re==-1){
                        return null;
                    }
                    if((char)read_re!='{'){
                        throw new Exception("格式错误");
                    }
                    do{
                        ch=(char)read_re;
                        if(ch=='{'){
                            count++;
                        }
                        if(ch=='}'){
                            count--;
                        }
                        if(count>0){
                            read_re=is.read();
                        }
                        stringBuilder.append(ch);
                    }while(count>0);
                    if(count!=0){
                        throw new Exception("格式错误");
                    }
                    String json_str=stringBuilder.toString();
                    JSONObject json=JSONObject.parseObject(json_str);
                    Movie movie=json.toJavaObject(Movie.class);
                    return movie;
                }catch (Exception e){
                    ErrorHandler.error(e);
                    return null;
                }

            }
        }
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public void close() throws Exception {
        try{
            if(is!=null){
                is.close();
            }
        }catch (Exception e){
            ErrorHandler.error(e);
        }
    }
}
