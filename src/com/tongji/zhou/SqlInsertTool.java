package com.tongji.zhou;

import com.tongji.zhou.Entity.*;
import com.tongji.zhou.Entity.Date;
import com.tongji.zhou.Mapper.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.*;


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
        date.setWeekday(movie.getReleaseWeekDay());
        date.setDay(movie.getReleaseDay());
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
        List<String> fields=genreSqlMapper.GetTableFields();
        fields.remove("genre_id");
        Map<String,Object> condition=new HashMap<>();
        for(String key:fields){
            condition.put(key,"N");
        }
        List<String>genres =movie.getGenres();
        for(String genre:genres){
            if(!condition.containsKey(genre)){
                genreSqlMapper.AddNewGenre(genre);
                genreSqlMapper.CreateNewIndex(genre);
                genreSqlMapper.InsertGenreName(genre);
            }
            condition.put(genre,"Y");
        }
        Integer genre_id=genreSqlMapper.GetGenreId(condition);
        if(genre_id==null){
            genreSqlMapper.InsertGenre(condition);
            genre_id=Integer.parseInt(condition.get("genre_id").toString());
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
            List<Person> persons=new ArrayList<>();
            for(int i=0;i<names.size();++i){
                Person person=new Person(type);
                person.setMovies(movie.getTitle());
                String name=names.get(i);
                stringBuilder.append(name);
                stringBuilder.append("|");
                person.setName(name);
                persons.add(person);
            }
            persons=SqlInsertTool.FilterDuplicatePerson(persons);
            ids=InsertPersons(sqlSession,persons);

            ActorAndDirectorSqlMapper actorAndDirectorSqlMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
            List<PersonGroup> personGroups=new ArrayList<>();
            for(int i=0;i<ids.size();++i){
                PersonGroup group=new PersonGroup(type);
                group.setNames(stringBuilder.toString());
                group.setCount(ids.size());
                group.setPerson_id(ids.get(i));
                if(i==0){
                    actorAndDirectorSqlMapper.CreateNewGroup(group);
                    group_id=group.getId();
                }else{
                    group.setId(group_id);
                    personGroups.add(group);
                }
            }
            if(!personGroups.isEmpty()){
                actorAndDirectorSqlMapper.InsertGroups(personGroups);
            }

        }
        return group_id;
    }


    private void InsertActorCorporation(SqlSession sqlSession,Movie movie){
        if(movie.getActors()==null||movie.getActors().size()<=1){
            return;
        }
        List<String> actors=movie.getActors();
        /*List<Person> personList= new LinkedList<>();
        for(String i:actors){
            Person person=new Person(PersonType.ACTOR);
            person.setName(i);
            personList.add(person);
        }
        personList=SqlInsertTool.FilterDuplicatePerson(personList);*/
        List<Person> personList=SqlInsertTool.GetPersons(actors,PersonType.ACTOR);
        if(personList.size()<=1){
            return;
        }
        ActorAndDirectorSqlMapper sqlMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
        CorporationSqlMapper corporationSqlMapper=sqlSession.getMapper(CorporationSqlMapper.class);
        personList=sqlMapper.GetPersonIds(personList);
        List<Corporation> corporations=new LinkedList<>();
        for(int i=0;i<personList.size();++i){
            Person p1=new Person(PersonType.ACTOR);
            p1.setName(personList.get(i).getName());
            p1.setId(personList.get(i).getId());
            p1.setMovies(movie.getTitle());
            for(int j=i+1;j<personList.size();++j){
                Person p2=new Person(PersonType.ACTOR);
                p2.setMovies(movie.getTitle());
                p2.setName(personList.get(j).getName());
                p2.setId(personList.get(j).getId());
                Corporation corporation=new Corporation(p1,p2);
                corporations.add(corporation);
            }
        }
        corporationSqlMapper.InsertActorCorporations(corporations);
    }

    private void InsertActorAndDirectorCorporation(SqlSession sqlSession,Movie movie){
        if(movie.getActors()==null||movie.getDirectors()==null||movie.getActors().isEmpty()||movie.getDirectors().isEmpty()){
            return;
        }
        List<String> actors=movie.getActors();
        List<String> directors=movie.getDirectors();

        List<Person> actorList=SqlInsertTool.GetPersons(actors,PersonType.ACTOR);

        List<Person> directorList=SqlInsertTool.GetPersons(directors,PersonType.DIRECTOR);


        ActorAndDirectorSqlMapper sqlMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
        CorporationSqlMapper corporationSqlMapper=sqlSession.getMapper(CorporationSqlMapper.class);
        actorList=sqlMapper.GetPersonIds(actorList);
        directorList=sqlMapper.GetPersonIds(directorList);
        List<Corporation> corporations=new ArrayList<>();
        for(int i=0;i<actorList.size();++i){
            Person p1=new Person(PersonType.ACTOR);
            p1.setName(actorList.get(i).getName());
            p1.setId(actorList.get(i).getId());
            p1.setMovies(movie.getTitle());
            for(int j=0;j<directorList.size();++j){
                Person p2=new Person(PersonType.DIRECTOR);
                p2.setName(directorList.get(j).getName());
                p2.setId(directorList.get(j).getId());
                p2.setMovies(movie.getTitle());
                Corporation corporation=new Corporation(p1,p2);
                corporations.add(corporation);
            }
        }
        corporationSqlMapper.InsertActorDirectorCorporations(corporations);
    }

    private List<Integer> InsertPersons(SqlSession sqlSession,List<Person> personList){
        if(personList==null||personList.isEmpty()){
            return new ArrayList<>();
        }
        List<Integer> result=new ArrayList<>();
        ActorAndDirectorSqlMapper personMapper=sqlSession.getMapper(ActorAndDirectorSqlMapper.class);
        List<Person> ids=personMapper.GetPersonIds(personList);
        Set<String> contain_ids=new HashSet<>();
        for(Person person:ids){
            result.add(person.getId());
            contain_ids.add(person.getName().toLowerCase());
            person.setMovies(personList.get(0).getMovies());
        }
        List<Person> insert_ids=new ArrayList<>();
        List<Person> update_ids=new ArrayList<>();
        for(Person person:personList){
            if(!contain_ids.contains(person.getName().toLowerCase())){
                insert_ids.add(person);
            }else{
                update_ids.add(person);
            }
        }
        if(!insert_ids.isEmpty()){
            personMapper.InsertPersons(insert_ids);
        }
        if(!update_ids.isEmpty()){
            personMapper.UpdatePersons(update_ids);
        }
        for(Person person:insert_ids){
            result.add(person.getId());
        }
        return result;
    }
    private static List<Person> FilterDuplicatePerson(List<Person> personList){
        Set<Person> set=new TreeSet<>();
        List<Person> result=new ArrayList<>();
        for(Person i:personList){
            if(set.contains(i)){
                continue;
            }else{
                set.add(i);
                result.add(i);
            }
        }
        return result;
    }
    private static List<Person> GetPersons(List<String> person_names,PersonType type){
        List<Person> personList=new LinkedList<>();
        for(String i:person_names){
            Person person=new Person(type);
            person.setName(i);
            personList.add(person);
        }
        personList=SqlInsertTool.FilterDuplicatePerson(personList);
        return FilterDuplicatePerson(personList);
    }
}
