package com.tongji.zhou;

import com.tongji.zhou.Entity.*;
import com.tongji.zhou.Mapper.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SqlInsertTool{
    private int movie_count=1;
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
            InitDatabase();
        }catch (Exception e){
            ErrorHandler.error(e);
        }
    }

    private void InitDatabase(){
        SqlSession sqlSession=sqlSessionFactory.openSession(false);
        try{
            GenreSqlMapper genreSqlMapper=sqlSession.getMapper(GenreSqlMapper.class);
            Map<String,Object> fields=genreSqlMapper.GetTableFields();
            if(fields==null){
                genreSqlMapper.InitInsert();
            }
            sqlSession.commit();
        }catch (Exception e){
            ErrorHandler.error(e);
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    public void InsertMovie(Movie movie){
        SqlSession sqlSession=sqlSessionFactory.openSession(false);
        try{
            if(!CheckMovieExist(sqlSession,movie)){
                MovieFact movieFact=new MovieFact();
                movieFact.setName(movie.getTitle());
                movieFact.setRanking_id(InsertRanking(sqlSession,movie));
                movieFact.setDate_id(InsertDate(sqlSession,movie));
                movieFact.setGenre_id(InsertGenre(sqlSession,movie));
                movieFact.setProducts_group_id(InsertProduct(sqlSession,movie));
                movieFact.setActors_group_id(InsertPersonGroup(sqlSession,movie,PersonType.ACTOR));
                movieFact.setSupportings_group_id(InsertPersonGroup(sqlSession,movie,PersonType.SUPPORTING));
                movieFact.setStarrings_group_id(InsertPersonGroup(sqlSession,movie,PersonType.STARRING));
                movieFact.setDirectors_group_id(InsertPersonGroup(sqlSession,movie,PersonType.DIRECTOR));
                InsertActorCorporation(sqlSession,movie);
                InsertActorAndDirectorCorporation(sqlSession,movie);
                InsertMovieFact(sqlSession,movieFact);
                sqlSession.commit();
                System.out.println("Save movie"+movie_count);
                ++movie_count;
            }
        }catch (Exception e){
            ErrorHandler.error(e);
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

    private boolean CheckMovieExist(SqlSession sqlSession,Movie movie){
        return sqlSession.getMapper(MovieFactSqlMapper.class).CheckMovieExist(movie)!=0;
    }

    private void InsertMovieFact(SqlSession sqlSession,MovieFact movieFact){
        sqlSession.getMapper(MovieFactSqlMapper.class).InsertMovieFact(movieFact);
    }

    private Integer InsertRanking(SqlSession sqlSession,Movie movie){
        Ranking ranking=new Ranking();
        ranking.setScore(movie.getRanking());
        ranking.setMovies(movie.getTitle());
        RankingSqlMapper rankingSqlMapper=sqlSession.getMapper(RankingSqlMapper.class);
        Integer ranking_id=rankingSqlMapper.GetRankingId(ranking);
        if(ranking_id==null){
            rankingSqlMapper.InsertRanking(ranking);
            ranking_id=ranking.getRanking_id();
        }else{
            rankingSqlMapper.UpdateRankingId(ranking);
        }
        return ranking_id;
    }

    private Integer InsertDate(SqlSession sqlSession,Movie movie){
        Date date=new Date();
        date.setDate_str(movie.getReleaseDate());
        date.setYear(movie.getReleaseYear());
        date.setMonth(movie.getReleaseMonth());
        date.setWeekday(movie.getReleaseDay());
        date.setMovies(movie.getTitle());
        DateSqlMapper dateSqlMapper=sqlSession.getMapper(DateSqlMapper.class);
        Integer date_id=dateSqlMapper.GetDateId(date);
        if(date_id==null){
            dateSqlMapper.InsertDate(date);
            date_id=date.getDate_id();
        }else{
            dateSqlMapper.UpdateDate(date);
        }
        return date_id;
    }

    private Integer InsertGenre(SqlSession sqlSession,Movie movie){
        GenreSqlMapper genreSqlMapper=sqlSession.getMapper(GenreSqlMapper.class);
        Map<String,Object>fields=genreSqlMapper.GetTableFields();
        fields.remove("genre_id");
        for(String key:fields.keySet()){
            fields.put(key,"N");
        }
        List<String>genres =movie.getGenres();
        for(String genre:genres){
            if(!fields.containsKey(genre)){
                genreSqlMapper.AddNewGenre(genre);
                genreSqlMapper.CreateNewIndex(genre);
            }
            fields.put(genre,"Y");
        }
        Integer genre_id=genreSqlMapper.GetGenreId(fields);
        if(genre_id==null){
            genreSqlMapper.InsertGenre(fields);
            genre_id=Integer.parseInt(fields.get("genre_id").toString());
        }
        return genre_id;
    }

    private Integer InsertProduct(SqlSession sqlSession,Movie movie){
        if(movie.getProducts()==null||movie.getProducts().isEmpty()){
            return null;
        }
        List<Product> products=movie.getProducts();
        ProductGroup productGroup=new ProductGroup();
        ProductSqlMapper productSqlMapper=sqlSession.getMapper(ProductSqlMapper.class);
        for(int i=0;i<products.size();++i){
            Product product=products.get(i);
            productSqlMapper.InsertProduct(product);
            productGroup.setProduct_id(product.getId());
            if(i==0){
                productSqlMapper.CreateNewProductGroup(productGroup);
            }else{
                productSqlMapper.InsertProductGroup(productGroup);
            }
        }
        return productGroup.getProduct_group_id();
    }

    private Integer InsertPersonGroup(SqlSession sqlSession,Movie movie,PersonType type){
        List<String> names=null;
        Integer group_id=null;
        switch (type){
            case ACTOR:
                names=movie.getActors();
                break;
            case DIRECTOR:
                names=movie.getDirectors();
                break;
            case STARRING:
                names=movie.getSupportingActors();
                break;
            case SUPPORTING:
                names=movie.getStarrings();
                break;
        }
        if(names==null||names.isEmpty()){
            return null;
        }else{
            StringBuilder stringBuilder=new StringBuilder("|");
            List<Integer> ids=new ArrayList<>();
            Person person=new Person(type);
            person.setMovies(movie.getTitle());
            for(int i=0;i<names.size();++i){
                String name=names.get(i);
                stringBuilder.append(name);
                stringBuilder.append("|");
                person.setName(name);
                ids.add(InsertPerson(sqlSession,person));
            }

            ActorAndDirectorSqlMapper actorAndDirectorSqlMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
            PersonGroup group=new PersonGroup(type);
            group.setNames(stringBuilder.toString());
            group.setCount(ids.size());
            for(int i=0;i<ids.size();++i){
                group.setPerson_id(ids.get(i));
                if(i==0){
                    actorAndDirectorSqlMapper.CreateNewGroup(group);
                }else{
                    actorAndDirectorSqlMapper.InsertGroup(group);
                }
            }
            group_id=group.getId();
        }
        return group_id;
    }


    private Integer InsertPerson(SqlSession sqlSession,Person person){
        ActorAndDirectorSqlMapper personSqlMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
        Integer person_id=personSqlMapper.GetPersonId(person);
        if(person_id==null){
            personSqlMapper.InsertPerson(person);
            person_id=person.getId();
        }else{
            personSqlMapper.UpdatePerson(person);
        }
        return person_id;
    }

    private void InsertActorCorporation(SqlSession sqlSession,Movie movie){
        if(movie.getActors()==null){
            return;
        }
        List<String> actors=movie.getActors();
        Person p1=new Person(PersonType.ACTOR);
        Person p2=new Person(PersonType.ACTOR);
        p1.setMovies(movie.getTitle());
        p2.setMovies(movie.getTitle());
        ActorAndDirectorSqlMapper sqlMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
        for(int i=0;i<actors.size();++i){
            p1.setName(actors.get(i));
            for(int j=i+1;j<actors.size();++j){
                p2.setName(actors.get(j));
                Corporation corporation=new Corporation(p1,p2);
                if(sqlMapper.CheckActorCorporation(corporation)!=null){
                    sqlMapper.UpdateActorCorporation(corporation);
                }else{
                    corporation.setId1(sqlMapper.GetPersonId(corporation.getP1()));
                    corporation.setId2(sqlMapper.GetPersonId(corporation.getP2()));
                    sqlMapper.InsertActorCorporation(corporation);
                }
            }
        }
    }

    private void InsertActorAndDirectorCorporation(SqlSession sqlSession,Movie movie){
        if(movie.getActors()==null||movie.getDirectors()==null){
            return;
        }
        List<String> actors=movie.getActors();
        List<String> directors=movie.getDirectors();
        Person p1=new Person(PersonType.ACTOR);
        Person p2=new Person(PersonType.DIRECTOR);
        p1.setMovies(movie.getTitle());
        p2.setMovies(movie.getTitle());
        ActorAndDirectorSqlMapper sqlMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
        for(int i=0;i<actors.size();++i){
            p1.setName(actors.get(i));
            for(int j=0;j<directors.size();++j){
                p2.setName(directors.get(j));
                Corporation corporation=new Corporation(p1,p2);
                if(sqlMapper.CheckDirectorActorCorporation(corporation)!=null){
                    sqlMapper.UpdateDirectorActorCorporation(corporation);
                }else{
                    corporation.setId1(sqlMapper.GetPersonId(corporation.getP1()));
                    corporation.setId2(sqlMapper.GetPersonId(corporation.getP2()));
                    sqlMapper.InsertDirectorActorCorporation(corporation);
                }
            }
        }
    }
}
