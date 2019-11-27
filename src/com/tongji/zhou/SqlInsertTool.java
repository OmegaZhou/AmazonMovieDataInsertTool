package com.tongji.zhou;

import com.tongji.zhou.Entity.Movie;
import com.tongji.zhou.Mapper.SqlMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;


public class SqlInsertTool{
    public enum DB_TYPE{
      MYSQL("mysql"), HIVE("hive");
      private final String db_name;
      DB_TYPE(String driver_name){
          this.db_name=driver_name;
      }
      public String getDatabaseName(){
          return db_name;
      }

    };
    SqlSessionFactory sqlSessionFactory=null;
    public SqlInsertTool(DB_TYPE type){
        try{
            String resource="config/config.xml";
            InputStream inputStream= Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,type.getDatabaseName());
        }catch (Exception e){
            ErrorHandler.error(e);
        }
    }
    public void InsertMovie(Movie movie){
        SqlSession sqlSession=sqlSessionFactory.openSession(false);
        try{
            if(!CheckMovieExist(sqlSession,movie)){

            }
        }catch (Exception e){
            ErrorHandler.error(e);
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }
    private boolean CheckMovieExist(SqlSession sqlSession,Movie movie){
        return sqlSession.getMapper(SqlMapper.class).CheckMovieExist(movie)!=0;
    }
    public void Test(){
        try(SqlSession sql=sqlSessionFactory.openSession()){
            SqlMapper sqlMapper=sql.getMapper(SqlMapper.class);
            Movie movie=new Movie();
            movie.setTitle("1");
            System.out.println(sqlMapper.CheckMovieExist(movie));
        }
    }

}
